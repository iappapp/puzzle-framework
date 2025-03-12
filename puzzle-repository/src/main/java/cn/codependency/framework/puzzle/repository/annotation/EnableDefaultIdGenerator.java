package cn.codependency.framework.puzzle.repository.annotation;

import cn.codependency.framework.puzzle.repository.id.IdGeneratorConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(IdGeneratorConfiguration.class)
public @interface EnableDefaultIdGenerator {
}
