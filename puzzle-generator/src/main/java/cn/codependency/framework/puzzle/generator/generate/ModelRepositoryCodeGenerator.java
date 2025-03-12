package cn.codependency.framework.puzzle.generator.generate;

import cn.codependency.framework.puzzle.generator.render.ModelRepositoryRenderDataBuilder;
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
public class ModelRepositoryCodeGenerator implements CodeGenerator<ModelDefinition> {

    private String basicPackage;

    @Override
    public void generate(String path, String basePackage, String generatorPackage, Collection<ModelDefinition> definitions) {

        String renderPath = path + "/src/main/java";
        log.info("[DomainRepository生成] 路径: " + renderPath + "/" + generatorPackage.replace(".", "/") + "/repository/sub");
        String sourceDir = renderPath + "/" + generatorPackage.replace(".", "/") + "/repository/sub";
        new File(sourceDir).mkdirs();
        for (ModelDefinition definition : definitions) {
            renderTemplate(renderPath, generatorPackage, definition);
        }
    }

    private void renderTemplate(String path, String generatorPackage, ModelDefinition definition) {
        if (definition.getName().startsWith("_")) {
            return;
        }

        String sourceDir = path + "/" + generatorPackage.replace(".", "/") + "/repository/sub";
        Template template = TemplateEngine.ENGINE.getTemplate("tpl/DomainRepository.ftl");
        String render = template.render(new ModelRepositoryRenderDataBuilder(definition, basicPackage).renderData());
        String file = sourceDir + "/" + definition.getName() + "DomainRepository.java";
        log.info("生成DomainRepository: " + definition.getName() + "DomainRepository, 生成路径: " + file);
        FileUtils.writeFile(render, file);
    }
}