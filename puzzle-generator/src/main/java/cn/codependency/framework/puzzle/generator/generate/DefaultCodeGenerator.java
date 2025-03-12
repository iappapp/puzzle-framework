package cn.codependency.framework.puzzle.generator.generate;

import cn.codependency.framework.puzzle.generator.config.GeneratorDefinition;
import cn.codependency.framework.puzzle.generator.template.TemplateEngine;
import cn.hutool.extra.template.Template;
import cn.codependency.framework.puzzle.generator.utils.FileUtils;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DefaultCodeGenerator implements CodeGenerator<GeneratorDefinition> {

    @Override
    public void generate(String path, String basePackage, String generatorPackage, Collection<GeneratorDefinition> nothings) {

        String listDirPath = path + "/src/main/java/" + generatorPackage.replace(".", "/") + "/list";
        renderListQueryTemplate(listDirPath, basePackage, generatorPackage);

        String utilDirPath = path + "/src/main/java/" + generatorPackage.replace(".", "/") + "/util";
        renderListQueryUtilsTemplate(utilDirPath, basePackage, generatorPackage);

        String listBasicXmlDirPath = path + "/src/main/resources/mapper/basic";
        renderListBasicXml(listBasicXmlDirPath);
    }

    private void renderListBasicXml(String listBasicXmlDirPath) {
        new File(listBasicXmlDirPath).mkdirs();
        Template template = TemplateEngine.ENGINE.getTemplate("tpl/common/ListBasic.xml.ftl");
        FileUtils.writeFile(template.render(new HashMap<>()), listBasicXmlDirPath + "/ListBasic.xml");
    }

    private void renderListQueryUtilsTemplate(String utilDirPath, String basePackage, String generatorPackage) {
        new File(utilDirPath).mkdirs();
        Template template = TemplateEngine.ENGINE.getTemplate("tpl/common/ListQueryUtils.ftl");
        Map<String, Object> renderData = new HashMap<String, Object>() {
            {
                put("package", basePackage);
                put("generatorPackage", generatorPackage);
            }
        };
        String render = template.render(renderData);
        FileUtils.writeFile(render, utilDirPath + "/ListQueryUtils.java");
    }

    /**
     * 生成ListQueryParam
     *
     * @param dir
     * @param basePackage
     */
    private static void renderListQueryTemplate(String dir, String basePackage, String generatorPackage) {
        new File(dir).mkdirs();
        Template template = TemplateEngine.ENGINE.getTemplate("tpl/common/ListQueryParam.ftl");
        Map<String, Object> renderData = new HashMap<String, Object>() {
            {
                put("package", basePackage);
                put("generatorPackage", generatorPackage);
            }
        };
        String render = template.render(renderData);
        FileUtils.writeFile(render, dir + "/ListQueryParam.java");
    }

}
