package ${package}.repository;

<#list imports as import>
import ${import};
</#list>
import ${package}.enums.*;
import java.util.*;
import ${package}.util.ListQueryUtils;
import cn.codependency.framework.puzzle.model.BaseEnum;
import cn.codependency.framework.puzzle.common.json.JsonUtils;
import cn.codependency.framework.puzzle.common.Streams;
import org.springframework.context.annotation.Description;
import ${package}.entity.${name?cap_first}Entity;
import ${basic_package}.mapper.${name?cap_first}Mapper;
import ${package}.model.${name?cap_first};
<#if subFields??>
<#list subFields as subField>
import ${package}.repository.sub.${subField.targetType.type?cap_first}DomainRepository;
</#list>
</#if>

/**
 * ${label}仓储实现
 * 注意: 此类由代码生成器自动生成请不要手动修改，代码可能会被重新覆盖
 */
@Description("${label} 主模型仓储实现")
public class ${name?cap_first}GenerateRepository implements Repository<${name?cap_first}, ${idType}> {

    @Resource
    protected RepositoryUtils repositoryUtils;

    @Resource
    protected IdGeneratorFactory idGeneratorFactory;

    @Resource
    protected ${name?cap_first}Mapper ${name?uncap_first}Mapper;

    <#if parent??>
    @Resource
    protected ${parent.name?cap_first}GenerateRepository ${parent.name?uncap_first}GenerateRepository;
    </#if>

<#if subFields??>
<#list subFields as subField>
    @Resource
    protected ${subField.targetType.type?cap_first}DomainRepository ${subField.targetType.type?uncap_first}DomainRepository;
</#list>
</#if>

    @QueryMapping
    public List<${idType}> QueryBy$ListQueryUtils(${name?cap_first}.$Query.$SearchQuery query) {
        ListQueryUtils.prepareListQuery(${name?cap_first}.class, query.getParam());
        List<Map<String, Object>> maps = ${name?uncap_first}Mapper.$listQuery(query.getParam());
        return new ArrayList<>(new LinkedHashSet<>(Streams.toList(maps, m -> (${idType})m.get("id"))));
    }

    /**
     * id生成策略
     *
     * @param rootDomain
     * @return
     */
    @Override
    public IdGenerator<${idType}> getIdGenerator(${name?cap_first} rootDomain) {
        <#if parent?? >
        return ${parent.name?uncap_first}GenerateRepository.getIdGenerator(rootDomain);
        <#else>
        return idGeneratorFactory.getEntityIdGenerator(${name?cap_first}Entity.class);
        </#if>
    }

    /**
     * 模型保存逻辑实现
     *
     * @param ${name?uncap_first}Agg
     */
    @SaveMapping(${name?cap_first}.class)
    public void _${name?uncap_first}Save(AggregateRoot<? extends ${name?cap_first}> ${name?uncap_first}Agg) {
        repositoryUtils.updateOrInsert(${name?uncap_first}Agg, this::_${name?uncap_first}Model2${name?cap_first}Entity, ${name?cap_first}::setId, ${name?cap_first}Entity::getId, ${name?cap_first}Entity::setId, ${name?uncap_first}Mapper);
        <#if subFields??>
        <#list subFields as subField>
        <#if subField.isCollection >
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
        <#if !subField.isCollection >
        Aggregate<${subField.targetType.type?cap_first}> ${subField.name?uncap_first}Agg = ${name?uncap_first}Agg.getAggregate(${name?cap_first}::get${subField.name?cap_first});
        if (Objects.nonNull(${subField.name?uncap_first}Agg.getAggregate())) {
            ${subField.name?uncap_first}Agg.getAggregate().set${subField.relateField?cap_first}(${name?uncap_first}Agg.getAggregate().getId());
        }
        ${subField.targetType.type?uncap_first}DomainRepository.${subField.targetType.type?uncap_first}Save(${subField.name?uncap_first}Agg);
        </#if>
        </#list>
        </#if>
        <#if parent?? >
        ${parent.name?uncap_first}GenerateRepository.$${parent.name?uncap_first}Save(${name?uncap_first}Agg);
        </#if>
    }
<#list refQueries as refQuery>

    @QueryMapping
    public <#if refQuery.refType == 'ONE_TO_ONE'>${idType}<#else>List<${idType}></#if> queryBy$${refQuery.refClass.type}$Id(${name?cap_first}.$Query.Query${refQuery.name?cap_first}By$${refQuery.refClass.type}$Id query) {
        return ${name?uncap_first}Mapper.query${refQuery.name?cap_first}By$${refQuery.refClass.type}$Id(query.get${refQuery.refField?cap_first}());
    }
</#list>
<#list queryDefines as query>

    @QueryMapping
    public <#if query.refType == 'ONE_TO_ONE'>${idType}<#else>List<${idType}></#if> ${query.name?uncap_first}(${name?cap_first}.$Query.${query.name?cap_first} query) {
        return ${name?uncap_first}Mapper.${query.name?uncap_first}(query.get${query.refField?cap_first}());
    }
</#list>


    /**
     * 批量模型查询实现
     *
     * @param ids
     * @return 模型列表
     */
    @Override
    public List<${name?cap_first}> selectByIds(List<${idType}> ids) {
        List<${name?cap_first}Entity> entities = ${name?uncap_first}Mapper.selectBatchIds(ids);
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
        <#if parent??>
        ${parent.name?uncap_first}GenerateRepository.fillModels(models);
        </#if>
        return models;
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
}