package cn.codependency.framework.puzzle.script;

import cn.codependency.framework.puzzle.common.Errors;
import cn.codependency.framework.puzzle.generator.config.ModelDefinition;
import cn.codependency.framework.puzzle.generator.constants.ModelType;
import cn.codependency.framework.puzzle.generator.registry.GeneratorRegistry;
import cn.codependency.framework.puzzle.model.RootModel;
import cn.codependency.framework.puzzle.repository.fast.R;
import cn.hutool.core.util.ReflectUtil;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public class ModelEngine {

    public ModelEngine(GeneratorRegistry registry) {
        this.registry = registry;
    }

    private final GeneratorRegistry registry;

    @SneakyThrows
    public boolean writeValues(String model, Serializable id, Map<String, Object> values) {
        ModelDefinition modelDef = registry.getModelDef(model);
        if (Objects.isNull(modelDef)) {
            throw Errors.system("异常的模型类型: " + model);
        }

        if (modelDef.getModelType() != ModelType.ROOT) {
            throw Errors.system(String.format("异常的模型类型: %s, 仅支持主模型", model));
        }
        Class<?> modelClass = Class.forName(modelDef.fullClass());
        Object modelData = R.getById(id, (Class<RootModel<Serializable>>) modelClass);
        if (Objects.isNull(modelData)) {
            return false;
        }

        // 设置值
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            String field = entry.getKey();
            String[] parts = field.split("\\.");
            int level = parts.length;
            Object writeModel = modelData;
            for (String part : parts) {
                if (Objects.isNull(writeModel)) {
                    break;
                }
                level--;
                Field f = ReflectUtil.getField(writeModel.getClass(), part);
                if (Objects.nonNull(f)) {
                    if (level == 0) {
                        if (entry.getValue() == null || f.getType() == entry.getValue().getClass()) {
                            ReflectUtil.setFieldValue(writeModel, f, entry.getValue());
                        } else {
                            if (f.getType() == String.class) {
                                ReflectUtil.setFieldValue(writeModel, f, Objects.toString(entry.getValue(), null));
                            } else if (f.getType() == Integer.class) {
                                ReflectUtil.setFieldValue(writeModel, f, Integer.parseInt(entry.getValue().toString()));
                            } else if (f.getType() == Long.class) {
                                ReflectUtil.setFieldValue(writeModel, f, Long.parseLong(entry.getValue().toString()));
                            } else if (f.getType() == Double.class) {
                                ReflectUtil.setFieldValue(writeModel, f, Double.parseDouble(entry.getValue().toString()));
                            } else if (f.getType() == BigDecimal.class) {
                                ReflectUtil.setFieldValue(writeModel, f, new BigDecimal(entry.getValue().toString()));
                            } else {
                                throw Errors.system("UnSupport value set type: " + f.getType());
                            }
                        }
                        break;
                    } else {
                        writeModel = ReflectUtil.getFieldValue(writeModel, f);
                    }
                } else {
                    Object $ref = ReflectUtil.invoke(writeModel, "$ref");
                    if (Objects.nonNull($ref)) {
                        f = ReflectUtil.getField($ref.getClass(), part);
                        writeModel = ReflectUtil.getFieldValue($ref, f);
                    } else {
                        break;
                    }
                }
            }
        }
        return true;
    }

    @SneakyThrows
    public Object readValue(String model, Serializable id, String field) {
        ModelDefinition modelDef = registry.getModelDef(model);
        if (Objects.isNull(modelDef)) {
            throw Errors.system("异常的模型类型: " + model);
        }

        if (modelDef.getModelType() != ModelType.ROOT) {
            throw Errors.system(String.format("异常的模型类型: %s, 仅支持主模型", model));
        }
        Class<?> modelClass = Class.forName(modelDef.fullClass());
        Object modelData = R.getById(id, (Class<RootModel<Serializable>>) modelClass);
        if (Objects.isNull(modelData)) {
            return null;
        }

        String[] parts = field.split("\\.");
        for (String part : parts) {
            if (Objects.isNull(modelData)) {
                return null;
            }

            Field f = ReflectUtil.getField(modelData.getClass(), part);
            if (Objects.nonNull(f)) {
                modelData = ReflectUtil.getFieldValue(modelData, f);
            } else {
                Object $ref = ReflectUtil.invoke(modelData, "$ref");
                if (Objects.nonNull($ref)) {
                    f = ReflectUtil.getField($ref.getClass(), part);
                    modelData = ReflectUtil.getFieldValue($ref, f);
                } else {
                    return null;
                }
            }
        }
        return modelData;
    }

}
