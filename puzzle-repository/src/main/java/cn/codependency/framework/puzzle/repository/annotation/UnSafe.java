package cn.codependency.framework.puzzle.repository.annotation;

import java.lang.annotation.*;

/**
 * 标记为不安全写法
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface UnSafe {


}
