package cn.codependency.framework.puzzle.repository.id;

import com.google.common.collect.Maps;
import cn.codependency.framework.puzzle.common.id.IdGenerator;
import cn.codependency.framework.puzzle.common.id.IdGeneratorFactory;
import cn.codependency.framework.puzzle.common.id.SnowflakeIdGenerator;

import java.util.Map;
import java.util.Objects;

public class DefaultIdGeneratorFactory implements IdGeneratorFactory<Long> {

    private volatile Map<Class, IdGenerator> generatorMap = Maps.newConcurrentMap();

    @Override
    public IdGenerator getEntityIdGenerator(Class<?> entityClazz) {
        IdGenerator idGenerator = generatorMap.get(entityClazz);
        if (Objects.isNull(idGenerator)) {
            IdGenerator newIdGenerator = new SnowflakeIdGenerator();
            idGenerator = generatorMap.putIfAbsent(entityClazz, newIdGenerator);
            if (Objects.isNull(idGenerator)) {
                return newIdGenerator;
            }
        }
        return idGenerator;
    }
}