package cn.codependency.framework.puzzle.generator.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@AllArgsConstructor
public enum Database {

    Mysql(new HashMap<String, ColumnMapping>() {{
        put(Integer.class.getSimpleName(), new ColumnMapping("int"));
        put(Long.class.getSimpleName(), new ColumnMapping("bigint"));
        put(String.class.getSimpleName(), new ColumnMapping("varchar", 32));
        put(Date.class.getSimpleName(), new ColumnMapping("datetime"));
        put(Double.class.getSimpleName(), new ColumnMapping("decimal", 18, 2));
        put(BigDecimal.class.getSimpleName(), new ColumnMapping("decimal", 18, 2));
        put(Float.class.getSimpleName(), new ColumnMapping("decimal", 18, 2));
        put(Boolean.class.getSimpleName(), new ColumnMapping("tinyint"));
        put(List.class.getSimpleName(), new ColumnMapping("varchar", 512));
        put(Map.class.getSimpleName(), new ColumnMapping("text"));
    }}),

    Postgres(new HashMap<String, ColumnMapping>() {{
        put(Integer.class.getSimpleName(), new ColumnMapping("integer"));
        put(Long.class.getSimpleName(), new ColumnMapping("bigint"));
        put(String.class.getSimpleName(), new ColumnMapping("varchar", 32));
        put(Date.class.getSimpleName(), new ColumnMapping("timestamp"));
        put(Double.class.getSimpleName(), new ColumnMapping("decimal", 18, 2));
        put(BigDecimal.class.getSimpleName(), new ColumnMapping("decimal", 18, 2));
        put(Float.class.getSimpleName(), new ColumnMapping("decimal", 18, 2));
        put(Boolean.class.getSimpleName(), new ColumnMapping("boolean"));
        put(List.class.getSimpleName(), new ColumnMapping("varchar", 512));
        put(Map.class.getSimpleName(), new ColumnMapping("text"));
    }})

    ;

    private Map<String, ColumnMapping> columnMapping;
}
