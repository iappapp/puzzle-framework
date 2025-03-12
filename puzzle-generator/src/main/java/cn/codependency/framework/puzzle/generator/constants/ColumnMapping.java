package cn.codependency.framework.puzzle.generator.constants;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ColumnMapping implements Serializable {

    public ColumnMapping(String columnType) {
        this.columnType = columnType;
        this.supportParameters = false;
    }

    public ColumnMapping(String columnType, Integer length) {
        this.columnType = columnType;
        this.length = length;
        this.supportParameters = true;
    }

    public ColumnMapping(String columnType, Integer length, Integer precision) {
        this.columnType = columnType;
        this.length = length;
        this.precision = precision;
        this.supportParameters = true;
    }

    private String columnType;

    private boolean supportParameters;

    private Integer length;

    private Integer precision;
}