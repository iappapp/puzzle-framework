package ${basic_package}.repository;

import cn.codependency.framework.puzzle.repository.Repository;
import ${package}.model.${name?cap_first};
import ${package}.repository.${name?cap_first}GenerateRepository;
import org.springframework.context.annotation.Description;

/**
 * ${label} Query仓储
 * 请在此类中添加需要的数据库查询方法
 */
@Description("自定义仓储: ${label}")
@org.springframework.stereotype.Repository
public class ${name?cap_first}QueryRepository extends ${name?cap_first}GenerateRepository implements Repository<${name?cap_first}, ${idType}> {


}