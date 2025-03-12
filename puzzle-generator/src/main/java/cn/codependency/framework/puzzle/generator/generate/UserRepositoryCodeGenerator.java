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
public class UserRepositoryCodeGenerator implements CodeGenerator<ModelDefinition> {

    @Override
    public void generate(String path, String basePackage, String generatorPackage, Collection<ModelDefinition> definitions) {

        String renderPath = path + "/src/main/java";
        log.info("[QueryRepository生成] 路径: " + renderPath + "/" + generatorPackage.replace(".", "/") + "/repository");

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

        Template template = TemplateEngine.ENGINE.getTemplate("tpl/UserRootRepository.ftl");
        String render = template.render(new RootRepositoryRenderDataBuilder(definition, generatorPackage).renderData());

        String file = sourceDir + "/" + definition.getName() + "QueryRepository.java";
        log.info("生成QueryRepository: " + definition.getName() + "QueryRepository, 生成路径: " + file);
        FileUtils.writeFile(render, file, false);
    }
}
