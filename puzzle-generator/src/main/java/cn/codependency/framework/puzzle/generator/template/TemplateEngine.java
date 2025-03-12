package cn.codependency.framework.puzzle.generator.template;

import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateUtil;
import cn.hutool.extra.template.engine.freemarker.FreemarkerEngine;

public class TemplateEngine {

    public static FreemarkerEngine ENGINE = (FreemarkerEngine) TemplateUtil.createEngine(new TemplateConfig("/", TemplateConfig.ResourceMode.CLASSPATH).setCustomEngine(FreemarkerEngine.class));

}
