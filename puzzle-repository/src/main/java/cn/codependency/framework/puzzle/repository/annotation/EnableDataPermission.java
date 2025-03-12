package cn.codependency.framework.puzzle.repository.annotation;

import cn.codependency.framework.puzzle.repository.configuration.DataPermissionConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(DataPermissionConfiguration.class)
public @interface EnableDataPermission {


}
