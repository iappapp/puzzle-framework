create table "${tablePrefix + camelToDashed(name)}" (
    id ${idColumnType.columnType} NOT NULL,
<#list fields as field>
    "${fieldPrefix + camelToDashed(field.field.name)}" ${field.columnType?upper_case},
</#list>
<#list defaultFields as field>
    "${fieldPrefix + camelToDashed(field.field.name)}" ${field.columnType?upper_case},
</#list>
    primary key(id)
);

comment on table "${tablePrefix + camelToDashed(name)}" is '${label}表';
<#list fields as field>
comment on column "${tablePrefix + camelToDashed(name)}"."${fieldPrefix + camelToDashed(field.field.name)}" IS '${field.field.label}';
</#list>
<#list defaultFields as field>
comment on column "${tablePrefix + camelToDashed(name)}"."${fieldPrefix + camelToDashed(field.field.name)}" IS '${field.field.label}';
</#list>

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