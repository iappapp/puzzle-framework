package ${mapper_package}.mapper;

import ${package}.mapper.${name?cap_first}GenerateMapper;
import org.springframework.context.annotation.Description;

/**
 * ${label} Mapper
 * 请在此类中添加需要的数据库查询方法
 */
@Description("用户自定义Mapper: ${label} Mapper")
public interface ${name?cap_first}Mapper extends ${name?cap_first}GenerateMapper {


}