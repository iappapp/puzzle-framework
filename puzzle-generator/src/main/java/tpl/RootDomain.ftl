package ${package}.model;


import ${package}.enums.*;
import ${package}.list.ListQueryParam;
import cn.codependency.framework.puzzle.model.RootModel;
import cn.codependency.framework.puzzle.common.Streams;
import cn.codependency.framework.puzzle.common.mask.Mask;
import org.springframework.context.annotation.Description;
import java.util.ArrayList;
<#list imports as import>
import ${import};
</#list>
<#list fields as field>
<#if field.modelClass??>
import ${field.modelClass.name};
</#if>
</#list>

/**
 * ${label}
 * 注意: 此类由代码生成器自动生成请不要手动修改，代码可能会被重新覆盖
 */
@Description("主模型: ${label}")
public class ${name} extends <#if parent??>${parent.name?cap_first}<#else>RootModel<${idType}></#if> {

    /**
     * 引用对象
     */
    private final $Ref $ref = new $Ref();

    /**
     * Action对象
     */
    private final ${name}$Act $act = new ${name}$Act(this);
<#list fields as field>

    /**
     * ${field.label}
     * ${tablePrefix + camelToDashed(name) + "." + fieldPrefix + camelToDashed(field.name)}
     */
    @Getter
    @Setter<#if field.extend?? && field.extend.mask?? && field.extend.mask>
    @Mask("${name}.${field.name}")</#if>
    <#if field.enumDefinition??>
    private ${field.enumDefinition.name}Enum ${field.name};
    <#elseif field.modelClass??>
    private ${field.modelClassSimpleName} ${field.name};
    <#else>
    private ${field.type.type?cap_first} ${field.name};
    </#if>

</#list>
<#list defaultFields as field>

    /**
    * ${field.label}
    * ${tablePrefix + camelToDashed(name) + "." + fieldPrefix + camelToDashed(field.name)}
    */
    @Getter
    @Setter
    <#if field.enumDefinition??>
    private ${field.enumDefinition.name}Enum ${field.name};
    <#else>
    private ${field.type.type?cap_first} ${field.name};
    </#if>
</#list>

    public $Ref $ref() {
        return $ref;
    }

    public ${name}$Act $act() {
        return $act;
    }

    public class $Ref <#if parent??>extends ${parent.name?cap_first}.$Ref<#else>implements Serializable</#if> {
<#if refs??>
    <#list refs as ref >

        <#if ref.refClass.simpleType == 'List'>
        public ${ref.refClass.type?cap_first} ${ref.name}() {return R.listByIds(${name}.this.${ref.refField}, ${ref.refClass.innerType.type?cap_first}.class);}

        public $Ref ${ref.name?uncap_first}(${ref.refClass.type?cap_first} list) {${name}.this.${ref.refField} = Objects.isNull(list) ? new ArrayList<>(): Streams.toList(list, ${ref.refClass.innerType.type?cap_first}::getId); return this;}
        <#else>
        public ${ref.refClass.type?cap_first} ${ref.name}() {return R.getById(${name}.this.${ref.refField}, ${ref.refClass.type?cap_first}.class);}

        public $Ref ${ref.name?uncap_first}(${ref.refClass.type?cap_first} ${ref.refClass.type?uncap_first}) {${name}.this.${ref.refField} = Objects.isNull(${ref.refClass.type?uncap_first}) ? null: ${ref.refClass.type?uncap_first}.getId(); return this;}
        </#if>
    </#list>
</#if>
<#if reverseRefs??>
    <#list reverseRefs as reverseRef >
        <#if reverseRef.refType == 'ONE_TO_ONE' >

        public ${reverseRef.refClass.type?cap_first} ${reverseRef.name}() { return R.getByRelation(new ${reverseRef.refClass.type}.$Query.Query${reverseRef.name?cap_first}By$${name?cap_first}$${reverseRef.refField?cap_first}(${name}.this.${reverseRef.refField}));}

        public $Ref ${reverseRef.name?uncap_first}(${reverseRef.refClass.type?cap_first} ${reverseRef.refClass.type?uncap_first}) {
            if (Objects.nonNull(${reverseRef.refClass.type?uncap_first})){
                ${reverseRef.refClass.type?uncap_first}.set${reverseRef.beRefField?cap_first}(${name?cap_first}.this.${reverseRef.refField});
                ${reverseRef.refClass.type?cap_first} $before = ${reverseRef.name}();
                if (Objects.nonNull($before) && !Objects.equals($before.getId(), ${reverseRef.refClass.type?uncap_first}.getId())) {
                    R.delete($before);
                }
            }
            return this;
        }

        </#if>
        <#if reverseRef.refType == 'ONE_TO_MANY' >
        public List<${reverseRef.refClass.type?cap_first}> ${reverseRef.name}() { return R.getByRelation(new ${reverseRef.refClass.type}.$Query.Query${reverseRef.name?cap_first}By$${name?cap_first}$${reverseRef.refField?cap_first}(${name}.this.${reverseRef.refField}));}

        public $Ref ${reverseRef.name?uncap_first}(${reverseRef.refClass.type?cap_first} ${reverseRef.refClass.type?uncap_first}) { if (Objects.nonNull(${reverseRef.refClass.type?uncap_first})) { ${reverseRef.refClass.type?uncap_first}.set${reverseRef.beRefField?cap_first}(${name?cap_first}.this.${reverseRef.refField}); } return this;}
        </#if>
    </#list>
</#if>
    }

    public static class $Query implements Serializable {

        @Getter @AllArgsConstructor public static class $SearchQuery implements ListRelationQuery<${name}, ${idType}> { private ListQueryParam param;}


    <#list refQueries as refQuery>
        <#if refQuery.refType == 'ONE_TO_ONE'>
        @Getter @AllArgsConstructor public static class Query${refQuery.name?cap_first}By$${refQuery.refClass.type}$Id implements SingleRelationQuery<${name}, ${idType}> { private ${refQuery.refIdType.type?cap_first} ${refQuery.refField?uncap_first};}
        </#if>
        <#if refQuery.refType == 'ONE_TO_MANY'>
        @Getter @AllArgsConstructor public static class Query${refQuery.name?cap_first}By$${refQuery.refClass.type}$Id implements ListRelationQuery<${name}, ${idType}> { private ${refQuery.refIdType.type?cap_first} ${refQuery.refField?uncap_first};}
        </#if>
    </#list>

    <#list queryDefines as query>

        <#if query.refType == 'ONE_TO_ONE'>
        @Getter @AllArgsConstructor public static class ${query.name?cap_first} implements SingleRelationQuery<${name}, ${idType}> { private ${query.refFieldType.type?cap_first} ${query.refField?uncap_first};}
        </#if>
        <#if query.refType == 'ONE_TO_MANY'>
        @Getter @AllArgsConstructor public static class ${query.name?cap_first} implements ListRelationQuery<${name}, ${idType}> { private ${query.refFieldType.type?cap_first} ${query.refField?uncap_first};}
        </#if>
    </#list>
    }
}


<#function camelToDashed(s)>
    <#return s
    <#-- "fooBar" to "foo_bar": -->
    ?replace('([a-z])([A-Z])', '$1_$2', 'r')
    <#-- "FOOBar" to "FOO_Bar": -->
    ?replace('([A-Z])([A-Z][a-z])', '$1_$2', 'r')
    <#-- All of those to "foo_bar": -->
    ?lower_case
    >
</#function>
