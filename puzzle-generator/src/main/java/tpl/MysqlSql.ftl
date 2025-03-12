create table ${tablePrefix + camelToDashed(name)} (
    `id` ${idColumnType.columnType} NOT NULL COMMENT '主键id',
<#list fields as field>
    `${fieldPrefix + camelToDashed(field.field.name)}` ${field.columnType?upper_case} COMMENT '${field.field.label}',
</#list>
<#list defaultFields as field>
    `${fieldPrefix + camelToDashed(field.field.name)}` ${field.columnType?upper_case} COMMENT '${field.field.label}',
</#list>
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '${label}表';

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