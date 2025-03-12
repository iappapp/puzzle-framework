package cn.codependency.framework.puzzle.generator.config;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

@Getter
public class GeneratorFieldType implements FieldType {

    public GeneratorFieldType(String fullType) {
        String[] split = fullType.split("\\.");
        this.type = split[split.length - 1];
        StringBuilder path = new StringBuilder();
        for (int i = 0; i < split.length - 1; i++) {
            if (path.length() > 0) {
                path.append(".");
            }
            path.append(split[i]);
        }
        this.packagePath = path.toString();
    }

    private String type;

    private String packagePath;

    @Override
    public List<String> getFullTypePath() {
        return Lists.newArrayList(packagePath + "." + type);
    }
}
