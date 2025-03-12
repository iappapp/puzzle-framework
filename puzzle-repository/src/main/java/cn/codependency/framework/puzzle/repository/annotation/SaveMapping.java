package cn.codependency.framework.puzzle.repository.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface SaveMapping {

    Class<?>[] value();

}