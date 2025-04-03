package cn.codependency.framework.puzzle.generator.dsl

import cn.codependency.framework.puzzle.generator.constants.Database
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry

// Module DSL 构建函数
fun module(block: GeneratorModuleBuilder.() -> Unit): GeneratorModuleBuilder {
    return GeneratorModuleBuilder().apply(block)
}

// Registry DSL 构建函数
fun registry(
    basicPackage: String,
    generatePackage: String = "generated",
    sqlDir: String = "sql",
    database: Database = Database.Mysql,
    tablePrefix: String = "",
    fieldPrefix: String = "",
    multiTenant: Boolean = false,
    tenantId: String = "tenant_id",
    block: GeneratorRegistryBuilder.() -> Unit = {}
): GeneratorRegistry {
    return GeneratorRegistryBuilder(
        basicPackage = basicPackage,
        generatePackage = generatePackage,
        sqlDir = sqlDir,
        database = database,
        tablePrefix = tablePrefix,
        fieldPrefix = fieldPrefix,
        multiTenant = multiTenant,
        tenantId = tenantId
    ).apply(block).build()
}
