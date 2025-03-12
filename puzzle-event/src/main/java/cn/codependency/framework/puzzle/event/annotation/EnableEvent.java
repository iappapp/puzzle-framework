package cn.codependency.framework.puzzle.event.annotation;

import cn.codependency.framework.puzzle.event.configuration.EventAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(EventAutoConfiguration.class)
public @interface EnableEvent {

}
