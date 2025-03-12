package cn.codependency.framework.puzzle.generator.config;

import cn.codependency.framework.puzzle.generator.constants.BasicFieldType;
import cn.codependency.framework.puzzle.generator.constants.ModelType;
import cn.codependency.framework.puzzle.generator.constants.RefType;
import cn.codependency.framework.puzzle.generator.field.*;
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Accessors(chain = true)
public class ModelDefinition implements GeneratorDefinition {

    public ModelDefinition(GeneratorRegistry registry, String name) {
        this.name = name;
        this.registry = registry;
        this.setBasicPackage(registry.getBasePackage() + "." + registry.getGeneratePackage());
        this.registry.register(this);
    }

    private GeneratorRegistry registry;

    private String name;

    private String label;

    private GeneratorFieldType idType = BasicFieldType.Long;

    private String basicPackage;

    private ModelType modelType = ModelType.ROOT;

    private String tablePrefix = "";

    private String fieldPrefix = "";

    private GeneratorFieldType parentIdType;

    private String parentIdField;

    private Boolean tenantIsolation = true;

    private List<GeneratorField> fields = new ArrayList<>();

    private List<GeneratorField> defaultFields = new ArrayList<>();

    private Map<String, ModelDefinition> subModels = new HashMap<>();

    private List<GeneratorRef> refs = new ArrayList<>();

    private List<GeneratorRef> refQueries = new ArrayList<>();

    private List<QueryDef> queryDefines = new ArrayList<>();

    private List<ReverseGeneratorRef> reverseRefs = new ArrayList<>();


    public String tableName() {
        return tablePrefix + StrUtil.toUnderlineCase(name);
    }

    public Object tableField(GeneratorField field) {
        return fieldPrefix + StrUtil.toUnderlineCase(field.getName());
    }

    public ModelDefinition setIdTypeClass(Class<?> clazz) {
        this.idType = new GeneratorFieldType(clazz.getName());
        return this;
    }

    public ModelDefinition addField(GeneratorField field) {
        this.fields.add(field);
        return this;
    }


    /**
     * 添加基础字段
     *
     * @param name
     * @param type
     * @param label
     * @return
     */
    public ModelDefinition addSimpleField(String name, FieldType type, String label) {
        this.fields.add(new SimpleGeneratorField(name, type, label));
        return this;
    }

    /**
     * 添加基础字段
     *
     * @param name
     * @param type
     * @param label
     * @return
     */
    public ModelDefinition addSimpleField(String name, FieldType type, String label, Extend extend) {
        this.fields.add(new SimpleGeneratorField(name, type, label, extend));
        return this;
    }

    /**
     * 添加基础字段
     *
     * @param name
     * @param clazz
     * @param label
     * @return
     */
    public ModelDefinition addSimpleField(String name, Class<?> clazz, String label) {
        this.fields.add(new SimpleGeneratorField(name, clazz.getName(), label));
        return this;
    }

    /**
     * 添加基础字段
     *
     * @param name
     * @param clazz
     * @param label
     * @return
     */
    public ModelDefinition addSimpleField(String name, Class<?> clazz, String label, Extend extend) {
        this.fields.add(new SimpleGeneratorField(name, clazz.getName(), label, extend));
        return this;
    }


    /**
     * 添加Url字段
     *
     * @param name
     * @param label
     * @return
     */
    public ModelDefinition addFileUrlField(String name, String label) {
        this.fields.add(new FileUrlGeneratorField(name, label));
        return this;
    }

    /**
     * 添加Url字段
     *
     * @param name
     * @param label
     * @return
     */
    public ModelDefinition addFileUrlField(String name, String label, Extend extend) {
        this.fields.add(new FileUrlGeneratorField(name, label, extend));
        return this;
    }

    /**
     * 添加枚举字段
     *
     * @param name
     * @param label
     * @param enumDefinition
     * @return
     */
    public ModelDefinition addEnumField(String name, String label, EnumDefinition enumDefinition) {
        this.fields.add(new EnumGeneratorField(name, label, enumDefinition));
        return this;
    }

    /**
     * 添加枚举字段
     *
     * @param name
     * @param label
     * @param enumDefinition
     * @return
     */
    public ModelDefinition addEnumField(String name, String label, EnumDefinition enumDefinition, Extend extend) {
        this.fields.add(new EnumGeneratorField(name, label, enumDefinition, extend));
        return this;
    }

    public <K, V> ModelDefinition addMappingField(String name, String label, MappingItem<K, V>... items) {
        MappingGeneratorField<K, V> kvMappingGeneratorField =
                new MappingGeneratorField<>(name, this.name, label, Arrays.stream(items).collect(Collectors.toList()));
        this.fields.add(kvMappingGeneratorField);
        this.registry.register(new MappingDefinition(name, this.name, kvMappingGeneratorField));
        return this;
    }

    public <K, V> ModelDefinition addMappingField(String name, String label, Extend extend, MappingItem<K, V>... items) {
        MappingGeneratorField<K, V> kvMappingGeneratorField =
                new MappingGeneratorField<>(name, this.name, label, extend, Arrays.stream(items).collect(Collectors.toList()));
        this.fields.add(kvMappingGeneratorField);
        this.registry.register(new MappingDefinition(name, this.name, kvMappingGeneratorField));
        return this;
    }


    public <K, V> ModelDefinition addMappingField(String name, String label, Extend extend, List<MappingItem<K, V>> items) {
        MappingGeneratorField<K, V> kvMappingGeneratorField =
                new MappingGeneratorField<>(name, this.name, label, extend, items);
        this.fields.add(kvMappingGeneratorField);
        this.registry.register(new MappingDefinition(name, this.name, kvMappingGeneratorField));
        return this;
    }

    /**
     * 添加子模型
     *
     * @param name
     * @param label
     * @param subModelDefinition
     * @param relateField
     * @param refType
     * @return
     */
    public ModelDefinition addSubModelField(String name, String label, ModelDefinition subModelDefinition, String relateField,
                                            RefType refType) {
        subModelDefinition.addSimpleField(relateField, idType, relateField);
        if (refType == RefType.ONE_TO_ONE) {
            this.fields.add(new SubModelGeneratorField(name, subModelDefinition, label, relateField));
        } else {
            this.fields.add(new SubModelListGeneratorField(name, subModelDefinition, label, relateField));
        }
        subModelDefinition.setParentIdType(this.getIdType());
        subModelDefinition.setParentIdField(relateField);
        this.addSubModel(subModelDefinition);
        return this;
    }


    /**
     * 单向引用
     *
     * @param name
     * @param label
     * @param refModel
     * @param refField
     * @return
     */
    public ModelDefinition addRefField(String name, String label, ModelDefinition refModel, String refField) {
        this.fields.add(new RefGeneratorField(refField, refModel, label, false, false));
        this.refs.add(new GeneratorRef(name, label, refField, refModel.idType, new GeneratorFieldType(refModel.fullClass())));
        return this;
    }

    /**
     * 单向引用
     *
     * @param name
     * @param label
     * @param refModel
     * @param refField
     * @return
     */
    public ModelDefinition addListRefField(String name, String label, ModelDefinition refModel, String refField) {
        this.fields.add(new RefGeneratorField(refField, refModel, label, true, true));
        this.refs.add(new GeneratorRef(name, label, refField, refModel.idType, new ListGeneratorFieldType(new GeneratorFieldType(refModel.fullClass())), RefType.ONE_TO_MANY));
        return this;
    }


    /**
     * 查询定义
     *
     * @param refField
     * @param refFieldType
     * @param refType
     * @return
     */
    public ModelDefinition addQueryDefine(String name, String refField, FieldType refFieldType, RefType refType) {
        this.getQueryDefines().add(new QueryDef(name, refField, refFieldType, refType));
        return this;
    }

    /**
     * 双边引用
     *
     * @param refName
     * @param refModelRefName
     * @param refField
     * @param refModel
     * @return
     */
    public ModelDefinition addDoubleSideRefField(String refName, String refLabel, String refModelRefName,
                                                 String refModelRefLabel, ModelDefinition refModel, String refField, RefType refType) {
        // 被引用增加引用
        refModel.addRefField(refModelRefName, refModelRefLabel, this, refField);
        refModel.getRefQueries().add(
                new GeneratorRef(refName, refLabel, refField, this.idType, new GeneratorFieldType(this.fullClass()), refType));
        this.reverseRefs.add(
                new ReverseGeneratorRef(refName, refLabel, "id", refField, this.idType, new GeneratorFieldType(refModel.fullClass()), refType));
        return this;
    }

    /**
     * 增加中间表引用
     *
     * @param refName
     * @param refType
     * @param modelA
     * @param modelAId
     * @param modelAName
     * @param modelALabel
     * @param modelB
     * @param modelBId
     * @param modelBName
     * @param modelBLabel
     * @return
     */
    public ModelDefinition addIntermediateRefField(String refName, String refLabel, RefType refType,
                                                   ModelDefinition modelA, String modelAId, String modelAName, String modelALabel, ModelDefinition modelB,
                                                   String modelBId, String modelBName, String modelBLabel) {
        // 两边模型对中间表添加双向引用
        modelA.addDoubleSideRefField(refName, refLabel, modelAName, modelALabel, this, modelAId,
                refType == RefType.MANY_TO_MANY ? RefType.ONE_TO_MANY : RefType.ONE_TO_ONE);
        modelB.addDoubleSideRefField(refName, refLabel, modelBName, modelBLabel, this, modelBId,
                refType == RefType.ONE_TO_ONE ? RefType.ONE_TO_ONE : RefType.ONE_TO_MANY);
        return this;
    }

    private ModelDefinition addSubModel(ModelDefinition subModel) {
        ModelDefinition modelDefinition = this.subModels.get(subModel.fullClass());
        if (Objects.isNull(modelDefinition)) {
            this.subModels.put(subModel.fullClass(), subModel);
        }
        return this;
    }

    public String fullClass() {
        if (modelType == ModelType.DOMAIN) {
            return basicPackage + ".model.sub." + name;
        }
        return basicPackage + ".model." + name;
    }
}
