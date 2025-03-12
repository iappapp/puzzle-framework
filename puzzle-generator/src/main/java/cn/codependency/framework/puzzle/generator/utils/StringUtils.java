package cn.codependency.framework.puzzle.generator.utils;

import cn.hutool.core.util.StrUtil;

public class StringUtils {

    public static String firstCap(String value) {
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }


    public static String toCamelCase(CharSequence name, char symbol) {
        if (null == name) {
            return null;
        }

        final String name2 = name.toString();
        if (StrUtil.contains(name2, symbol)) {
            final int length = name2.length();
            final StringBuilder sb = new StringBuilder(length);
            boolean upperCase = false;
            for (int i = 0; i < length; i++) {
                char c = name2.charAt(i);

                if (c == symbol) {
                    upperCase = true;
                } else if (upperCase) {
                    sb.append(Character.toUpperCase(c));
                    upperCase = false;
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        } else {
            return name2;
        }
    }
}
