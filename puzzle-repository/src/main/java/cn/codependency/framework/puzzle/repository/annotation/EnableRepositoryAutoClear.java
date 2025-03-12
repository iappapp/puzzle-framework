package cn.codependency.framework.puzzle.repository.annotation;

import cn.codependency.framework.puzzle.repository.configuration.RepositoryAutoClearConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RepositoryAutoClearConfiguration.class)
public @interface EnableRepositoryAutoClear {

}
