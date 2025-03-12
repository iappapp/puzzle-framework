"${label}"
type ${name} {
    id: ID
<#list fields as field>
<#if field.enumDefinition??>
    """
    ${field.label}
    ${field.enumDefinition.enumDesc}
    """
    ${field.name}: Enum
<#elseif field.modelClass??>
    <#if field.extend?? && field.extend.internal?? && field.extend.internal>
    <#else>
    """
    ${field.label}
    """
    ${field.name}: ${field.modelClassSimpleName}
    </#if>
<#else>
    <#if field.extend?? && field.extend.internal?? && field.extend.internal>
    <#else>
    """
    ${field.label}
    """
    ${field.name}: ${field.type.graphqlType?cap_first} <#if field.extend?? && field.extend.mask?? && field.extend.mask>@mask(field: "${name}.${field.name}")</#if>
    </#if>
</#if>

</#list>
<#list defaultFields as field>
<#if field.enumDefinition??>
    """
    ${field.label}
    ${field.enumDefinition.enumDesc}
    """
    ${field.name}: Enum
<#else>
    <#if field.extend?? && field.extend.internal?? && field.extend.internal>
    <#else>
    """
    ${field.label}
    """
    ${field.name}: ${field.type.graphqlType?cap_first}
    </#if>
</#if>

</#list>

<#if refs??>
<#list refs as ref >
    """
    ${ref.label}
    """
    ${ref.name}: ${ref.refClass.graphqlType?cap_first}

</#list>
<#list reverseRefs as reverseRef >
<#if reverseRef.refType == 'ONE_TO_ONE' >
    """
    ${reverseRef.label}
    """
    ${reverseRef.name}: ${reverseRef.refClass.type?cap_first}
</#if>
<#if reverseRef.refType == 'ONE_TO_MANY' >
    """
    ${reverseRef.label}
    """
    ${reverseRef.name}: [${reverseRef.refClass.type?cap_first}]
</#if>

</#list>
</#if>
}

<#if modelType == 'ROOT'>
type ${name}PagedResult{
    list: [${name}]
    total: Int
}
</#if>

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