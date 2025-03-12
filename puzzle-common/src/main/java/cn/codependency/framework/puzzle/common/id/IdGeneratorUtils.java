package cn.codependency.framework.puzzle.common.id;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class IdGeneratorUtils {

    private IdGeneratorFactory idGeneratorFactory;

    public IdGeneratorUtils(IdGeneratorFactory idGeneratorFactory) {
        this.idGeneratorFactory = idGeneratorFactory;
    }

    /**
     * 生成id
     *
     * @param entity
     * @return
     */
    public <IdType, Entity> IdType generatorId(Entity entity, Function<Entity, IdType> getter,
                                               BiConsumer<Entity, IdType> setter) {
        return generatorId(entity, getter, setter, entity.getClass());
    }

    /**
     * 生成id
     *
     * @param entity
     * @return
     */
    public <IdType, Entity> IdType generatorId(Entity entity, Function<Entity, IdType> getter,
                                               BiConsumer<Entity, IdType> setter, Class<?> entityClass) {
        IdType id = getter.apply(entity);
        if (Objects.isNull(id)) {
            IdType generatorId = this.generatorId(entityClass);
            if (Objects.nonNull(generatorId)) {
                setter.accept(entity, generatorId);
                id = generatorId;
            }
        }
        return id;
    }

    /**
     * 生成id
     *
     * @param entityClass
     * @return
     */
    public <IdType> IdType generatorId(Class entityClass) {
        IdGenerator<IdType> idGenerator = idGeneratorFactory.getEntityIdGenerator(entityClass);
        if (Objects.nonNull(idGenerator)) {
            IdType generatorId = idGenerator.generatorId();
            return generatorId;
        }
        return null;
    }

}

