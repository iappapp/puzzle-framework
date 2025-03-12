package cn.codependency.framework.puzzle.generator.generate;

import cn.codependency.framework.puzzle.generator.config.ModelDefinition;
import cn.codependency.framework.puzzle.generator.constants.ModelType;
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry;
import cn.codependency.framework.puzzle.generator.render.ModelRenderDataBuilder;
import cn.codependency.framework.puzzle.generator.template.TemplateEngine;
import cn.codependency.framework.puzzle.generator.utils.FileUtils;
import cn.hutool.extra.template.Template;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Collection;

@Slf4j
public class ModelActionGenerator implements CodeGenerator<ModelDefinition> {

    public ModelActionGenerator(GeneratorRegistry registry) {
        this.registry = registry;
    }

    private GeneratorRegistry registry;

    @Override
    public void generate(String path, String basePackage, String generatorPackage, Collection<ModelDefinition> definitions) {
        String renderPath = path + "/src/main/java";
        log.info("[ModelAction生成] 路径: " + renderPath + "/" + basePackage + "/action");
        for (ModelDefinition definition : definitions) {
            renderTemplate(renderPath, basePackage, definition);
        }
    }

    private void renderTemplate(String path, String generatorPackage, ModelDefinition definition) {
        if (definition.getName().startsWith("_")) {
            return;
        }

        if (definition.getModelType() == ModelType.ROOT) {
            String sourceDir = path + "/" + generatorPackage.replace(".", "/") + "/action";
            new File(sourceDir).mkdirs();

            Template template = TemplateEngine.ENGINE.getTemplate("tpl/Action.ftl");
            String render = template.render(new ModelRenderDataBuilder(registry, definition).renderData());
            String modelFile = sourceDir + "/" + definition.getName() + "$Act.java";
            log.info("生成模型Action: " + definition.getName() + ", 生成路径: " + modelFile);
            FileUtils.writeFile(render, modelFile, false);
        }
    }
}
