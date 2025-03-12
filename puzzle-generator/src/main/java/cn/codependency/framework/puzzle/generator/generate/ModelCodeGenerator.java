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
public class ModelCodeGenerator implements CodeGenerator<ModelDefinition> {

    public ModelCodeGenerator(GeneratorRegistry registry) {
        this.registry = registry;
    }

    private GeneratorRegistry registry;

    @Override
    public void generate(String path, String basePackage, String generatorPackage, Collection<ModelDefinition> definitions) {
        String renderPath = path + "/src/main/java";
        log.info("[Model生成] 路径: " + renderPath + "/" + generatorPackage.replace(".", "/") + "/model");
        for (ModelDefinition definition : definitions) {
            renderTemplate(renderPath, generatorPackage, definition);
        }
    }

    private void renderTemplate(String path, String generatorPackage, ModelDefinition definition) {
        if (definition.getName().startsWith("_")) {
            return;
        }

        if (definition.getModelType() == ModelType.ROOT) {
            String sourceDir = path + "/" + generatorPackage.replace(".", "/") + "/model";
            new File(sourceDir).mkdirs();

            Template template = TemplateEngine.ENGINE.getTemplate("tpl/" + definition.getModelType().getTemplate());
            String render = template.render(new ModelRenderDataBuilder(registry, definition).renderData());
            String modelFile = sourceDir + "/" + definition.getName() + ".java";
            log.info("生成模型: " + definition.getName() + ", 生成路径: " + modelFile);
            FileUtils.writeFile(render, modelFile);


        } else {
            String subModelSourceDir = path + "/" + generatorPackage.replace(".", "/") + "/model/sub";
            new File(subModelSourceDir).mkdirs();

            Template template = TemplateEngine.ENGINE.getTemplate("tpl/" + definition.getModelType().getTemplate());
            String render = template.render(new ModelRenderDataBuilder(registry, definition).renderData());
            String modelFile = subModelSourceDir + "/" + definition.getName() + ".java";
            log.info("生成子模型: " + definition.getName() + ", 生成路径: " + modelFile);
            FileUtils.writeFile(render, modelFile);
        }
    }
}