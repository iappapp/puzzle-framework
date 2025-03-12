package cn.codependency.framework.puzzle.repository.annotation;

import cn.codependency.framework.puzzle.repository.configuration.RepositoryConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RepositoryConfiguration.class)
@EnableRepositoryAutoClear
public @interface EnableRepository {


}
