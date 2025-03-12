package cn.codependency.framework.puzzle.generator.generate;

import cn.codependency.framework.puzzle.common.Streams;
import cn.codependency.framework.puzzle.generator.constants.ModelType;
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry;
import cn.codependency.framework.puzzle.generator.render.ModelRenderDataBuilder;
import cn.codependency.framework.puzzle.generator.template.TemplateEngine;
import cn.hutool.extra.template.Template;
import cn.codependency.framework.puzzle.generator.config.ModelDefinition;
import cn.codependency.framework.puzzle.generator.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class GraphqlSchemaMappingGenerator implements CodeGenerator<ModelDefinition> {

    public GraphqlSchemaMappingGenerator(GeneratorRegistry registry) {
        this.registry = registry;
    }

    private GeneratorRegistry registry;

    @Override
    public void generate(String path, String basePackage, String generatorPackage, Collection<ModelDefinition> definitions) {
        String renderPath = path + "/src/main/java";
        log.info("[GraphqlSchemaMapping生成] 路径: " + renderPath + "/" + generatorPackage.replace(".", "/") + "/graphql");
        Optional<ModelDefinition> firstOptional = Streams.findFirstOptional(definitions, d -> d.getModelType() == ModelType.DOMAIN);

        for (ModelDefinition definition : definitions) {
            renderTemplate(renderPath, generatorPackage, definition, firstOptional.isPresent());
        }
    }

    private void renderTemplate(String renderPath, String generatorPackage, ModelDefinition definition, boolean hasSub) {
        if (definition.getName().startsWith("_")) {
            return;
        }

        Map<String, Object> renderData = new ModelRenderDataBuilder(registry, definition).renderData();
        if (CollectionUtils.isEmpty((Collection<?>) renderData.get("refs"))
                && CollectionUtils.isEmpty((Collection<?>) renderData.get("reverseRefs"))) {
            return;
        }
        renderData.put("hasSub", hasSub);

        String sourceDir = renderPath + "/" + generatorPackage.replace(".", "/") + "/graphql";
        new File(sourceDir).mkdirs();

        Template template = TemplateEngine.ENGINE.getTemplate("tpl/GraphqlSchemaMapping.ftl");
        String render = template.render(renderData);

        String modelFile = sourceDir + "/" + definition.getName() + "SchemaMapping.java";
        log.info("生成GraphqlSchemaMapping: " + definition.getName() + ", 生成路径: " + modelFile);
        FileUtils.writeFile(render, modelFile);
    }
}