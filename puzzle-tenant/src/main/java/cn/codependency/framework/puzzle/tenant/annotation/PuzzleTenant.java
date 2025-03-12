package cn.codependency.framework.puzzle.tenant.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface PuzzleTenant {

    /**
     * tenantId字段名
     *
     * @return
     */
    String value();
}
