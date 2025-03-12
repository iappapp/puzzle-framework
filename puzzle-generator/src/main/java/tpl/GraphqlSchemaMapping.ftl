package ${package}.graphql;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import java.util.List;
<#if hasSub>
import ${package}.model.sub.*;
</#if>
import ${package}.model.*;

/**
* ${label} Graphql Schema Mapping
* 注意: 此类由代码生成器自动生成请不要手动修改，代码可能会被重新覆盖
*/
@Controller
public class ${name?cap_first}SchemaMapping {

<#if refs??>
<#list refs as ref >
    @SchemaMapping
    public ${ref.refClass.type?cap_first} ${ref.name}(${name?cap_first} model) {
        return model.$ref().${ref.name}();
    }
</#list>
</#if>
<#if reverseRefs??>
<#list reverseRefs as reverseRef >
    <#if reverseRef.refType == 'ONE_TO_ONE' >

    @SchemaMapping
    public ${reverseRef.refClass.type?cap_first} ${reverseRef.name}(${name?cap_first} model) {
        return model.$ref().${reverseRef.name}();
    }
    </#if>
    <#if reverseRef.refType == 'ONE_TO_MANY' >

    @SchemaMapping
    public List<${reverseRef.refClass.type?cap_first}> ${reverseRef.name}(${name?cap_first} model) {
        return model.$ref().${reverseRef.name}();
    }
    </#if>
</#list>
</#if>
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