package cn.codependency.framework.puzzle.generator.generate;

import cn.codependency.framework.puzzle.generator.render.EnumRenderDataBuilder;
import cn.codependency.framework.puzzle.generator.template.TemplateEngine;
import cn.hutool.extra.template.Template;
import cn.codependency.framework.puzzle.generator.config.EnumDefinition;
import cn.codependency.framework.puzzle.generator.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Collection;

@Slf4j
public class EnumCodeGenerator implements CodeGenerator<EnumDefinition> {

    @Override
    public void generate(String path, String basePackage, String generatorPackage, Collection<EnumDefinition> definitions) {

        String renderPath = path + "/src/main/java";
        log.info("[Enum生成] 路径: " + renderPath + "/" + generatorPackage.replace(".", "/") + "/enums");

        for (EnumDefinition definition : definitions) {
            renderTemplate(renderPath, generatorPackage, definition);
        }
    }

    private static void renderTemplate(String path, String generatorPackage, EnumDefinition<?, ?> definition) {
        String sourceDir = path + "/" + generatorPackage.replace(".", "/") + "/enums";
        new File(sourceDir).mkdirs();

        Template template = TemplateEngine.ENGINE.getTemplate("tpl/Enum.ftl");
        String render = template.render(new EnumRenderDataBuilder(definition).renderData());

        final String file = sourceDir + "/" + definition.getName() + "Enum.java";
        log.info("生成Enum: " + definition.getName() + "Enum, 生成路径: " + file);
        FileUtils.writeFile(render, file);
    }
}

