package ${package}.enums;

import cn.codependency.framework.puzzle.model.BaseEnum;
import org.springframework.context.annotation.Description;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.function.Supplier;


/**
 * 枚举 ${label}
 * 注意: 此类由代码生成器自动生成请不要手动修改，代码可能会被重新覆盖
 */
@Getter
@AllArgsConstructor
@Description("枚举: ${label}")
public enum ${name?cap_first}Enum implements BaseEnum<${keyClass}, ${valueClass}> {

<#list items as item>
    ${item.name?upper_case}(<#if keyClass == 'String'>"</#if>${item.key}<#if keyClass == 'String'>"</#if>, <#if valueClass == 'String'>"</#if>${item.value}<#if valueClass == 'String'>"</#if>),
</#list>
    ;

    private final ${keyClass} key;

    private final ${valueClass} value;


    /**
    * 通过key查找
    * @param key
    * @return
    */
    public static ${name?cap_first}Enum keyOf(${keyClass} key) {
        return keyOf(key, null);
    }

    /**
    * 通过key查找
    * @param key
    * @return
    */
    public static ${name?cap_first}Enum keyOf(${keyClass} key, Supplier<? extends RuntimeException> exceptionSupplier) {
        for (${name?cap_first}Enum value : values()) {
            if (value.getKey().equals(key)) {
                return value;
            }
        }

        if (exceptionSupplier != null) {
            throw exceptionSupplier.get();
        }
        return null;
    }
}
