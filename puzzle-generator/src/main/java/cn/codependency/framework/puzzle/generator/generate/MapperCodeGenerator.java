package cn.codependency.framework.puzzle.generator.generate;

import cn.codependency.framework.puzzle.generator.constants.Database;
import cn.codependency.framework.puzzle.generator.render.MapperRenderDataBuilder;
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
public class MapperCodeGenerator implements CodeGenerator<ModelDefinition> {


    private Database database;

    @Override
    public void generate(String path, String basePackage, String generatorPackage, Collection<ModelDefinition> definitions) {
        String renderPath = path + "/src/main/java";
        log.info("[GenerateMapper生成] 路径: " + renderPath + "/" + generatorPackage.replace(".", "/") + "/mapper");

        for (ModelDefinition definition : definitions) {
            renderTemplate(renderPath, generatorPackage, definition, database);
        }
    }

    private static void renderTemplate(String path, String generatorPackage, ModelDefinition definition, Database database) {
        String sourceDir = path + "/" + generatorPackage.replace(".", "/") + "/mapper";
        new File(sourceDir).mkdirs();

        Template template = TemplateEngine.ENGINE.getTemplate("tpl/Mapper.ftl");
        String render = template.render(new MapperRenderDataBuilder(database, definition).renderData());

        String file = sourceDir + "/" + definition.getName() + "GenerateMapper.java";
        log.info("生成GenerateMapper: " + definition.getName() + "GenerateMapper, 生成路径: " + file);
        FileUtils.writeFile(render, file);

    }
}
