package cn.codependency.framework.puzzle.common.diff;

import cn.codependency.framework.puzzle.common.DeepEquals;
import cn.codependency.framework.puzzle.common.Streams;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.function.Function;

public class CollectionDiff {

    private static final String NULL_KEY = "$NULL";

    /**
     * id转换为StringFuc
     *
     * @param idGetter
     * @param <Model>
     * @param <IdType>
     * @return
     */
    private static <Model, IdType> Function<Model, String> idGroupKeyFunc(Function<Model, IdType> idGetter) {
        return (t) -> parseIdString(idGetter.apply(t));
    }


    /**
     * 转换id
     *
     * @param id
     * @return
     */
    private static String parseIdString(Object id) {
        return Objects.toString(id, NULL_KEY);
    }

    /**
     * 对比两个列表的差异，返回需要新增，更新，删除的数据
     *
     * @param entities
     */
    public static <Model, IdType> CollectionDiffResult<Model> collectionDiff(Collection<Model> entities,
                                                                             Collection<Model> snapshotEntities, Function<Model, IdType> idGetter) {
        if (Objects.isNull(entities)) {
            entities = new ArrayList<>();
        }
        if (Objects.isNull(snapshotEntities)) {
            snapshotEntities = new ArrayList<>();
        }

        List<Model> deleteEntities = new ArrayList<>();
        List<ChangedRecord<Model>> updateEntities = new ArrayList<>();
        List<Model> insertEntities = new ArrayList<>();
        // 按id聚合
        Map<String, List<Model>> entitiesMap = Streams.groupBy(entities, idGroupKeyFunc(idGetter));
        // 删除，更新的实体
        for (Model snapshotEntity : snapshotEntities) {
            List<Model> entityMatched = entitiesMap.get(idGroupKeyFunc(idGetter).apply(snapshotEntity));
            if (CollectionUtils.isEmpty(entityMatched)) {
                deleteEntities.add(snapshotEntity);
            } else {
                Model newEntity = entityMatched.get(0);
                Set<String> changedFields = DeepEquals.getChangedFields(snapshotEntity, newEntity);
                if (CollectionUtils.isNotEmpty(changedFields)) {
                    updateEntities.add(new ChangedRecord<>(newEntity, changedFields));
                }
            }
        }
        // 新增的实体
        Map<String, List<Model>> dbEntitiesMap = Streams.groupBy(snapshotEntities, idGroupKeyFunc(idGetter));
        List<Model> notIdEntities = entitiesMap.get(NULL_KEY);
        if (CollectionUtils.isNotEmpty(notIdEntities)) {
            insertEntities.addAll(notIdEntities);
        }
        for (Model entity : entities) {
            String id = idGroupKeyFunc(idGetter).apply(entity);
            if (!Objects.equals(id, NULL_KEY)) {
                // id不为空, 且数据库实体中也不包含则新增
                if (CollectionUtils.isEmpty(dbEntitiesMap.get(id))) {
                    insertEntities.add(entity);
                }
            }
        }
        return new CollectionDiffResult<>(insertEntities, updateEntities, deleteEntities);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class CollectionDiffResult<T> {

        private List<T> insertList;

        private List<ChangedRecord<T>> updateList;

        private List<T> deleteList;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ChangedRecord<T> {
        /**
         * 记录
         */
        private T record;

        /**
         * 修改的字段
         */
        private Set<String> changedFields;
    }
}
