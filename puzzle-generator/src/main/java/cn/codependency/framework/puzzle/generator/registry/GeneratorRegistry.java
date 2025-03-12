package cn.codependency.framework.puzzle.generator.registry;

import cn.codependency.framework.puzzle.common.Ops;
import cn.codependency.framework.puzzle.common.Streams;
import cn.codependency.framework.puzzle.generator.config.EnumDefinition;
import cn.codependency.framework.puzzle.generator.config.GeneratorFieldType;
import cn.codependency.framework.puzzle.generator.config.MappingDefinition;
import cn.codependency.framework.puzzle.generator.config.ModelDefinition;
import cn.codependency.framework.puzzle.generator.constants.BasicFieldType;
import cn.codependency.framework.puzzle.generator.constants.ColumnMapping;
import cn.codependency.framework.puzzle.generator.constants.Database;
import cn.codependency.framework.puzzle.generator.constants.ModelType;
import cn.codependency.framework.puzzle.generator.field.GeneratorField;
import cn.codependency.framework.puzzle.generator.field.SimpleGeneratorField;
import cn.codependency.framework.puzzle.generator.generate.*;
import cn.hutool.core.io.FileUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.util.*;

@Getter
@Builder
public class GeneratorRegistry {
    private String basePackage;
    private String generatePackage;
    private String sqlDir;
    private String tablePrefix;
    private String fieldPrefix;
    private Boolean multiTenant;
    private String tenantId;
    private Class<? extends Serializable> tenantIdType;

    @Builder.Default
    private Database database = Database.Mysql;

    @Builder.Default
    private Map<String, ColumnMapping> databaseMapping = new HashMap<>();

    @Builder.Default
    private Map<String, ModelDefinition> modelDefinitions = new HashMap<>();

    @Builder.Default
    private Map<String, EnumDefinition> enumDefinitions = new HashMap<>();

    @Builder.Default
    private Map<String, MappingDefinition> mappingDefinitions = new HashMap<>();

    @Setter
    @Builder.Default
    private List<GeneratorField> defaultFields = new ArrayList<>();

    public void register(ModelDefinition definition) {
        definition.setBasicPackage(basePackage + "." + generatePackage);
        this.modelDefinitions.put(definition.getName(), definition);
    }

    public void register(EnumDefinition definition) {
        definition.setBasicPackage(basePackage + "." + generatePackage);
        this.enumDefinitions.put(definition.getName(), definition);
    }

    public void register(MappingDefinition definition) {
        definition.setBasicPackage(basePackage + "." + generatePackage);
        this.mappingDefinitions.put(definition.getName(), definition);
    }

    public void generate() {
        URL resource = this.getClass().getResource("/");
        String projectDir = resource.getPath().replace("/target/classes/", "").replace("/target/test-classes/", "");
        generate(projectDir);
    }

    public void generate(String projectDir) {
        generateCode(projectDir);
    }

    public MappingDefinition getMappingDef(String name) {
        return this.mappingDefinitions.get(name);
    }

    public EnumDefinition getEnumDef(String name) {
        return this.enumDefinitions.get(name);
    }

    public ModelDefinition getModelDef(String name) {
        return this.modelDefinitions.get(name);
    }

    public void generateCode(String projectDir) {

        // 备份目录
        String generateDir = projectDir + "/src/main/java/" + getBasePackage().replace(".", "/") + "/" + getGeneratePackage();
        File file = new File(generateDir);
        if (file.exists()) {
            // 创建备份目录
            String backup = projectDir + "/" + ".backup";
            File backupDir = new File(backup);
            if (!backupDir.exists()) {
                backupDir.mkdir();
            }
            // 将源生成目录移动到备份目录
            FileUtil.move(file, new File(backup + "/" + getGeneratePackage() + "_" + System.currentTimeMillis()), true);
        }

        Collection<ModelDefinition> definitions = modelDefinitions.values();
        for (ModelDefinition definition : definitions) {
            definition.setDefaultFields(defaultFields);
            // 追加租户字段
            if (Ops.isTrue(getMultiTenant()) && Ops.isTrue(definition.getTenantIsolation()) ) {
                definition.getFields().add(new SimpleGeneratorField(Objects.isNull(tenantId) ? "tenantId": tenantId, BasicFieldType.Long, "租户id"));
            }
        }

        List<ModelDefinition> rootDefinitions =
                Streams.findList(definitions, def -> def.getModelType() == ModelType.ROOT);
        List<ModelDefinition> subDefinitions =
                Streams.findList(definitions, def -> def.getModelType() == ModelType.DOMAIN);

        new EnumCodeGenerator().generate(projectDir, getBasePackage(), getBasePackage() + "." + getGeneratePackage(),
                enumDefinitions.values());

        new ModelCodeGenerator(this).generate(projectDir, getBasePackage(), getBasePackage() + "." + getGeneratePackage(), definitions);
        new ModelActionGenerator(this).generate(projectDir, getBasePackage(), getBasePackage() + "." + getGeneratePackage(), definitions);
        new ModelRepositoryCodeGenerator(getBasePackage()).generate(projectDir, getBasePackage(),
                getBasePackage() + "." + getGeneratePackage(), subDefinitions);
        new EntityCodeGenerator(database).generate(projectDir, getBasePackage(), getBasePackage() + "." + getGeneratePackage(), definitions);

        new MapperCodeGenerator(database).generate(projectDir, getBasePackage(), getBasePackage() + "." + getGeneratePackage(), definitions);
        new UserMapperCodeGenerator().generate(projectDir, getBasePackage(), getBasePackage(), definitions);
        new RepositoryCodeGenerator(getBasePackage()).generate(projectDir,
                getBasePackage(), getBasePackage() + "." + getGeneratePackage(), rootDefinitions);
        new UserRepositoryCodeGenerator().generate(projectDir, getBasePackage(), getBasePackage(), rootDefinitions);

        new SqlGenerator(database, databaseMapping).generate(projectDir, getBasePackage(), sqlDir, definitions);

        new GraphqlSchemaGenerator(this).generate(projectDir, getBasePackage(), getBasePackage() + "." + getGeneratePackage(), definitions);
        new GraphqlSchemaMappingGenerator(this).generate(projectDir, getBasePackage(), getBasePackage() + "." + getGeneratePackage(), definitions);

        new DefaultCodeGenerator().generate(projectDir, getBasePackage(), getBasePackage() + "." + getGeneratePackage(), null);
    }


}
