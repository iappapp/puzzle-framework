package cn.codependency.framework.puzzle.generator.config;

import cn.codependency.framework.puzzle.generator.constants.RefType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryDef {

    public QueryDef(String name, String refField, FieldType refFieldType, RefType refType) {
        this.name = name;
        this.refField = refField;
        this.refFieldType = refFieldType;
        this.refType = refType;
    }

    private String name;

    private String refField;

    private FieldType refFieldType;

    private FieldType refClass;

    private RefType refType;

}
