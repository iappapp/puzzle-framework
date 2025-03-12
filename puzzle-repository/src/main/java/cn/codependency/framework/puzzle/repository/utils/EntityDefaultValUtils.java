package cn.codependency.framework.puzzle.repository.utils;

import cn.codependency.framework.puzzle.tenant.TenantContext;
import cn.codependency.framework.puzzle.tenant.annotation.PuzzleTenant;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Objects;

public class EntityDefaultValUtils {

    /**
     * 删除字段
     */
    public static final String FIELD_IS_DELETED = "isDeleted";

    /**
     * 修改时间
     */
    public static final String FIELD_MODIFY_TIME = "modifyTime";

    /**
     * 创建时间
     */
    public static final String FIELD_CREATE_TIME = "createTime";

    /**
     * 实体更新时填充默认值
     *
     * @param entity
     */
    public static void fillDefaultValueForUpdate(Object entity) {
        fillIsDeleted(entity);
        fillModifyTime(entity);
    }

    /**
     * 实体创建时填充默认值
     *
     * @param entity
     */
    public static void fillDefaultValueForInsert(Object entity) {
        fillIsDeleted(entity);
        fillModifyTime(entity);
        fillCreateTime(entity);
        fillTenant(entity);
    }

    private static void fillTenant(Object entity) {
        PuzzleTenant tenant = AnnotationUtils.findAnnotation(entity.getClass(), PuzzleTenant.class);
        if (Objects.nonNull(tenant)) {
            Field field = ReflectionUtils.findField(entity.getClass(), tenant.value());
            if (Objects.nonNull(field) && Objects.equals(field.getType(), Long.class)) {
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, entity, TenantContext.getTenantId());
            }
        }
    }

    /**
     * 是否有删除字段
     *
     * @param entity
     * @return
     */
    public static boolean hasIsDeletedField(Object entity) {
        Field field = ReflectionUtils.findField(entity.getClass(), FIELD_IS_DELETED);
        return Objects.nonNull(field) && Objects.equals(field.getType(), Integer.class);
    }

    private static void fillIsDeleted(Object entity) {
        Field field = ReflectionUtils.findField(entity.getClass(), FIELD_IS_DELETED);
        if (Objects.nonNull(field) && Objects.equals(field.getType(), Integer.class)) {
            ReflectionUtils.makeAccessible(field);
            ReflectionUtils.setField(field, entity, 0);
        }
    }

    private static void fillModifyTime(Object entity) {
        Field field = ReflectionUtils.findField(entity.getClass(), FIELD_MODIFY_TIME);
        if (Objects.nonNull(field) && Objects.equals(field.getType(), Date.class)) {
            ReflectionUtils.makeAccessible(field);
            ReflectionUtils.setField(field, entity, new Date());
        }
    }

    private static void fillCreateTime(Object entity) {
        Field field = ReflectionUtils.findField(entity.getClass(), FIELD_CREATE_TIME);
        if (Objects.nonNull(field) && Objects.equals(field.getType(), Date.class)) {
            ReflectionUtils.makeAccessible(field);
            ReflectionUtils.setField(field, entity, new Date());
        }
    }
}
