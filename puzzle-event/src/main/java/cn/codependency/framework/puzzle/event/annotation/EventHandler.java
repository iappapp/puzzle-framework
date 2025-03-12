package cn.codependency.framework.puzzle.event.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Component
public @interface EventHandler {

    /**
     * eventBus
     *
     * @return
     */
    String eventBus() default "";
}
