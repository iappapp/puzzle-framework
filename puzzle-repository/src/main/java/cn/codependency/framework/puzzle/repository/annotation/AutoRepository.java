package cn.codependency.framework.puzzle.repository.annotation;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface AutoRepository {

    boolean autoCommit() default true;

    boolean readOnly() default false;
}
