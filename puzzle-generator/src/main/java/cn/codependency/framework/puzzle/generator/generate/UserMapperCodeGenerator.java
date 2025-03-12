package cn.codependency.framework.puzzle.generator.generate;

import cn.codependency.framework.puzzle.generator.render.UserMapperRenderDataBuilder;
import cn.codependency.framework.puzzle.generator.template.TemplateEngine;
import cn.hutool.extra.template.Template;
import cn.codependency.framework.puzzle.generator.config.ModelDefinition;
import cn.codependency.framework.puzzle.generator.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Collection;

@Slf4j
public class UserMapperCodeGenerator implements CodeGenerator<ModelDefinition> {

    @Override
    public void generate(String path, String basePackage, String generatorPackage, Collection<ModelDefinition> definitions) {

        String renderPath = path + "/src/main/java";
        log.info("[Mapper生成] 路径: " + renderPath + "/" + generatorPackage.replace(".", "/") + "/mapper");

        String xmlRenderPath = path + "/src/main/resources/mapper";
        log.info("[Mapper.xml生成] 路径: " + xmlRenderPath);

        for (ModelDefinition definition : definitions) {
            renderTemplate(renderPath, generatorPackage, definition);
            renderXmlTemplate(xmlRenderPath, generatorPackage, definition);
        }
    }

    private void renderXmlTemplate(String xmlRenderPath, String generatorPackage, ModelDefinition definition) {
        String sourceDir = xmlRenderPath + "/generate";
        new File(sourceDir).mkdirs();

        Template template = TemplateEngine.ENGINE.getTemplate("tpl/Mapper.xml.ftl");
        String render = template.render(new UserMapperRenderDataBuilder(definition, generatorPackage).renderData());

        final String file = sourceDir + "/" + definition.getName() + "GenerateMapper.xml";
        log.info("生成Mapper: " + definition.getName() + "Mapper, 生成路径: " + file);
        FileUtils.writeFile(render, file, false);
    }

    private static void renderTemplate(String path, String basePackage, ModelDefinition definition) {

        String sourceDir = path + "/" + basePackage.replace(".", "/") + "/mapper";
        new File(sourceDir).mkdirs();

        Template template = TemplateEngine.ENGINE.getTemplate("tpl/UserMapper.ftl");
        String render = template.render(new UserMapperRenderDataBuilder(definition, basePackage).renderData());

        final String file = sourceDir + "/" + definition.getName() + "Mapper.java";
        log.info("生成Mapper: " + definition.getName() + "Mapper, 生成路径: " + file);
        FileUtils.writeFile(render, file, false);

    }

}
