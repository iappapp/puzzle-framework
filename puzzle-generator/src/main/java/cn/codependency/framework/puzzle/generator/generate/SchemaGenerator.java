package cn.codependency.framework.puzzle.generator.generate;


import cn.codependency.framework.puzzle.generator.constants.ModelType;
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry;
import cn.codependency.framework.puzzle.common.json.JsonUtils;
import cn.codependency.framework.puzzle.generator.config.EnumDefinition;
import cn.codependency.framework.puzzle.generator.config.GeneratorRef;
import cn.codependency.framework.puzzle.generator.config.ModelDefinition;
import cn.codependency.framework.puzzle.generator.field.GeneratorField;
import cn.codependency.framework.puzzle.generator.field.SubModelGeneratorField;

import java.util.*;

public class SchemaGenerator {

    public static String generatorJson(GeneratorRegistry registry) {
        Map<String, Object> schema = generator(registry);
        return JsonUtils.toJson(schema);
    }

    public static Map<String, Object> generator(GeneratorRegistry registry) {
        Collection<ModelDefinition> definitions = registry.getModelDefinitions().values();
        Map<String, Object> schema = new HashMap<>();
        Map<String, Object> models = new HashMap<>();
        schema.put("models", models);
        generatorModelSchema(registry, definitions, models);
        Map<String, Object> enums = new HashMap<>();
        schema.put("enums", enums);
        generatorEnumSchema(registry.getEnumDefinitions().values(), enums);
        return schema;
    }

    private static void generatorEnumSchema(Collection<EnumDefinition> definitions, Map<String, Object> enums) {
        for (EnumDefinition definition : definitions) {
            Map<String, Object> enumDefinition = new HashMap<>();
            enumDefinition.put("label", definition.getLabel());
            enumDefinition.put("items", definition.getItems());
            enums.put(definition.getName() + "Enum", enumDefinition);
        }
    }

    private static void generatorModelSchema(GeneratorRegistry registry, Collection<ModelDefinition> definitions, Map<String, Object> models) {
        for (ModelDefinition definition : definitions) {
            if (definition.getModelType() == ModelType.ROOT) {
                Map<String, Object> modelDefinition = new HashMap<>();
                List<Map<String, Object>> fields = new ArrayList<>();
                List<Map<String, Object>> refs = new ArrayList<>();
                models.put(definition.getName(), modelDefinition);
                modelDefinition.put("description", definition.getLabel());
                modelDefinition.put("fields", fields);
                modelDefinition.put("refs", refs);
                modelDefinition.put("tableName", definition.tableName());

                Map<String, Object> idField = new HashMap<>();
                idField.put("fieldName", "id");
                idField.put("fieldLabel", "id");
                idField.put("fieldType", definition.getIdType().getType());
                idField.put("tableField", "id");
                fields.add(idField);

                for (GeneratorField field : definition.getFields()) {
                    Map<String, Object> schema = field.schema(registry);
                    if (!(field instanceof SubModelGeneratorField)) {
                        schema.put("tableField", definition.tableField(field));
                    }
                    fields.add(schema);
                }
                for (GeneratorField defaultField : definition.getRegistry().getDefaultFields()) {
                    Map<String, Object> schema = defaultField.schema(registry);
                    schema.put("tableField", definition.tableField(defaultField));
                    fields.add(schema);
                }
                for (GeneratorRef ref : definition.getRefs()) {
                    refs.add(ref.schema(registry));
                }
                for (GeneratorRef reverseRef : definition.getReverseRefs()) {
                    refs.add(reverseRef.schema(registry));
                }
            }
        }
    }

    public static Map<String, Object> generatorSubModel(GeneratorRegistry registry, ModelDefinition definition) {
        if (definition.getModelType() == ModelType.DOMAIN) {
            Map<String, Object> modelDefinition = new HashMap<>();
            modelDefinition.put("description", definition.getLabel());
            List<Map<String, Object>> fields = new ArrayList<>();
            modelDefinition.put("fields", fields);
            modelDefinition.put("tableName", definition.tableName());
            List<Map<String, Object>> refs = new ArrayList<>();
            modelDefinition.put("refs", refs);

            Map<String, Object> idField = new HashMap<>();
            idField.put("fieldName", "id");
            idField.put("fieldLabel", "id");
            idField.put("fieldType", definition.getIdType().getType());
            idField.put("tableField", "id");
            fields.add(idField);
            for (GeneratorField field : definition.getFields()) {
                Map<String, Object> schema = field.schema(registry);
                if (!(field instanceof SubModelGeneratorField)) {
                    schema.put("tableField", definition.tableField(field));
                }
                fields.add(schema);
            }

            for (GeneratorField defaultField : definition.getRegistry().getDefaultFields()) {
                Map<String, Object> schema = defaultField.schema(registry);
                schema.put("tableField", definition.tableField(defaultField));
                fields.add(schema);
            }

            for (GeneratorRef ref : definition.getRefs()) {
                refs.add(ref.schema(registry));
            }
            for (GeneratorRef reverseRef : definition.getReverseRefs()) {
                refs.add(reverseRef.schema(registry));
            }

            return modelDefinition;
        }
        return null;
    }

}
