package cn.codependency.framework.puzzle.generator.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ModelType {

    ROOT("RootDomain.ftl"),

    DOMAIN("Domain.ftl"),

    ;

    private String template;

}