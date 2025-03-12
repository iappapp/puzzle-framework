package ${package}.model.sub;

import ${package}.enums.*;
import cn.codependency.framework.puzzle.model.Model;
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
@Description("子模型: ${label}")
public class ${name?cap_first} extends <#if parent??>${parent.name?cap_first}<#else>Model</#if> {

    /**
     * 引用对象
     */
    private final $Ref $ref = new $Ref();

    /**
     * 子模型 主键id
     */
    @Getter
    @Setter
    private ${idType} id;
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

    public class $Ref <#if parent??>extends ${parent.name?cap_first}.$Ref<#else>implements Serializable</#if> {
<#if refs??>
    <#list refs as ref >
        <#if ref.refClass.simpleType == 'List'>
        public ${ref.refClass.type?cap_first} ${ref.name}() {return R.listByIds(${name}.this.${ref.refField}, ${ref.refClass.innerType.type?cap_first}.class);}

        public $Ref ${ref.name?uncap_first}(${ref.refClass.type?cap_first} list) {${name}.this.${ref.refField} = Objects.isNull(list) ? new ArrayList<>(): Streams.toList(list, ${ref.refClass.innerType.type?cap_first}::getId); return this;}
        <#else>
        public ${ref.refClass.type?cap_first} ${ref.name}() {return R.getById(${name}.this.${ref.refField}, ${ref.refClass.type?cap_first}.class);}

        public void set${ref.name?cap_first}(${ref.refClass.type?cap_first} ${ref.refClass.type?uncap_first}) {${name}.this.${ref.refField} = ${ref.refClass.type?uncap_first}.getId();}
        </#if>
    </#list>
</#if>
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