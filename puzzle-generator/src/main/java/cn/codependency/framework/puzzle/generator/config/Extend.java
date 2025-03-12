package cn.codependency.framework.puzzle.generator.config;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Extend {

    private Integer maxLength;

    private Integer minLength;

    private Integer precision;

    private String columnType;

    private Boolean internal;

    private Boolean mask;
}
