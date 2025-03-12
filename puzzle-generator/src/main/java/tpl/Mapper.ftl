package ${package}.mapper;

import org.springframework.context.annotation.Description;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${package}.list.ListQueryParam;
import ${package}.entity.${name?cap_first}Entity;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * ${label} Mapper
 * 注意: 此类由代码生成器自动生成请不要手动修改，代码可能会被重新覆盖
 */
@Description("数据库表Mapper: ${label} Mapper, Table: ${tablePrefix + camelToDashed(name)}")
public interface ${name?cap_first}GenerateMapper extends BaseMapper<${name?cap_first}Entity> {

    <#if parentIdField??>

    <#if seqField??>
    @Select("<script>select id<#list fields as field>, <#if safeQuot??>${safeQuot}</#if>${fieldPrefix + camelToDashed(field.name)}<#if safeQuot??>${safeQuot}</#if></#list><#list defaultFields as field>, <#if safeQuot??>${safeQuot}</#if>${fieldPrefix + camelToDashed(field.name)}<#if safeQuot??>${safeQuot}</#if></#list> from ${tablePrefix + camelToDashed(name)} <where> <#if safeQuot??>${safeQuot}</#if>${fieldPrefix + camelToDashed(parentIdField)?uncap_first}<#if safeQuot??>${safeQuot}</#if>=${r"#{0}"} </where> order by <#if safeQuot??>${safeQuot}</#if>${fieldPrefix + camelToDashed(seqField.name)}<#if safeQuot??>${safeQuot}</#if> ${seqField.direction}</script>")
    List<${name?cap_first}Entity> $listByParentId(${parentIdType.type?cap_first} parentId);
    <#else >
    @Select("<script>select id<#list fields as field>, <#if safeQuot??>${safeQuot}</#if>${fieldPrefix + camelToDashed(field.name)}<#if safeQuot??>${safeQuot}</#if></#list><#list defaultFields as field>, <#if safeQuot??>${safeQuot}</#if>${fieldPrefix + camelToDashed(field.name)}<#if safeQuot??>${safeQuot}</#if></#list> from ${tablePrefix + camelToDashed(name)} <where> <#if safeQuot??>${safeQuot}</#if>${fieldPrefix + camelToDashed(parentIdField)?uncap_first}<#if safeQuot??>${safeQuot}</#if>=${r"#{0}"} </where></script>")
    List<${name?cap_first}Entity> $listByParentId(${parentIdType.type?cap_first} parentId);
    </#if>
    <#else>
    List<Map<String, Object>> $listQuery(ListQueryParam param);
    </#if>
    <#list refQueries as refQuery>

    @Select("<script>select id from ${tablePrefix + camelToDashed(name)} <where> <#if safeQuot??>${safeQuot}</#if>${fieldPrefix + camelToDashed(refQuery.refField?uncap_first)}<#if safeQuot??>${safeQuot}</#if>=${r"#{0}"}</where></script>")
    <#if refQuery.refType == 'ONE_TO_ONE'>${idType}<#else>List<${idType}></#if> query${refQuery.name?cap_first}By$${refQuery.refClass.type}$Id(${refQuery.refIdType.type?cap_first} ${refQuery.refField?uncap_first});
    </#list>
    <#list queryDefines as query>

    @Select("<script>select id from ${tablePrefix + camelToDashed(name)} <where> <#if safeQuot??>${safeQuot}</#if>${fieldPrefix + camelToDashed(query.refField?uncap_first)}<#if safeQuot??>${safeQuot}</#if>=${r"#{0}"}</where></script>")
    <#if query.refType == 'ONE_TO_ONE'>${idType}<#else>List<${idType}></#if> ${query.name?uncap_first}(${query.refFieldType.type?cap_first} ${query.refField?uncap_first});
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