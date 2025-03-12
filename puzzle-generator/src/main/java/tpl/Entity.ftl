package ${package}.entity;

import cn.codependency.framework.puzzle.model.BaseDO;
import lombok.Getter;
import lombok.Setter;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.context.annotation.Description;
<#list imports as import>
import ${import};
</#list>

/**
 * ${label} 数据库实体
 * 注意: 此类由代码生成器自动生成请不要手动修改，代码可能会被重新覆盖
 */
@Getter
@Setter
@Description("数据库表实体: ${label}, Table: ${tablePrefix + camelToDashed(name)}")
<#if tenantIsolation >
@PuzzleTenant("${tenantId}")
</#if>
@TableName("${tablePrefix + camelToDashed(name)}")
public class ${name?cap_first}Entity extends BaseDO {

    /**
     * 主键id
     */
    @TableId
    private ${idType} id;
<#list fields as field>
    <#if field.type.simpleType == 'List'>
    /**
     * ${field.label}
     */
    @TableField("<#if safeQuot??>${safeQuot}</#if>${fieldPrefix + camelToDashed(field.name)}<#if safeQuot??>${safeQuot}</#if>")
    private String ${field.name?uncap_first};
    <#else>
    /**
     * ${field.label}
     */
    @TableField("<#if safeQuot??>${safeQuot}</#if>${fieldPrefix + camelToDashed(field.name)}<#if safeQuot??>${safeQuot}</#if>")
    private ${field.type.type?cap_first} ${field.name?uncap_first};
    </#if>
</#list>
<#list defaultFields as field>

    /**
    * ${field.label}
    */
    @TableField("<#if safeQuot??>${safeQuot}</#if>${fieldPrefix + camelToDashed(field.name)}<#if safeQuot??>${safeQuot}</#if>")
    private ${field.type.type?cap_first} ${field.name?uncap_first};
</#list>

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