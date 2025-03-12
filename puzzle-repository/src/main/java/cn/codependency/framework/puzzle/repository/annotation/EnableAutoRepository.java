package cn.codependency.framework.puzzle.repository.annotation;

import cn.codependency.framework.puzzle.repository.configuration.RepositoryAutoCommitConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableRepository
@Import(RepositoryAutoCommitConfiguration.class)
public @interface EnableAutoRepository {

}
