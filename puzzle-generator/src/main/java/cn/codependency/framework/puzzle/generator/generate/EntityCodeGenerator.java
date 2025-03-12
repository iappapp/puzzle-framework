package cn.codependency.framework.puzzle.generator.generate;

import cn.codependency.framework.puzzle.generator.constants.Database;
import cn.codependency.framework.puzzle.generator.render.EntityRenderDataBuilder;
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
public class EntityCodeGenerator implements CodeGenerator<ModelDefinition> {

    private Database database;

    @Override
    public void generate(String path, String basePackage, String generatorPackage, Collection<ModelDefinition> definitions) {

        String renderPath = path + "/src/main/java";
        log.info("[Entity生成] 路径: " + renderPath + "/" + generatorPackage.replace(".", "/") + "/entity");
        for (ModelDefinition definition : definitions) {
            renderTemplate(renderPath, generatorPackage, definition, database);
        }
    }

    private static void renderTemplate(String path, String basePackage, ModelDefinition definition, Database database) {
        String sourceDir = path + "/" + basePackage.replace(".", "/") + "/entity";
        new File(sourceDir).mkdirs();

        Template template = TemplateEngine.ENGINE.getTemplate("tpl/Entity.ftl");
        String render = template.render(new EntityRenderDataBuilder(database, definition).renderData());
        String file = sourceDir + "/" + definition.getName() + "Entity.java";
        log.info("生成Entity: " + definition.getName() + "Entity, 生成路径: " + file);
        FileUtils.writeFile(render, file);
    }
}
