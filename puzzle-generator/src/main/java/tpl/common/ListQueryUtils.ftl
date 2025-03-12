package ${generatorPackage}.util;


import java.util.*;
import java.util.function.BiConsumer;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import ${package}.define.Define;
import ${generatorPackage}.list.ListQueryParam;
import cn.codependency.framework.puzzle.common.Errors;
import cn.codependency.framework.puzzle.common.Streams;
import cn.codependency.framework.puzzle.generator.constants.Database;
import cn.codependency.framework.puzzle.generator.generate.SchemaGenerator;
import cn.codependency.framework.puzzle.model.RootModel;

import cn.hutool.core.text.CharPool;


public class ListQueryUtils {

    
    public static final Map<String, Object> SCHEMA = SchemaGenerator.generator(Define.INSTANCE.getRegistry());

    public static <T extends RootModel<?>> void prepareListQuery(Class<T> clazz, ListQueryParam param, List<String> fields) {

        String mainModel = clazz.getSimpleName();
        param.setMainTable(modelTable(mainModel));
        Set<String> relatePaths = new TreeSet<>();
        param.setColumns(Lists.newArrayList(Streams.toList(fields, f -> new ListQueryParam.QueryColumn(f))));


        // 准备展示列
        for (ListQueryParam.QueryColumn column : param.getColumns()) {
            if (prepareColumn(column, mainModel)) {
                relatePaths.add(column.getField());
            } else {
                addPreJoinPath(relatePaths, column.getField());
            }
        }

        // 准备查询字段
        if (CollectionUtils.isNotEmpty(param.getQueries())) {
            for (ListQueryParam.QueryCondition query : param.getQueries()) {
                if (Objects.equals(query.getType(), "or")) {
                    for (ListQueryParam.QueryCondition condition : query.getConditions()) {
                        if (prepareQueryCondition(condition, mainModel)) {
                            relatePaths.add(condition.getField());
                        } else {
                            addPreJoinPath(relatePaths, condition.getField());
                        }
                    }
                } else {
                    if (prepareQueryCondition(query, mainModel)) {
                        relatePaths.add(query.getField());
                    } else {
                        addPreJoinPath(relatePaths, query.getField());
                    }
                }
            }
        }

        // 准备额外的查询字段
        if (CollectionUtils.isNotEmpty(param.getExtColumns())) {
            for (ListQueryParam.QueryColumn column : param.getExtColumns()) {
                if (prepareColumn(column, mainModel)) {
                    relatePaths.add(column.getField());
                } else {
                    addPreJoinPath(relatePaths, column.getField());
                }
            }
        }


        if (CollectionUtils.isNotEmpty(param.getOrders())) {
            // 准备排序字段
            for (ListQueryParam.OrderBy order : param.getOrders()) {
                if (order.getExpression() == null) {
                    if (prepareOrderBy(order, mainModel)) {
                        relatePaths.add(order.getField());
                    } else {
                        addPreJoinPath(relatePaths, order.getField());
                    }
                }
            }
        }

        // 准备joins
        param.setJoins(prepareJoinTables(mainModel, relatePaths));
    }


    public static <T extends RootModel<?>> void prepareListQuery(Class<T> clazz, ListQueryParam param) {
        prepareListQuery(clazz, param, Lists.newArrayList("id"));
    }


    private static void addPreJoinPath(Set<String> relatePaths, String field) {
        String[] split = field.split("\\.");
        if (split.length > 2) {
            relatePaths.add(StringUtils.join(split, ".", 0, split.length - 1));
        }
    }


    private static boolean prepareColumn(ListQueryParam.QueryColumn column, String mainModel) {
        return prepareField(column, column.getField(), mainModel, ListQueryParam.QueryColumn::setDbField, ListQueryParam.QueryColumn::setFieldName, (thisColumn, map) -> {
            if (Objects.nonNull(map)) {
                String dataSource = (String) map.get("dataSource");
                if (Objects.nonNull(dataSource)) {
                    if (dataSource.startsWith("$dict.")) {
                        String enums = dataSource.substring("$dict.".length());
                        thisColumn.setEnums(enums);
                    }
                    if (dataSource.startsWith("$mapping.")) {
                        String mapping = dataSource.substring("$mapping.".length());
                        thisColumn.setMapping(mapping);
                    }
                }
            }
        }, (thisColumn, map) -> {
            Map<String, Object> ref = (Map<String, Object>) map.get("ref");
            Map<String, Object> field = (Map<String, Object>) map.get("field");
            if (Objects.nonNull(ref) && Objects.equals(ref.get("refType"), "links")) {
                String tableName = (String) getModelDef((String) ref.get("refModel")).get("tableName");
                String sourceField = thisColumn.getField();
                String[] fieldPaths = sourceField.split("\\.");
                String preTableAlias = "_main";
                Map<String, Object> modelDef = null;
                if (fieldPaths.length > 2) {
                    String preTablePaths = StringUtils.join(fieldPaths, ".", 0, fieldPaths.length - 2);
                    preTableAlias = cn.codependency.framework.puzzle.generator.utils.StringUtils.toCamelCase(preTablePaths, CharPool.DOT);
                    Map<String, Object> modelRefByPath = getModelRefByPath(mainModel, preTablePaths);
                    modelDef = getModelDef((String) modelRefByPath.get("refModel"));
                } else {
                    modelDef = getModelDef(mainModel);
                }
                String dbField = getModelDbField(modelDef, (String) ref.get("refField"));
                if (Define.INSTANCE.getRegistry().getDatabase() == Database.Mysql) {
                    String subQuery = String.format("(select group_concat(%s separator ',') from %s where JSON_CONTAINS(CONVERT(%s.%s, JSON), CONVERT(id, CHAR)) > 0)", field.get("tableField"), tableName, preTableAlias, dbField);
                    thisColumn.setExpression(subQuery);
                } else if (Define.INSTANCE.getRegistry().getDatabase() == Database.Postgres) {
                    // 字段暂不处理
                }
            }
        });
    }

    private static List<ListQueryParam.Join> prepareJoinTables(String mainModel, Set<String> relatePaths) {
        List<ListQueryParam.Join> joins = new ArrayList<>();
        Set<String> joinTableContain = new HashSet<>();
        for (String relatePath : relatePaths) {
            String[] splitPaths = relatePath.split("\\.");
            if (splitPaths.length == 1) {
                continue;
            }

            for (int i = 0; i < splitPaths.length - 1; i++) {
                String path = StringUtils.join(splitPaths, ".", 0, i + 1);
                boolean add = joinTableContain.add(path);
                if (add) {
                    String joinAlias =
                        cn.codependency.framework.puzzle.generator.utils.StringUtils.toCamelCase(path, CharPool.DOT);
                    Map<String, Object> modelRefByPath = getModelRefByPath(mainModel, path);
                    Map<String, Object> joinModelDef = (Map<String, Object>)modelRefByPath.get("subModel");
                    if (Objects.isNull(joinModelDef)) {
                        joinModelDef = getModelDef((String)modelRefByPath.get("refModel"));
                    }

                    String leftAlias = null;
                    Map<String, Object> leftModelDef = null;
                    if (i > 0) {
                        String prePath = StringUtils.join(splitPaths, ".", 0, i);
                        leftAlias =
                            cn.codependency.framework.puzzle.generator.utils.StringUtils.toCamelCase(prePath, CharPool.DOT);

                        Map<String, Object> findModel = getModelRefByPath(mainModel, prePath);
                        leftModelDef = getModelDef((String)findModel.get("refModel"));
                        if (Objects.isNull(leftModelDef)) {
                            leftModelDef = (Map<String, Object>) findModel.get("subModel");
                        }
                    } else {
                        leftAlias = "_main";
                        leftModelDef = getModelDef(mainModel);
                    }

                    ListQueryParam.Join join = new ListQueryParam.Join();
                    join.setName(joinAlias);
                    join.setTableName((String)joinModelDef.get("tableName"));
                    String beRefField = (String)modelRefByPath.get("beRefField");

                    if (Objects.nonNull(beRefField)) {
                        join.setCondition(String.format("%s.id = %s.%s", leftAlias, joinAlias,
                            getModelDbField(joinModelDef, beRefField)));
                    } else {
                        if (Objects.equals(modelRefByPath.get("refType"), "links")) {
                            if (Define.INSTANCE.getRegistry().getDatabase() == Database.Postgres) {
                                join.setCondition(String.format("%s.id in (select jsonb_array_elements_text(%s.%s::jsonb)::bigint)",
                                    joinAlias, leftAlias, getModelDbField(leftModelDef, (String) modelRefByPath.get("refField"))));
                            } else if (Define.INSTANCE.getRegistry().getDatabase() == Database.Mysql) {
                                join.setCondition(
                                String.format("JSON_CONTAINS(%s.%s, CAST(%s.id AS JSON), '$')",
                                    leftAlias, getModelDbField(leftModelDef, (String) modelRefByPath.get("refField")), joinAlias));
                            } else {
                                throw Errors.system(String.format("database: %s, not allow this links operation", Define.INSTANCE.getRegistry().getDatabase()));
                            }
                        } else {
                            join.setCondition(String.format("%s.%s = %s.id", leftAlias,
                                getModelDbField(leftModelDef, (String) modelRefByPath.get("refField")), joinAlias));
                        }
                    }
                    joins.add(join);
                }
            }
        }

        return joins;
    }

    private static String modelTable(String model) {
        Map<String, Map<String, Object>> models = (Map<String, Map<String, Object>>)SCHEMA.get("models");
        Map<String, Object> modelDef = models.get(model);
        if (Objects.nonNull(modelDef)) {
            return (String)modelDef.get("tableName");
        }
        return null;
    }

    public static Map<String, Object> getModelDef(String model) {
        Map<String, Map<String, Object>> models = (Map<String, Map<String, Object>>)SCHEMA.get("models");
        Map<String, Object> modelDef = models.get(model);
        return modelDef;
    }

    public static String getModelDbField(Map<String, Object> modelDef, String fieldName) {
        List<Map<String, Object>> fields = (List<Map<String, Object>>)modelDef.get("fields");
        for (Map<String, Object> field : fields) {
            if (Objects.equals(field.get("fieldName"), fieldName)) {
                return (String)field.get("tableField");
            }
        }
        return null;
    }

    public static Map<String, Object> getModelRefByPath(String mainModel, String modelPath) {
        Map<String, Object> mainModelDef = getModelDef(mainModel);
        if (Objects.isNull(mainModelDef)) {
            return null;
        }
        String[] paths = modelPath.split("\\.");
        Map<String, Object> modelDef = mainModelDef;
        Map<String, Object> modelRef = null;
        nextPath:
        for (int i = 0; i < paths.length; i++) {
            String path = paths[i];
            List<Map<String, Object>> refs = (List<Map<String, Object>>)modelDef.get("refs");

            // 从关联中查找
            if (CollectionUtils.isNotEmpty(refs)) {
                for (Map<String, Object> ref : refs) {
                    if (StringUtils.equals((String)ref.get("fieldName"), path)) {
                        String refModel = (String)ref.get("refModel");
                        modelRef = ref;
                        modelDef = getModelDef(refModel);
                        continue nextPath;
                    }
                }
            }

            List<Map<String, Object>> fields = (List<Map<String, Object>>)modelDef.get("fields");
            if (CollectionUtils.isNotEmpty(fields)) {
                for (Map<String, Object> field : fields) {
                    if (StringUtils.equals((String)field.get("fieldName"), path)) {
                        modelRef = field;
                        modelDef = (Map<String, Object>)field.get("subModel");
                        continue nextPath;
                    }
                }
            }
            throw new IllegalArgumentException("主模型: " + mainModel + "找不到字段引用:" + path + ", 全路径: " + modelPath);
        }
        return modelRef;
    }

    private static <T> boolean prepareField(T condition, String fieldPath, String mainModel,
        BiConsumer<T, String> dbFieldSetter, BiConsumer<T, String> fieldNameSetter) {
        return prepareField(condition, fieldPath, mainModel, dbFieldSetter, fieldNameSetter, null, null);
    }

    private static <T> boolean prepareField(T condition, String fieldPath, String mainModel,
        BiConsumer<T, String> dbFieldSetter, BiConsumer<T, String> fieldNameSetter,
        BiConsumer<T, Map<String, Object>> fieldExtSetter, BiConsumer<T, Map<String, Object>> subQuerySetter) {
        Map<String, Object> mainModelDef = getModelDef(mainModel);
        if (Objects.isNull(mainModelDef)) {
            return true;
        }

        String[] paths = fieldPath.split("\\.");
        if (paths.length == 1) {
            List<Map<String, Object>> fields = (List<Map<String, Object>>)mainModelDef.get("fields");
            boolean descField = false;
            String fPath = paths[0];
            if (fPath.endsWith("$desc")) {
                fPath = fPath.substring(0, fPath.length() - "$desc".length());
                descField = true;
            }

            for (Map<String, Object> field : fields) {
                if (StringUtils.equals((String)field.get("fieldName"), fPath)) {
                    dbFieldSetter.accept(condition, "_main." + field.get("tableField"));
                    if (Objects.nonNull(fieldNameSetter)) {
                        fieldNameSetter.accept(condition,
                            descField ? field.get("fieldName") + "$desc" : (String)field.get("fieldName"));
                    }
                    if (Objects.nonNull(fieldExtSetter)) {
                        fieldExtSetter.accept(condition, field);
                    }
                    return true;
                }
            }
            throw new IllegalArgumentException("主模型: " + mainModel + "，找不到字段引用:" + paths[0] + ", 全路径: " + fieldPath);
        } else {
            Map<String, Object> modelDef = mainModelDef;
            boolean loadJoinPath = true;
            Map<String, Object> fieldRef = null;

            nextPath:
            for (int i = 0; i < paths.length - 1; i++) {
                String path = paths[i];
                List<Map<String, Object>> refs = (List<Map<String, Object>>)modelDef.get("refs");

                // 从关联中查找
                if (CollectionUtils.isNotEmpty(refs)) {
                    for (Map<String, Object> ref : refs) {
                        if (StringUtils.equals((String)ref.get("fieldName"), path)) {
                            fieldRef = ref;
                            String refModel = (String)ref.get("refModel");
                            modelDef = getModelDef(refModel);
                            continue nextPath;
                        }
                    }
                }

                List<Map<String, Object>> fields = (List<Map<String, Object>>)modelDef.get("fields");
                if (CollectionUtils.isNotEmpty(fields)) {
                    for (Map<String, Object> field : fields) {
                        if (StringUtils.equals((String)field.get("fieldName"), path)) {
                            modelDef = (Map<String, Object>)field.get("subModel");
                            continue nextPath;
                        }
                    }
                }
                throw new IllegalArgumentException("主模型: " + mainModel + "，找不到字段引用:" + path + ", 全路径: " + fieldPath);
            }

            List<Map<String, Object>> fields = (List<Map<String, Object>>)modelDef.get("fields");
            String path = paths[paths.length - 1];
            for (Map<String, Object> field : fields) {
                String alias = cn.codependency.framework.puzzle.generator.utils.StringUtils
                    .toCamelCase(StringUtils.join(paths, ".", 0, paths.length - 1), CharPool.DOT);
                boolean descField = false;
                if (path.endsWith("$desc")) {
                    path = path.substring(0, path.length() - "$desc".length());
                    descField = true;
                }

                if (StringUtils.equals((String)field.get("fieldName"), path)) {
                    dbFieldSetter.accept(condition, alias + "." + field.get("tableField"));
                    if (Objects.nonNull(subQuerySetter)) {
                        Map<String, Object> param = new HashMap<>();
                        param.put("ref", fieldRef);
                        param.put("field", field);
                        subQuerySetter.accept(condition, param);
                    }
                    if (Objects.nonNull(fieldNameSetter)) {
                        fieldNameSetter.accept(condition,
                            descField ? fieldPath.replace(CharPool.DOT, CharPool.UNDERLINE) + "$desc"
                                : fieldPath.replace(CharPool.DOT, CharPool.UNDERLINE));
                    }
                    if (Objects.nonNull(fieldExtSetter)) {
                        fieldExtSetter.accept(condition, field);
                    }
                    return loadJoinPath;
                }
            }
            throw new IllegalArgumentException("主模型: " + mainModel + "，找不到字段引用:" + path + ", 全路径: " + fieldPath);
        }
    }

    public static boolean prepareQueryCondition(ListQueryParam.QueryCondition query, String mainModel) {
        return prepareField(query, query.getField(), mainModel, ListQueryParam.QueryCondition::setDbField,
            ListQueryParam.QueryCondition::setFieldName);
    }

    public static boolean prepareOrderBy(ListQueryParam.OrderBy orderBy, String mainModel) {
        return prepareField(orderBy, orderBy.getField(), mainModel, ListQueryParam.OrderBy::setDbField, null);
    }

}