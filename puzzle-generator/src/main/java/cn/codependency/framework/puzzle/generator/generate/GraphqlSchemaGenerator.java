package cn.codependency.framework.puzzle.generator.generate;

import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry;
import cn.codependency.framework.puzzle.generator.render.ModelRenderDataBuilder;
import cn.codependency.framework.puzzle.generator.template.TemplateEngine;
import cn.hutool.extra.template.Template;
import cn.codependency.framework.puzzle.generator.config.ModelDefinition;
import cn.codependency.framework.puzzle.generator.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Collection;

@Slf4j
public class GraphqlSchemaGenerator implements CodeGenerator<ModelDefinition> {


    public GraphqlSchemaGenerator(GeneratorRegistry registry) {
        this.registry = registry;
    }

    private GeneratorRegistry registry;

    @Override
    public void generate(String path, String basePackage, String generatorPackage, Collection<ModelDefinition> definitions) {
        path = path + "/src/main/resources/graphql/schema";

        log.info("[GraphqlSchema生成] 路径: " + path);
        new File(path).mkdirs();
        for (ModelDefinition definition : definitions) {
            renderTemplate(path, generatorPackage, definition);
        }
    }

    private void renderTemplate(String path, String basePackage, ModelDefinition definition) {
        if (definition.getName().startsWith("_")) {
            return;
        }

        Template template = TemplateEngine.ENGINE.getTemplate("tpl/GraphqlSchema.ftl");
        String render = template.render(new ModelRenderDataBuilder(registry, definition).renderData());
        String schemaFile = path + "/" + definition.getName() + ".graphqls";
        log.info("生成模型GraphqlSchema: " + definition.getName() + ", 生成路径: " + schemaFile);
        FileUtils.writeFile(render, schemaFile);
    }
}
