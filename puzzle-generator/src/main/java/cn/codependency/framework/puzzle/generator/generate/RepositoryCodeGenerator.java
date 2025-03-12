package cn.codependency.framework.puzzle.generator.generate;

import cn.codependency.framework.puzzle.generator.render.RootRepositoryRenderDataBuilder;
import cn.codependency.framework.puzzle.generator.template.TemplateEngine;
import cn.hutool.extra.template.Template;
import cn.codependency.framework.puzzle.generator.config.ModelDefinition;
import cn.codependency.framework.puzzle.generator.utils.FileUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Collection;

@Slf4j
@AllArgsConstructor
public class RepositoryCodeGenerator implements CodeGenerator<ModelDefinition> {

    private String basicPackage;

    @Override
    public void generate(String path, String basePackage, String generatorPackage, Collection<ModelDefinition> definitions) {

        String renderPath = path + "/src/main/java";
        log.info("[GenerateRepository生成] 路径: " + renderPath + "/" + generatorPackage.replace(".", "/") + "/repository");

        for (ModelDefinition definition : definitions) {
            renderTemplate(renderPath, generatorPackage, definition);
        }
    }

    private void renderTemplate(String path, String generatorPackage, ModelDefinition definition) {
        if (definition.getName().startsWith("_")) {
            return;
        }

        String sourceDir = path + "/" + generatorPackage.replace(".", "/") + "/repository";
        new File(sourceDir).mkdirs();

        Template template = TemplateEngine.ENGINE.getTemplate("tpl/RootRepository.ftl");
        String render = template.render(new RootRepositoryRenderDataBuilder(definition, basicPackage).renderData());
        String file = sourceDir + "/" + definition.getName() + "GenerateRepository.java";
        log.info("生成GenerateRepository: " + definition.getName() + "GenerateRepository, 生成路径: " + file);
        FileUtils.writeFile(render, file);
    }
}