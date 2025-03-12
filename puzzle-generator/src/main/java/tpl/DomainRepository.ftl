package ${package}.repository.sub;

<#list imports as import>
import ${import};
</#list>
import ${package}.enums.*;
import cn.codependency.framework.puzzle.model.BaseEnum;
import org.springframework.context.annotation.Description;
import cn.codependency.framework.puzzle.common.json.JsonUtils;
import ${package}.entity.${name?cap_first}Entity;
import ${basic_package}.mapper.${name?cap_first}Mapper;
import ${package}.model.sub.${name?cap_first};

/**
 * ${label}子模型仓储实现
 * 注意: 此类由代码生成器自动生成请不要手动修改，代码可能会被重新覆盖
 */
@Description("${label}子模型仓储实现")
@org.springframework.stereotype.Repository
public class ${name?cap_first}DomainRepository {

    @Resource
    protected RepositoryUtils repositoryUtils;
    @Resource
    protected ${name?cap_first}Mapper ${name?uncap_first}Mapper;
<#if parent??>
    @Resource
    protected ${parent.name?cap_first}DomainRepository ${parent.name?uncap_first}DomainRepository;
</#if>
<#if subFields?? && (subFields?size > 0)>
<#list subFields as subField>
    @Resource
    protected ${subField.targetType.type?cap_first}DomainRepository ${subField.targetType.type?uncap_first}DomainRepository;
</#list>
</#if>

    /**
     * 单个子模型保存逻辑实现
     *
     * @param ${name?uncap_first}Agg
     */
    public void ${name?uncap_first}Save(Aggregate<? extends ${name?cap_first}> ${name?uncap_first}Agg) {
        repositoryUtils.updateOrInsert(${name?uncap_first}Agg, this::_${name?uncap_first}Model2${name?cap_first}Entity, ${name?cap_first}::setId, ${name?cap_first}Entity::getId, ${name?cap_first}Entity::setId, ${name?uncap_first}Mapper);
        <#if subFields?? && (subFields?size > 0)>
        <#list subFields as subField>
        <#if subField.isCollection == true >
        {
            CollectionAggregate<${subField.targetType.type?cap_first}> ${subField.name?uncap_first}CollAgg = ${name?uncap_first}Agg.getCollectionAggregate(${name?cap_first}::get${subField.name?cap_first});
            ${subField.name?uncap_first}CollAgg.getAggregate().stream().filter(Objects::nonNull).forEach(agg -> agg.set${subField.relateField?cap_first}(${name?uncap_first}Agg.getAggregate().getId()));
            <#if subField.seqField??>
            AtomicInteger seq = new AtomicInteger();
            List<${subField.targetType.type?cap_first}> setSequenceList = new ArrayList<>(${subField.name?uncap_first}CollAgg.getAggregate());
            <#if subField.seqField.direction == "DESC" >
            Collections.reverse(setSequenceList);
            </#if>
            setSequenceList.stream().filter(Objects::nonNull).forEach(agg -> agg.set${subField.seqField.name?cap_first}(seq.incrementAndGet()));
            </#if>
            ${subField.targetType.type?uncap_first}DomainRepository.${subField.targetType.type?uncap_first}Save(${subField.name?uncap_first}CollAgg);
        }
        </#if>
        <#if subField.isCollection == false >
        Aggregate<${subField.targetType.type?cap_first}> ${subField.name?uncap_first}Agg = ${name?uncap_first}Agg.getAggregate(${name?cap_first}::get${subField.name?cap_first});
        if (Objects.nonNull(${subField.name?uncap_first}Agg.getAggregate())) {
            ${subField.name?uncap_first}Agg.getAggregate().set${subField.relateField?cap_first}(${name?uncap_first}Agg.getAggregate().getId());
        }
        ${subField.targetType.type?uncap_first}DomainRepository.${subField.targetType.type?uncap_first}Save(${subField.name?uncap_first}Agg);
        </#if>
        </#list>
        </#if>
        <#if parent?? >
        ${parent.name?uncap_first}DomainRepository.${parent.name?uncap_first}Save(${name?uncap_first}Agg);
        </#if>
    }

    /**
     * 子模型批量保存逻辑实现
     *
     * @param ${name?uncap_first}Agg
     */
    public void ${name?uncap_first}Save(CollectionAggregate<? extends ${name?cap_first}> ${name?uncap_first}Agg) {
        for (Aggregate<? extends ${name?cap_first}> subAggregate : ${name?uncap_first}Agg.fetchAggregateListWithDeleted(${name?cap_first}::getId)) {
            ${name?uncap_first}Save(subAggregate);
        }
    }

    <#if parentIdField?? >

    /**
     * 通过父模型id查询模型对象
     *
     * @param parentId
     * @return
     */
    public ${name?cap_first} getByParentId(${parentIdType} parentId) {
        List<${name?cap_first}Entity> entities = ${name?uncap_first}Mapper.$listByParentId(parentId);
        ${name?cap_first}Entity entity = null;
        if (entities == null || entities.size() == 0) {
            return null;
        }
        entity = entities.get(0);
        ${name?cap_first} model = _${name?uncap_first}Entity2${name?cap_first}Model(entity);
        <#if subFields?? && (subFields?size > 0)>
        <#list subFields as subField>
        <#if subField.isCollection >
        model.set${subField.name?cap_first}(${subField.targetType.type?uncap_first}DomainRepository.listByParentId(model.getId()));
        </#if>
        <#if !subField.isCollection >
        model.set${subField.name?cap_first}(${subField.targetType.type?uncap_first}DomainRepository.getByParentId(model.getId()));
        </#if>
        </#list>
        </#if>
        <#if parent?? >
        ${parent.name?uncap_first}DomainRepository.fillModels(new ArrayList<${name?cap_first}>(){{add(model);}});
        </#if>
        return model;
    }

    /**
     * 通过父模型id查询模型对象列表
     *
     * @param parentId
     * @return
     */
    public List<${name?cap_first}> listByParentId(${parentIdType} parentId) {
        List<${name?cap_first}Entity> entities = ${name?uncap_first}Mapper.$listByParentId(parentId);
        List<${name?cap_first}> models = entities.stream().map(this::_${name?uncap_first}Entity2${name?cap_first}Model).collect(Collectors.toList());
        <#if subFields?? && (subFields?size > 0)>
        for (${name?cap_first} model : models) {
            <#list subFields as subField>
            <#if subField.isCollection >
            model.set${subField.name?cap_first}(${subField.targetType.type?uncap_first}DomainRepository.listByParentId(model.getId()));
            </#if>
            <#if !subField.isCollection >
            model.set${subField.name?cap_first}(${subField.targetType.type?uncap_first}DomainRepository.getByParentId(model.getId()));
            </#if>
            </#list>
        }
        </#if>
    <#if parent?? >
        ${parent.name?uncap_first}DomainRepository.fillModels(models);
    </#if>
        return models;
    }

    </#if>

    /**
     * 数据库实体转换为模型
     *
     * @params entity 数据库实体
     * @return 模型对象
     */
    private ${name?cap_first} _${name?uncap_first}Entity2${name?cap_first}Model(${name?cap_first}Entity entity) {
        ${name?cap_first} model = new ${name?cap_first}();
        model.setId(entity.getId());
        <#list fields as field>
        <#if field.type.simpleType == 'List'>
        model.set${field.name?cap_first}(JsonUtils.toList(entity.get${field.name?cap_first}(), ${field.type.innerType.type}.class));
        <#else>
        <#if field.enumDefinition??>
        model.set${field.name?cap_first}(${field.enumDefinition.name?cap_first}Enum.keyOf(entity.get${field.name?cap_first}()));
        <#elseif field.modelClass??>
        model.set${field.name?cap_first}(JsonUtils.toObject(entity.get${field.name?cap_first}(), ${field.modelClass.name}.class, true));
        <#else>
        model.set${field.name?cap_first}(entity.get${field.name?cap_first}());
        </#if>
        </#if>
        </#list>
        <#list defaultFields as field>
        <#if field.type.simpleType == 'List'>
        model.set${field.name?cap_first}(JsonUtils.toList(entity.get${field.name?cap_first}(), ${field.type.innerType.type}.class));
        <#else>
        <#if field.enumDefinition??>
        model.set${field.name?cap_first}(${field.enumDefinition.name?cap_first}Enum.keyOf(entity.get${field.name?cap_first}()));
        <#elseif field.modelClass??>
        model.set${field.name?cap_first}(JsonUtils.toObject(entity.get${field.name?cap_first}(), ${field.modelClass.name}.class, true));
        <#else>
        model.set${field.name?cap_first}(entity.get${field.name?cap_first}());
        </#if>
        </#if>
        </#list>
        return model;
    }

    /**
     * 模型转换为数据库实体
     *
     * @params model 模型对象
     * @return 数据库实体
     */
    private ${name?cap_first}Entity _${name?uncap_first}Model2${name?cap_first}Entity(${name?cap_first} model) {
        ${name?cap_first}Entity entity = new ${name?cap_first}Entity();
        entity.setId(model.getId());
        <#list fields as field>
        <#if field.type.simpleType == 'List'>
        entity.set${field.name?cap_first}(JsonUtils.toJson(model.get${field.name?cap_first}()));
        <#else>
        <#if field.enumDefinition??>
        entity.set${field.name?cap_first}(BaseEnum.getKey(model.get${field.name?cap_first}()));
        <#elseif field.modelClass??>
        entity.set${field.name?cap_first}(JsonUtils.toJson(model.get${field.name?cap_first}(), true));
        <#else>
        entity.set${field.name?cap_first}(model.get${field.name?cap_first}());
        </#if>
        </#if>
        </#list>
        <#list defaultFields as field>
        <#if field.type.simpleType == 'List'>
        entity.set${field.name?cap_first}(JsonUtils.toJson(model.get${field.name?cap_first}()));
        <#else>
        <#if field.enumDefinition??>
        entity.set${field.name?cap_first}(BaseEnum.getKey(model.get${field.name?cap_first}()));
        <#elseif field.modelClass??>
        entity.set${field.name?cap_first}(JsonUtils.toJson(model.get${field.name?cap_first}(), true));
        <#else>
        entity.set${field.name?cap_first}(model.get${field.name?cap_first}());
        </#if>
        </#if>
        </#list>
        return entity;
    }

    /**
    * 填充模型
    * @params models
    */
    public void fillModels(List<? extends ${name?cap_first}> models) {
        Map<${idType}, ${name?cap_first}> modelMap = models.stream().collect(Collectors.toMap(${name?cap_first}::getId, Function.identity()));
        List<${name?cap_first}Entity> entities =  ${name?uncap_first}Mapper.selectBatchIds(modelMap.keySet());
        for (${name?cap_first}Entity entity : entities) {
            ${name?cap_first} model = modelMap.get(entity.getId());
            _${name?uncap_first}Entity2${name?cap_first}Model(model, entity);
        }
        <#if parent??>
        ${parent.name?uncap_first}DomainRepository.fillModels(models);
        </#if>
    }

    /**
    * 数据库实体转换为模型
    *
    * @params entity 数据库实体
    * @return 模型对象
    */
    private ${name?cap_first} _${name?uncap_first}Entity2${name?cap_first}Model(${name?cap_first} model, ${name?cap_first}Entity entity) {
        <#list fields as field>
        <#if field.type.simpleType == 'List'>
        model.set${field.name?cap_first}(JsonUtils.toList(entity.get${field.name?cap_first}(), ${field.type.innerType.type}.class));
        <#else>
        <#if field.enumDefinition??>
        model.set${field.name?cap_first}(${field.enumDefinition.name?cap_first}Enum.keyOf(entity.get${field.name?cap_first}()));
        <#elseif field.modelClass??>
        model.set${field.name?cap_first}(JsonUtils.toObject(entity.get${field.name?cap_first}(), ${field.modelClass.name}.class, true));
        <#else>
        model.set${field.name?cap_first}(entity.get${field.name?cap_first}());
        </#if>
        </#if>
        </#list>
        <#list defaultFields as field>
        <#if field.type.simpleType == 'List'>
        model.set${field.name?cap_first}(JsonUtils.toList(entity.get${field.name?cap_first}(), ${field.type.innerType.type}.class));
        <#else>
        <#if field.enumDefinition??>
        model.set${field.name?cap_first}(${field.enumDefinition.name?cap_first}Enum.keyOf(entity.get${field.name?cap_first}()));
        <#elseif field.modelClass??>
        model.set${field.name?cap_first}(JsonUtils.toObject(entity.get${field.name?cap_first}(), ${field.modelClass.name}.class, true));
        <#else>
        model.set${field.name?cap_first}(entity.get${field.name?cap_first}());
        </#if>
        </#if>
        </#list>
        return model;
    }
}