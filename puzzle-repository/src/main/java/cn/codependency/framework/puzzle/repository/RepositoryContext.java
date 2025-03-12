package cn.codependency.framework.puzzle.repository;

import cn.codependency.framework.puzzle.repository.sql.SqlOperation;
import cn.codependency.framework.puzzle.repository.aggregate.AggregateRoot;
import com.google.common.collect.Lists;
import cn.codependency.framework.puzzle.common.Errors;
import cn.codependency.framework.puzzle.model.RootModel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class RepositoryContext {

    @Getter
    private boolean enableBatchExecute = false;

    @Getter
    @Setter
    private Integer batchSize;

    /**
     * 进入次数
     */
    private AtomicInteger entryCounts = new AtomicInteger();

    /**
     * 自动清理
     */
    @Getter
    @Setter
    private Boolean autoCommit = false;

    /**
     * 待执行的sql
     */
    @Getter
    private List<SqlOperation> sqlOperations = Lists.newArrayListWithCapacity(100);

    @Getter
    private List<Runnable> afterCommitSuccessRunners = Lists.newArrayListWithCapacity(20);

    @Getter
    private List<Runnable> afterCommitRunners = Lists.newArrayListWithCapacity(20);

    @Getter
    private Set<RootModel<? extends Serializable>> aggregateRoots = new LinkedHashSet<>(64);
    /**
     * 聚合根集合
     */
    private Map<String, AggregateRoot<? extends RootModel<? extends Serializable>>> aggregateRootMap =
            new HashMap<>(64);

    /**
     * 获取聚合根
     *
     * @param id
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends RootModel<IdType>, IdType extends Serializable> AggregateRoot<T> getAggregateRoot(IdType id, Class<?> clazz) {
        if (Objects.isNull(id)) {
            return null;
        }
        return (AggregateRoot<T>) aggregateRootMap.get(buildAggregateRootId(id, clazz));
    }

    public <T extends RootModel<IdType>, IdType extends Serializable> void removeAggregateRoot(IdType id,
                                                                                               Class<?> clazz) {
        AggregateRoot<? extends RootModel<? extends Serializable>> remove =
                aggregateRootMap.remove(buildAggregateRootId(id, clazz));
        if (Objects.nonNull(remove)) {
            for (Class<?> removeClazz : RepositoryReflectUtils
                    .fetchAllRootModelClasses(remove.getAggregate().getClass())) {
                aggregateRootMap.put(buildAggregateRootId(id, removeClazz), remove);
            }
            aggregateRoots.remove(remove.getAggregate());
        }
    }

    /**
     * 保存聚合根
     *
     * @param id
     * @param aggregateRoot
     */
    public void cacheAggregateRoot(Serializable id,
                                   AggregateRoot<? extends RootModel<? extends Serializable>> aggregateRoot) {
        if (Objects.isNull(id)) {
            RootModel<? extends Serializable> aggregate = aggregateRoot.getAggregate();
            String rootDomainClazz = null;
            if (Objects.nonNull(aggregate)) {
                rootDomainClazz = aggregateRoot.getAggregate().getClass().getName();
            }
            throw Errors.system("缓存聚合根时，聚合根的id不能为空，请检查查询方法实现时是否赋值聚合根id。聚合根类型: " + rootDomainClazz);
        }
        if (aggregateRoots.add(aggregateRoot.getAggregate())) {
            for (Class<?> clazz : RepositoryReflectUtils
                    .fetchAllRootModelClasses(aggregateRoot.getAggregate().getClass())) {
                aggregateRootMap.put(buildAggregateRootId(id, clazz), aggregateRoot);
            }
        }
    }

    /**
     * 增加重入次数
     *
     * @return
     */
    protected int increaseReentryTimes() {
        return this.entryCounts.addAndGet(1);
    }

    /**
     * 释放重入次数
     *
     * @return
     */
    protected int releaseReentryTimes() {
        return entryCounts.addAndGet(-1);
    }

    /**
     * 支持批量执行
     */
    protected boolean enableBatchExecute(Integer batchSize) {
        boolean lastSwitch = enableBatchExecute;
        this.enableBatchExecute = true;
        this.batchSize = batchSize;
        return lastSwitch;
    }

    /**
     * 不支持批量执行
     */
    protected void disableBatchExecute() {
        this.enableBatchExecute = false;
        this.batchSize = null;
    }

    /**
     * 添加执行sql
     *
     * @param sqlOperations
     */
    protected void addSqlOperations(List<SqlOperation> sqlOperations) {
        if (CollectionUtils.isNotEmpty(sqlOperations)) {
            this.sqlOperations.addAll(sqlOperations);
        }
    }

    /**
     * 添加执行sql
     *
     * @param sqlOperation
     */
    protected void addSqlOperation(SqlOperation sqlOperation) {
        if (Objects.nonNull(sqlOperation)) {
            this.sqlOperations.add(sqlOperation);
        }
    }

    /**
     * 清空聚合根集合
     */
    protected void clearAggregates() {
        sqlOperations.clear();
        aggregateRoots.clear();
        aggregateRootMap.clear();
    }

    /**
     * 构建聚合根存储id
     *
     * @param id
     * @param clazz
     * @param <T>
     * @return
     */
    private <T extends RootModel<IdType>, IdType extends Serializable> String buildAggregateRootId(IdType id,
                                                                                                   Class<?> clazz) {
        return String.format("%s#%s", clazz.getName(), id);
    }
}
