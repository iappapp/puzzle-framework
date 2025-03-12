package cn.codependency.framework.puzzle.repository;

import cn.codependency.framework.puzzle.repository.sql.DeleteBatchSqlOperation;
import cn.codependency.framework.puzzle.repository.sql.InsertBatchSqlOperation;
import cn.codependency.framework.puzzle.repository.sql.SqlOperation;
import cn.codependency.framework.puzzle.repository.sql.merge.BasicMergeSqlFactory;
import cn.codependency.framework.puzzle.repository.sql.merge.MergeSqlFactory;
import cn.codependency.framework.puzzle.repository.sql.merge.MergeSqlStrategy;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.google.common.collect.Lists;
import cn.codependency.framework.puzzle.common.DeepEquals;
import cn.codependency.framework.puzzle.common.Streams;
import cn.codependency.framework.puzzle.common.diff.CollectionDiff;
import cn.codependency.framework.puzzle.common.id.IdGeneratorUtils;
import cn.codependency.framework.puzzle.common.json.JsonUtils;
import cn.codependency.framework.puzzle.model.Model;
import cn.codependency.framework.puzzle.repository.aggregate.Aggregate;
import cn.codependency.framework.puzzle.repository.aggregate.CollectionAggregate;
import cn.codependency.framework.puzzle.repository.exception.ThrowableProcessor;
import cn.codependency.framework.puzzle.repository.sql.UpdateSomeColumnSqlOperation;
import cn.codependency.framework.puzzle.repository.utils.EntityDefaultValUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class RepositoryUtils {

    public RepositoryUtils(IdGeneratorUtils idGeneratorUtils, DbType dbType) {
        this.dbType = dbType;
        this.idGeneratorUtils = idGeneratorUtils;
        this.mergeSqlFactory = new BasicMergeSqlFactory(dbType);
    }

    public RepositoryUtils(IdGeneratorUtils idGeneratorUtils, DbType dbType, Integer defaultBatchSize) {
        this.dbType = dbType;
        this.idGeneratorUtils = idGeneratorUtils;
        this.defaultBatchSize = defaultBatchSize;
        this.mergeSqlFactory = new BasicMergeSqlFactory(dbType);
    }

    @Getter
    private DbType dbType;


    private IdGeneratorUtils idGeneratorUtils;

    @Setter
    private Integer defaultBatchSize = 1000;


    @Setter
    private MergeSqlFactory mergeSqlFactory;

    /**
     * 单体转列表转换器
     *
     * @param func
     * @param <Domain>
     * @param <Entity>
     * @return
     */
    private <Domain, Entity> Function<Collection<Domain>, Collection<Entity>>
    collectionFunc(Function<Domain, Entity> func) {
        return col -> Streams.toList(col, func);
    }

    /**
     * 对比两个列表的差异，返回需要新增，更新，删除的数据
     *
     * @param entities
     */
    public <Model, IdType> CollectionDiff.CollectionDiffResult<Model> collectionDiff(Collection<Model> entities,
                                                                                     Collection<Model> snapshotEntities, Function<Model, IdType> idGetter) {
        return CollectionDiff.collectionDiff(entities, snapshotEntities, idGetter);
    }

    /**
     * 更新或者创建
     *
     * @param idGetter
     * @param idSetter
     * @param mapper
     * @param <Entity>
     * @param <IdType>
     */
    @Transactional(rollbackFor = Exception.class)
    public <Domain extends Model, Entity, IdType extends Serializable> IdType updateOrInsert(
            Aggregate<Domain> aggregate, Function<Domain, Entity> transferFunc, BiConsumer<Domain, IdType> domainIdSetter,
            Function<Entity, IdType> idGetter, BiConsumer<Entity, IdType> idSetter, BaseMapper<Entity> mapper) {

        List<SqlOperation> sqlOperations =
                updateOrInsertOperation(aggregate, transferFunc, domainIdSetter, idGetter, idSetter, mapper);

        // 执行或者记录批量SQL操作
        this.invokeOrRecordBatchSqlOperations(sqlOperations);

        if (Objects.isNull(aggregate.getAggregate())) {
            return null;
        }
        Entity entity = transferFunc.apply(aggregate.getAggregate());
        return Objects.isNull(entity) ? null : idGetter.apply(entity);
    }

    /**
     * 批量更新或者创建
     *
     * @param aggregate
     * @param idGetter
     * @param idSetter
     * @param mapper
     * @param <Entity>
     * @param <IdType>
     */
    @Transactional(rollbackFor = Exception.class)
    public <Domain extends Model, Entity, IdType extends Serializable> void batchUpdateOrInsert(
            CollectionAggregate<Domain> aggregate, Function<Domain, Entity> transferFunc,
            BiConsumer<Domain, IdType> domainIdSetter, Function<Entity, IdType> idGetter,
            BiConsumer<Entity, IdType> idSetter, BaseMapper<Entity> mapper) {
        List<SqlOperation> sqlOperations =
                batchUpdateOrInsertOperations(aggregate, transferFunc, domainIdSetter, idGetter, idSetter, mapper);

        // 执行或者记录批量SQL操作
        this.invokeOrRecordBatchSqlOperations(sqlOperations);
    }

    /**
     * 生成实体id
     *
     * @param entity
     * @param idGetter
     * @param idSetter
     * @param <Entity>
     * @param <IdType>
     */
    public <Entity, IdType extends Serializable> IdType generatorId(Entity entity, Function<Entity, IdType> idGetter,
                                                                    BiConsumer<Entity, IdType> idSetter) {
        return idGeneratorUtils.generatorId(entity, idGetter, idSetter);
    }

    /**
     * 生成实体id
     *
     * @param entity
     * @param idGetter
     * @param idSetter
     * @param <Entity>
     * @param <IdType>
     */
    public <Entity, IdType extends Serializable> IdType generatorId(Entity entity, Function<Entity, IdType> idGetter,
                                                                    BiConsumer<Entity, IdType> idSetter, Class<?> entityClass) {
        return idGeneratorUtils.generatorId(entity, idGetter, idSetter, entityClass);
    }

    /**
     * 更新或者创建
     *
     * @param idGetter
     * @param idSetter
     * @param mapper
     * @param <Entity>
     * @param <IdType>
     */
    public <Domain extends Model, Entity, IdType extends Serializable> List<SqlOperation> updateOrInsertOperation(
            Aggregate<Domain> aggregate, Function<Domain, Entity> transferFunc, BiConsumer<Domain, IdType> domainIdSetter,
            Function<Entity, IdType> idGetter, BiConsumer<Entity, IdType> idSetter, BaseMapper<Entity> mapper) {
        Entity entity = null;
        List<SqlOperation> sqlOperations = Lists.newArrayList();

        // 原值新值都是空，不做任何操作
        if (Objects.isNull(aggregate.getAggregate()) && Objects.isNull(aggregate.getSnapshot())) {
            return sqlOperations;
        }


        if (aggregate.isDelete() || Objects.isNull(aggregate.getAggregate())) {
            if (Objects.isNull(aggregate.getSnapshot())) {
                return sqlOperations;
            }

            // 删除时，取快照里面的id
            entity = transferFunc.apply(aggregate.getSnapshot());
            if (Objects.nonNull(entity)) {
                IdType id = idGetter.apply(entity);
                if (Objects.nonNull(id)) {
                    String entityName = entity.getClass().getSimpleName();
                    if (Objects.nonNull(id)) {
                        log.debug(String.format("[%s]数据删除记录: %s, %s", entityName, 1, JsonUtils.toJson(entity)));
                        sqlOperations
                                .add(new DeleteBatchSqlOperation<>(mapper, entity.getClass(), Lists.newArrayList(id)));
                    }
                }
            }
        } else {
            entity = transferFunc.apply(aggregate.getAggregate());
            if (aggregate.isNew()) {
                if (Objects.nonNull(entity)) {
                    IdType id = idGeneratorUtils.generatorId(entity, idGetter, idSetter);
                    domainIdSetter.accept(aggregate.getAggregate(), id);
                    EntityDefaultValUtils.fillDefaultValueForInsert(entity);
                    String entityName = entity.getClass().getSimpleName();
                    log.debug(String.format("[%s]数据创建记录: %s, %s", entityName, 1, JsonUtils.toJson(entity)));
                    sqlOperations.add(new InsertBatchSqlOperation<>(mapper, Lists.newArrayList(entity)));
                }
            } else {
                Entity snapshot = transferFunc.apply(aggregate.getSnapshot());
                IdType newModelId = idGetter.apply(entity);
                IdType oldModelId = idGetter.apply(snapshot);
                if (Objects.equals(newModelId, oldModelId)) {
                    Set<String> changedFields = DeepEquals.getChangedFields(snapshot, entity);
                    if (CollectionUtils.isNotEmpty(changedFields)) {
                        EntityDefaultValUtils.fillDefaultValueForUpdate(entity);
                        changedFields.add(EntityDefaultValUtils.FIELD_MODIFY_TIME);
                        String entityName = entity.getClass().getSimpleName();
                        log.debug(String.format("[%s]数据更新记录: %s, entity: %s, %s", entityName, 1,
                                JsonUtils.toJson(changedFields), JsonUtils.toJson(entity)));
                        sqlOperations.add(new UpdateSomeColumnSqlOperation<>(mapper, entity, changedFields));
                    }
                } else {
                    IdType id = idGeneratorUtils.generatorId(entity, idGetter, idSetter);
                    domainIdSetter.accept(aggregate.getAggregate(), id);
                    EntityDefaultValUtils.fillDefaultValueForInsert(entity);
                    String entityName = entity.getClass().getSimpleName();
                    log.debug(String.format("[%s]数据创建记录: %s, %s", entityName, 1, JsonUtils.toJson(entity)));
                    sqlOperations.add(new InsertBatchSqlOperation<>(mapper, Lists.newArrayList(entity)));
                    log.debug(String.format("[%s]数据删除记录: %s, %s", entityName, 1, JsonUtils.toJson(snapshot)));
                    sqlOperations.add(new DeleteBatchSqlOperation<>(mapper, entity.getClass(), Lists.newArrayList(oldModelId)));
                }
            }
        }

        return sqlOperations;
    }

    /**
     * 批量更新或者创建
     *
     * @param aggregate
     * @param idGetter
     * @param idSetter
     * @param mapper
     * @param <Entity>
     * @param <IdType>
     */
    public <Domain extends Model, Entity, IdType extends Serializable, Mapper extends BaseMapper<Entity>>
    List<SqlOperation> batchUpdateOrInsertOperations(CollectionAggregate<Domain> aggregate,
                                                     Function<Domain, Entity> transferFunc, BiConsumer<Domain, IdType> domainIdSetter,
                                                     Function<Entity, IdType> idGetter, BiConsumer<Entity, IdType> idSetter, Mapper mapper) {

        List<SqlOperation> sqlOperations = Lists.newArrayList();

        if (aggregate.isDelete()) {
            // 删除直接删除快照id列表
            List<Entity> entities = Streams.toList(aggregate.getSnapshot(), d -> transferFunc.apply(d));
            if (CollectionUtils.isNotEmpty(entities)) {
                List<IdType> deleteIds = Streams.toList(entities, e -> idGetter.apply(e));
                if (CollectionUtils.isNotEmpty(deleteIds)) {
                    String entityName = entities.get(0).getClass().getSimpleName();
                    log.debug(
                            String.format("[%s]数据删除记录: %s, %s", entityName, entities.size(), JsonUtils.toJson(entities)));
                    sqlOperations.add(new DeleteBatchSqlOperation<>(mapper, entities.get(0).getClass(), deleteIds));
                }
            }
        } else {
            Map<Entity, Domain> entityToDomainMap = new HashMap<>();
            List<Entity> entities = Lists.newArrayListWithExpectedSize(aggregate.getAggregate().size());
            aggregate.getAggregate().forEach(domain -> {
                Entity entity = transferFunc.apply(domain);
                if (Objects.nonNull(entity)) {
                    entityToDomainMap.put(entity, domain);
                    entities.add(entity);
                }
            });
            Collection<Entity> snapshots = null;
            if (Objects.nonNull(aggregate.getSnapshot())) {
                snapshots = collectionFunc(transferFunc).apply(aggregate.getSnapshot());
            }
            CollectionDiff.CollectionDiffResult<Entity> diffResult = collectionDiff(entities, snapshots, idGetter);
            sqlOperations.addAll(batchUpdateOrInsertSomeFieldsOperations(diffResult.getInsertList(),
                    diffResult.getUpdateList(), diffResult.getDeleteList(), idGetter, idSetter, mapper));
            // 填充新增的模型id
            diffResult.getInsertList().forEach(r -> {
                Domain domain = entityToDomainMap.get(r);
                if (Objects.nonNull(domain)) {
                    domainIdSetter.accept(domain, idGetter.apply(r));
                }
            });
        }
        return sqlOperations;
    }

    /**
     * 批量更新或者创建
     *
     * @param insertEntities
     * @param updateEntities
     * @param deleteEntities
     * @param idGetter
     * @param idSetter
     * @param mapper
     * @param <Entity>
     * @param <IdType>
     */
    public <Entity, IdType extends Serializable> List<SqlOperation> batchUpdateOrInsertSomeFieldsOperations(
            List<Entity> insertEntities, List<CollectionDiff.ChangedRecord<Entity>> updateEntities,
            List<Entity> deleteEntities, Function<Entity, IdType> idGetter, BiConsumer<Entity, IdType> idSetter,
            BaseMapper<Entity> mapper) {

        List<SqlOperation> sqlOperations = Lists.newArrayList();

        // 自动生成id
        for (Entity entity : insertEntities) {
            idGeneratorUtils.generatorId(entity, idGetter, idSetter);
        }
        // 删除实体的id列表
        List<IdType> deleteIds = deleteEntities.stream().map(idGetter).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(insertEntities)) {
            insertEntities.forEach(e -> EntityDefaultValUtils.fillDefaultValueForInsert(e));
            String entityName = insertEntities.get(0).getClass().getSimpleName();
            log.info(String.format("[%s]数据创建记录: %s, %s", entityName, insertEntities.size(),
                    JsonUtils.toJson(insertEntities)));
            sqlOperations.add(new InsertBatchSqlOperation<>(mapper, insertEntities));
        }
        if (CollectionUtils.isNotEmpty(updateEntities)) {
            String entityName = updateEntities.get(0).getRecord().getClass().getSimpleName();
            updateEntities.forEach(e -> {
                EntityDefaultValUtils.fillDefaultValueForUpdate(e.getRecord());
                e.getChangedFields().add(EntityDefaultValUtils.FIELD_MODIFY_TIME);
                log.info(String.format("[%s]数据更新记录: %s, entity: %s", entityName, JsonUtils.toJson(e.getChangedFields()),
                        JsonUtils.toJson(e.getRecord())));
                sqlOperations.add(new UpdateSomeColumnSqlOperation<>(mapper, e.getRecord(), e.getChangedFields()));
            });
        }
        if (CollectionUtils.isNotEmpty(deleteIds)) {
            String entityName = deleteEntities.get(0).getClass().getSimpleName();
            Class<?> aClass = deleteEntities.get(0).getClass();
            log.info(String.format("[%s]数据删除记录: %s, %s", entityName, insertEntities.size(),
                    JsonUtils.toJson(insertEntities)));
            sqlOperations.add(new DeleteBatchSqlOperation<>(mapper, aClass, deleteIds));
        }

        return sqlOperations;
    }

    /**
     * 执行Sql操作
     *
     * @param sqlOperations
     * @param batchSize
     */
    public <T extends SqlOperation> void executeSqlOperation(List<SqlOperation> sqlOperations, Integer batchSize) {
        if (CollectionUtils.isEmpty(sqlOperations)) {
            return;
        }
        // 批量执行数量
        Integer executeBatchSize = Objects.isNull(batchSize) ? defaultBatchSize : batchSize;

        try {
            // 根据entity分组
            Map<Class<?>, List<SqlOperation>> entityClassMaps =
                    Streams.groupBy(sqlOperations, SqlOperation::entityClass);
            if (MapUtils.isNotEmpty(entityClassMaps)) {
                SqlSession sqlSession = null;

                for (Map.Entry<Class<?>, List<SqlOperation>> entry : entityClassMaps.entrySet()) {
                    if (Objects.equals(entry.getKey(), Object.class)) {
                        continue;
                    }
                    // 获取数据库该实例的sqlSession
                    sqlSession = SqlHelper.sqlSessionBatch(entry.getKey());
                    // 获取对应的批量操作
                    List<SqlOperation> entitySqlOperations = entry.getValue();
                    Map<Class<? extends SqlOperation>, List<SqlOperation>> sqlOperationTypeGroup =
                            Streams.groupBy(entitySqlOperations, SqlOperation::getClass);
                    // 优先按指定顺序执行 -> 删除 -> 插入 -> 更新
                    for (Class<? extends SqlOperation> sqlExecuteSequence : BasicMergeSqlFactory.SQL_EXECUTE_SEQUENCES) {
                        List<T> sqlOps = (List<T>) sqlOperationTypeGroup.remove(sqlExecuteSequence);
                        if (Objects.nonNull(sqlOps)) {
                            MergeSqlStrategy<T> mergeSqlStrategy = mergeSqlFactory.matchMergeSqlOperation(sqlExecuteSequence);
                            mergeSqlStrategy.mergeExecute(sqlOps, sqlSession, executeBatchSize);
                        }
                    }
                    // 剩下的SqlOps
                    for (Map.Entry<Class<? extends SqlOperation>,
                            List<SqlOperation>> sqlOperationEntry : sqlOperationTypeGroup.entrySet()) {
                        if (Objects.nonNull(sqlOperationEntry.getKey())) {
                            MergeSqlStrategy<T> mergeSqlStrategy =
                                    mergeSqlFactory.matchMergeSqlOperation(sqlOperationEntry.getKey());
                            mergeSqlStrategy.mergeExecute((List<T>) sqlOperationEntry.getValue(), sqlSession,
                                    executeBatchSize);
                        }
                    }
                }
            }
        } catch (Throwable e) {
            throw ThrowableProcessor.processThrowable(e);
        }
    }

    /**
     * 执行Sql操作
     *
     * @param sqlOperations
     */
    @Transactional(rollbackFor = Exception.class)
    public <T extends SqlOperation> void executeSqlOperation(List<SqlOperation> sqlOperations) {
        executeSqlOperation(sqlOperations, defaultBatchSize);
    }

    /**
     * 注册sql合并策略
     *
     * @param mergeSqlStrategy
     */
    public void registerMergeSqlStrategy(MergeSqlStrategy mergeSqlStrategy) {
        mergeSqlFactory.registerMergeSqlStrategy(mergeSqlStrategy);
    }

    /**
     * 执行或者记录批量SQL操作
     *
     * @param sqlOperations
     */
    private void invokeOrRecordBatchSqlOperations(List<SqlOperation> sqlOperations) {
        // 是否批量执行
        if (Repositorys.getCtx().isEnableBatchExecute()) {
            Repositorys.getCtx().addSqlOperations(sqlOperations);
        } else {
            executeSqlOperation(sqlOperations, defaultBatchSize);
        }
    }

}
