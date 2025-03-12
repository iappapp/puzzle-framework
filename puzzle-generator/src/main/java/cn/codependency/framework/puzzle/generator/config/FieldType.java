package cn.codependency.framework.puzzle.generator.config;

import java.util.List;

public interface FieldType {

    List<String> getFullTypePath();


    String getType();

    default String getGraphqlType() {
        String type = getType();
        if (type.equals("Integer")) {
            return "Int";
        }
        return type;
    }


    default String getSimpleType() {
        return getType();
    }
}
