## Puzzle Framework

Puzzle Framework 是一个面向领域建模（DDD）场景的 Java 多模块框架，围绕“模型定义 → 代码生成 → 仓储访问 → 事件驱动 → 多租户扩展”提供一套可组合能力。

项目当前采用 Maven 多模块组织，核心定位是：

- 用 **Kotlin DSL + FreeMarker 模板** 统一生成模型、仓储、Mapper、SQL、GraphQL Schema 等样板代码
- 用 **Repository 抽象 + MyBatis-Plus** 承载聚合根读写
- 用 **EventCenter/EventBus** 构建领域事件发布与消费机制
- 用 **TenantContext + 注解** 支持租户隔离能力

---

## 1. 核心能力概览

- **领域模型驱动生成**：通过 `GeneratorRegistry`、`ModelDefinition`、DSL Builder 定义模型并生成代码
- **仓储层统一抽象**：通过 `Repository<T, IdType>` 约束聚合根查询/持久化行为
- **领域事件机制**：提供同步/异步事件总线、事件中心、监听器注册能力
- **多租户支持**：支持租户上下文管理及模型租户字段注入
- **脚本扩展基础**：提供基于 ANTLR 的 PuzzleScript 语法基础和模型读写引擎

---

## 2. 模块说明（重点）

### `puzzle-common`

公共基础模块，提供通用工具与基础设施能力。

主要内容：

- 错误与异常体系：`Errors`、`BizException`、`SystemError`
- ID 生成能力：`IdGenerator`、`SnowflakeIdGenerator`、`IdGeneratorFactory`
- JSON 与日期处理：`JsonUtils`、`JacksonDateFormat`
- 国际化支持：`I18n`、`MessageUtils`、`PuzzleI18nMessageSource`
- 其他通用工具：复制、Diff、校验、流式工具等

适用场景：作为其他模块的基础依赖，避免业务项目重复建设底层工具能力。

---

### `puzzle-model`

领域建模基础模块，提供统一模型抽象与基础对象定义。

主要内容：

- 模型基类：`Model`、`RootModel`
- 基础对象：`BaseDO`、`BaseDTO`、`BaseVO`、`BaseParam`
- 返回与分页：`Result`、`PagedParam`
- 枚举能力：`BaseEnum`

适用场景：为生成代码和仓储层提供统一类型约束。

---

### `puzzle-repository`

仓储模块，负责聚合根数据访问、自动提交拦截、数据权限扩展等能力。

主要内容：

- 仓储抽象：`Repository<T, IdType>`、`RepositoryFactory`
- 快捷查询入口：`R`
- 注解开关：`@EnableRepository`、`@EnableAutoRepository`、`@EnableDataPermission`、`@EnableDefaultIdGenerator`
- 自动化处理：`RepositoryProcessor`、`RepositoryAutoCommitIntercept`、`RepositoryAutoClearIntercept`
- SQL 扩展：批量插入/删除/更新、Merge 策略

适用场景：统一管理聚合根的读取、保存、批量操作与扩展拦截逻辑。

---

### `puzzle-generator`

代码生成模块，是整个框架的“模型驱动核心”。

主要内容：

- DSL 定义能力：`registry(...)`、`model(...)`、`fields(...)`、`refs(...)`
- 模型配置：`ModelDefinition`、`EnumDefinition`、`MappingDefinition`
- 生成器：`ModelCodeGenerator`、`EntityCodeGenerator`、`MapperCodeGenerator`、`RepositoryCodeGenerator`
- GraphQL 生成：`GraphqlSchemaGenerator`、`GraphqlSchemaMappingGenerator`
- SQL 生成：`SqlGenerator`
- 模板体系：`src/main/java/tpl/*.ftl` 及 `tpl/common/*.ftl`

输出内容（典型）：

- 模型代码（Model / Action / Repository）
- 持久化代码（Entity / Mapper / Mapper.xml）
- GraphQL Schema 与 Mapping
- SQL 脚本

适用场景：快速搭建中后台领域模型与 CRUD/查询基础设施，降低手工样板代码成本。

---

### `puzzle-event`

事件模块，提供领域事件发布、总线分发、处理器扫描等机制。

主要内容：

- 事件抽象：`Event`、`BaseEvent`
- 事件中心：`EventCenter`、`DefaultEventCenter`
- 总线机制：`SyncEventBus`、`AsyncEventBus`
- 监听器工厂与处理：`EventListenerFactory`、`EventProcessor`
- 自动配置与注解：`@EnableEvent`、`@EventHandler`

适用场景：领域行为解耦、异步通知、跨模块业务联动。

---

### `puzzle-tenant`

多租户模块，提供租户上下文与租户注解标记能力。

主要内容：

- `Tenant`、`TenantContext`（ThreadLocal 维度租户上下文）
- `@PuzzleTenant`（声明租户字段）

适用场景：SaaS 场景下租户隔离、租户标识透传。

---

### `puzzle-script`

脚本模块，提供 PuzzleScript 语言基础与模型操作引擎。

主要内容：

- 语法定义：`PuzzleScript.g4`（ANTLR）
- 解析产物：Lexer / Parser / Visitor
- 运行时能力：`ModelEngine`（按模型名 + 主键读写字段）

说明：

- `PuzzleScriptEngine` 当前是空实现骨架，适合后续扩展脚本执行器。

适用场景：规则脚本化、低代码流程编排、动态字段读写。

---

## 3. 模块依赖关系（简化）

```text
puzzle-common  <- 基础能力
	 ↑
puzzle-model   <- 模型抽象
	 ↑
puzzle-repository <- 仓储访问（依赖 tenant/common/model）
	 ↑
puzzle-script  <- 脚本运行时（依赖 repository + generator）

puzzle-generator <- 代码生成（依赖 common）
puzzle-event     <- 事件机制（依赖 common）
puzzle-tenant    <- 多租户上下文
```

> 注：上图为逻辑简化图，实际以各模块 `pom.xml` 为准。

---

## 4. 技术栈与版本基线

- JDK: `1.8`
- Maven: `3.6+`（建议）
- Spring Boot Parent: `2.4.2`
- MyBatis-Plus: `3.5.7`
- Kotlin: `1.8.10`（主要用于 generator DSL）
- FreeMarker: `2.3.30`
- ANTLR4: `4.13.1`

---

## 5. 快速开始

### 5.1 克隆与构建

```bash
git clone <your-repo-url>
cd puzzle-framework
mvn clean install -DskipTests
```

### 5.2 单模块构建（示例）

```bash
mvn -pl puzzle-generator -am clean package -DskipTests
```

### 5.3 在业务工程引入（示例）

按需引入模块依赖，例如：

```xml
<dependency>
	<groupId>cn.codependency.framework.puzzle</groupId>
	<artifactId>puzzle-repository</artifactId>
	<version>${puzzle.version}</version>
</dependency>

<dependency>
	<groupId>cn.codependency.framework.puzzle</groupId>
	<artifactId>puzzle-event</artifactId>
	<version>${puzzle.version}</version>
</dependency>
```

---

## 6. 常见启用方式

### 6.1 启用仓储

```java
@Configuration
@EnableAutoRepository
@EnableDefaultIdGenerator
public class RepositoryConfig {
}
```

如需数据权限扩展：

```java
@Configuration
@EnableDataPermission
public class DataPermissionConfig {
}
```

### 6.2 启用事件

```java
@Configuration
@EnableEvent
public class EventConfig {
}
```

事件处理器：

```java
@EventHandler
public class OrderCreatedHandler {
	// handle event
}
```

---

## 7. 代码生成（Generator）使用思路

生成器核心流程：

1. 使用 DSL 定义 `registry`（基础包、生成包、数据库、多租户配置）
2. 通过 `models` / `enums` / `refs` 定义领域模型结构
3. 调用 `registry.generate()` 生成代码

DSL 形态示例（示意）：

```kotlin
val registry = registry(
	basicPackage = "com.demo",
	generatePackage = "generated",
	multiTenant = true,
	tenantId = "tenant_id"
) {
	// defaultFields {}
	// enums {}
	// models {}
}

registry.generate("/path/to/project")
```

生成器会在目标项目下生成 Java 代码、Mapper/XML、SQL、GraphQL Schema 等文件，并在存在旧目录时自动做备份。

---

## 8. 脚本能力（Script）说明

`puzzle-script` 基于 ANTLR 提供了 `if/for/when/callFunction` 等语法结构，适合后续做：

- 规则表达式执行
- 条件分支流程编排
- 与模型读写引擎联动

目前可直接使用 `ModelEngine` 实现“按模型 + 主键”进行字段读写；完整脚本执行引擎可在 `PuzzleScriptEngine` 基础上继续扩展。

---

## 9. 推荐接入路径

建议按以下顺序落地：

1. 先引入 `puzzle-model` + `puzzle-common` 建立统一基础类型
2. 接入 `puzzle-repository` 统一聚合根读写
3. 用 `puzzle-generator` 将核心模型代码自动化
4. 接入 `puzzle-event` 解耦领域动作与副作用
5. SaaS 场景开启 `puzzle-tenant` 多租户能力
6. 最后按需引入 `puzzle-script` 做动态规则能力

---

## 10. 开发与维护建议

- 保持“模型定义”作为唯一事实源，避免手写代码与生成代码冲突
- 生成目录建议加入代码审查流程，避免模板变更引起隐藏行为变化
- 事件处理建议保证幂等，便于重试与故障恢复
- 多租户场景务必在请求入口处设置并清理 `TenantContext`

---

## 11. 许可证

Apache License 2.0