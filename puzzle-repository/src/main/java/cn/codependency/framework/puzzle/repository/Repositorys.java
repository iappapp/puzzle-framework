package cn.codependency.framework.puzzle.repository;

import cn.codependency.framework.puzzle.common.Errors;
import cn.codependency.framework.puzzle.common.Streams;
import cn.codependency.framework.puzzle.common.SupplierWithThrowable;
import cn.codependency.framework.puzzle.common.json.JsonUtils;
import cn.codependency.framework.puzzle.model.RootModel;
import cn.codependency.framework.puzzle.repository.aggregate.AggregateFactory;
import cn.codependency.framework.puzzle.repository.aggregate.AggregateRoot;
import cn.codependency.framework.puzzle.repository.processor.RepositoryProcessor;
import cn.codependency.framework.puzzle.repository.query.ListRelationQuery;
import cn.codependency.framework.puzzle.repository.query.Query;
import cn.codependency.framework.puzzle.repository.query.Relation;
import cn.codependency.framework.puzzle.repository.query.SingleRelationQuery;
import cn.codependency.framework.puzzle.repository.utils.BeanUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;


@Slf4j
public class Repositorys {

    /**
     * 仓储上下文
     */
    private static final ThreadLocal<RepositoryContext> REPOSITORY_CTX =
            ThreadLocal.withInitial(() -> new RepositoryContext());

    @Getter
    @Setter
    private List<RepositoryProcessor> processors = new ArrayList<>();

    /**
     * 清空上下文
     */
    public void clear() {
        REPOSITORY_CTX.get().clearAggregates();
    }

    public void remove() {
        REPOSITORY_CTX.remove();
    }

    /**
     * 增加重入次数
     *
     * @return
     */
    public int increaseReentryTimes() {
        return getCtx().increaseReentryTimes();
    }

    /**
     * 减少重入次数
     *
     * @return
     */
    public int releaseReentryTimes() {
        return getCtx().releaseReentryTimes();
    }


    public static int DEFAULT_BATCH_COMMIT_SIZE = 1000;

    /**
     * 获取上下文
     *
     * @return
     */
    protected static RepositoryContext getCtx() {
        return REPOSITORY_CTX.get();
    }

    /**
     * 构造仓储
     *
     * @param repositoryFactory
     */
    public Repositorys(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
    }

    /**
     * 仓储工厂
     */
    private RepositoryFactory repositoryFactory;

    @Resource
    private RepositoryUtils repositoryUtils;


    public <T extends RootModel<IdType>, IdType extends Serializable> void unmark(T rootDomain) {
        RepositoryContext repositoryContext = REPOSITORY_CTX.get();
        repositoryContext.removeAggregateRoot(rootDomain.getId(), rootDomain.getClass());
    }

    /**
     * 标记聚合根
     *
     * @param rootDomain
     * @param <T>
     */
    protected <T extends RootModel<IdType>, IdType extends Serializable> void mark(T rootDomain) {
        this.mark(Lists.newArrayList(rootDomain));
    }


    /**
     * 标记聚合根
     *
     * @param rootDomains
     * @param <T>
     */
    protected <T extends RootModel<IdType>, IdType extends Serializable> void mark(List<T> rootDomains) {
        RepositoryContext repositoryContext = REPOSITORY_CTX.get();
        for (T rootDomain : rootDomains) {
            AggregateRoot<? extends RootModel> aggregateRoot =
                    repositoryContext.getAggregateRoot(rootDomain.getId(), rootDomain.getClass());
            if (Objects.nonNull(aggregateRoot)) {
                // 不重复标记
                continue;
            }
            repositoryContext.cacheAggregateRoot(rootDomain.getId(), AggregateFactory.buildAggregateRoot(rootDomain));
        }
    }


    /**
     * 标记删除聚合根
     *
     * @param rootDomains
     * @param <T>
     */
    public <T extends RootModel<IdType>, IdType extends Serializable> void delete(List<T> rootDomains) {
        if (CollectionUtils.isEmpty(rootDomains)) {
            return;
        }
        rootDomains.forEach(this::delete);
    }

    /**
     * 判断聚合根是否为删除状态
     *
     * @param rootModel
     * @return
     */
    public boolean isDeleted(RootModel<?> rootModel) {
        RepositoryContext repositoryContext = REPOSITORY_CTX.get();
        AggregateRoot<? extends RootModel<?>> aggregateRoot =
                repositoryContext.getAggregateRoot(rootModel.getId(), rootModel.getClass());
        if (Objects.nonNull(aggregateRoot)) {
            return aggregateRoot.isDelete();
        }
        return false;
    }

    /**
     * 标记删除聚合根
     *
     * @param rootDomain
     * @param <T>
     */
    public <T extends RootModel<IdType>, IdType extends Serializable> void delete(T rootDomain) {
        RepositoryContext repositoryContext = REPOSITORY_CTX.get();
        AggregateRoot<? extends RootModel> aggregateRoot =
                repositoryContext.getAggregateRoot(rootDomain.getId(), rootDomain.getClass());
        if (Objects.nonNull(aggregateRoot)) {
            aggregateRoot.setDelete(true);
        }
    }

    /**
     * 创建聚合根
     *
     * @param rootDomain
     * @param <T>
     */
    public <T extends RootModel<IdType>, IdType extends Serializable> T create(T rootDomain) {
        RepositoryContext repositoryContext = REPOSITORY_CTX.get();
        if (Objects.isNull(rootDomain.getId())) {
            IdType id = repositoryFactory.generateId(rootDomain);
            if (Objects.isNull(id)) {
                throw Errors.system(
                        "Repository未配置id生成策略，实现Repository的接口方法。cn.codependency.framework.puzzle.repository.Repository.getIdGenerator");
            }
            rootDomain.setId(id);
        }
        repositoryContext.cacheAggregateRoot(rootDomain.getId(), AggregateFactory.createAggregateRoot(rootDomain));
        return rootDomain;
    }

    @SneakyThrows
    public <T extends RootModel<IdType>, IdType extends Serializable> T create(Class<T> clazz) {
        T t = clazz.newInstance();
        create(t);
        return t;
    }

    @SneakyThrows
    public <T extends RootModel<IdType>, IdType extends Serializable> T create(Class<T> clazz, IdType id) {
        T t = clazz.newInstance();
        if (Objects.nonNull(id)) {
            t.setId(id);
        }
        create(t);
        return t;
    }


    /**
     * 获取聚合根
     *
     * @param id
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends RootModel<IdType>, IdType extends Serializable> T getById(IdType id, Class<T> clazz) {
        return getById(id, clazz, null);
    }

    /**
     * 获取聚合根
     *
     * @param id
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends RootModel<IdType>, IdType extends Serializable> T getById(IdType id, Class<T> clazz, Supplier<? extends RuntimeException> exceptionSupplier) {
        RepositoryContext repositoryContext = REPOSITORY_CTX.get();
        if (Objects.isNull(id)) {
            if (Objects.nonNull(exceptionSupplier)) {
                throw exceptionSupplier.get();
            }
            return null;
        }
        AggregateRoot<T> aggregateRoot = repositoryContext.getAggregateRoot(id, clazz);
        if (Objects.nonNull(aggregateRoot)) {
            log.debug("从本地缓存中获取聚合根: ({}#{})", clazz, id);
            return aggregateRoot.getAggregate();
        }
        T rootDomain = repositoryFactory.getById(id, clazz);
        if (Objects.isNull(rootDomain)) {
            if (Objects.nonNull(exceptionSupplier)) {
                throw exceptionSupplier.get();
            }
        } else {
            log.debug("从数据库中加载聚合根: ({}#{})", clazz, id);
            // 出场时标记一下聚合根
            this.mark(rootDomain);
        }
        return rootDomain;
    }


    public <T extends RootModel<IdType>, IdType extends Serializable> AggregateRoot<T> root(T domain) {
        if (Objects.isNull(domain) || Objects.isNull(domain.getId())) {
            return null;
        }
        RepositoryContext repositoryContext = REPOSITORY_CTX.get();
        AggregateRoot<T> aggregateRoot = repositoryContext.getAggregateRoot(domain.getId(), domain.getClass());
        return aggregateRoot;
    }


    /**
     * 通过id列表查询
     *
     * @param ids
     * @param clazz
     * @param <T>
     * @param <IdType>
     * @return
     */
    public <R, T extends RootModel<IdType>, IdType extends Serializable> List<R> listByIds(List<IdType> ids,
                                                                                           Class<T> clazz) {
        if (Objects.isNull(ids)) {
            ids = Lists.newArrayList();
        }
        RepositoryContext repositoryContext = REPOSITORY_CTX.get();
        Map<IdType, R> domainMap = Maps.newHashMap();
        List<IdType> queryIds = Lists.newArrayListWithExpectedSize(ids.size());
        for (IdType id : ids) {
            AggregateRoot<T> aggregateRoot = repositoryContext.getAggregateRoot(id, clazz);
            if (Objects.nonNull(aggregateRoot)) {
                log.debug("从本地缓存中获取聚合根: ({}#{})", clazz, id);
                domainMap.put(id, (R) aggregateRoot.getAggregate());
                continue;
            }
            queryIds.add(id);
        }
        if (CollectionUtils.isNotEmpty(queryIds)) {
            log.debug("从数据库中加载聚合根: ({}#{})", clazz, JsonUtils.toJson(queryIds));
            List<T> queryDomains = repositoryFactory.getByIds(queryIds, clazz);
            if (CollectionUtils.isNotEmpty(queryDomains)) {
                // 标记所有出厂的聚合根
                this.mark(queryDomains);
                for (T queryDomain : queryDomains) {
                    domainMap.put(queryDomain.getId(), (R) queryDomain);
                }
            }
        }
        // 按顺序返回模型列表
        final List<R> domainList = Streams.toList(ids, domainMap::get);
        return Objects.isNull(domainList) ? Lists.newArrayList() : domainList;
    }


    /**
     * 查询关联
     *
     * @param relationQuery
     * @param <R>
     * @param <T>
     * @param <IdType>
     * @return
     */
    public <R, T extends RootModel<IdType>, IdType extends Serializable> R
    getByRelation(Relation<R, T> relationQuery) {
        if (relationQuery instanceof ListRelationQuery) {
            ListRelationQuery<T, IdType> listRelationQuery = (ListRelationQuery<T, IdType>) relationQuery;
            List<IdType> ids = repositoryFactory.query(listRelationQuery);
            if (CollectionUtils.isEmpty(ids)) {
                return (R) Lists.newArrayList();
            }
            return (R) listByIds(ids, listRelationQuery.getRootModelClass());
        } else if (relationQuery instanceof SingleRelationQuery) {
            SingleRelationQuery<T, IdType> listRelationQuery = (SingleRelationQuery<T, IdType>) relationQuery;
            IdType id = repositoryFactory.query(listRelationQuery);
            if (Objects.isNull(id)) {
                return null;
            }
            return (R) getById(id, listRelationQuery.getRelationClass());
        }
        return null;
    }

    /**
     * 自定义查询
     *
     * @param query
     * @param <R>
     * @return
     */
    public <R> R query(Query<R> query) {
        if (Objects.isNull(query)) {
            return null;
        }
        return repositoryFactory.query(query);
    }


    /**
     * 保存上下文中所有
     */
    @Transactional(rollbackFor = Exception.class)
    public void commit() {
        commit(DEFAULT_BATCH_COMMIT_SIZE);
    }


    /**
     * 自动提交
     *
     * @param supplier
     */
    public <E extends Throwable> Object autoCommit(SupplierWithThrowable<Object, E> supplier) throws E {
        if (getCtx().getAutoCommit()) {
            return supplier.get();
        } else {
            getCtx().setAutoCommit(true);
            boolean success = true;
            try {
                return supplier.get();
            } catch (Exception e) {
                success = false;
                throw e;
            } finally {
                try {
                    getCtx().setAutoCommit(false);
                    if (success) {
                        BeanUtils.get(Repositorys.class).commit();
                        // 执行提交后的逻辑
                        this.doAfterCommitSuccess();
                    }
                } finally {
                    this.doAfterCommit();
                }
            }
        }
    }

    private void doAfterCommitSuccess() {
        // 提交后执行，先快照下来
        List<Runnable> runnables = Lists.newArrayList(getCtx().getAfterCommitSuccessRunners());
        // 先清空后执行，避免递归
        getCtx().getAfterCommitSuccessRunners().clear();
        for (Runnable runnable : runnables) {
            try {
                runnable.run();
            } catch (Exception e) {
                log.error("提交成功后执行失败: " + runnable.getClass().getName(), e);
            }
        }
    }


    private void doAfterCommit() {
        // 提交后执行，先快照下来
        List<Runnable> runnables = Lists.newArrayList(getCtx().getAfterCommitRunners());
        // 先清空后执行，避免递归
        getCtx().getAfterCommitRunners().clear();
        for (Runnable runnable : runnables) {
            try {
                runnable.run();
            } catch (Exception e) {
                log.error("提交后执行失败: " + runnable.getClass().getName(), e);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public <IdClass extends Serializable> void commit(int batchSize) {
        this.batchExecuteWrapper(batchSize, () -> {
            for (RootModel<? extends Serializable> aggregateRoot : new LinkedHashSet<>(getCtx().getAggregateRoots())) {
                if (Objects.nonNull(aggregateRoot)) {
                    commitInternal(aggregateRoot.getId(), aggregateRoot.getClass(), true);
                }
            }
            // 处理批量执行
            this.processOnBatchExecute();
        });
        // 清空上下文缓存
        getCtx().clearAggregates();
    }

    /**
     * 立刻保存
     *
     * @param rootDomains 待保存的聚合根
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public <IdClass extends Serializable> void commitNow(RootModel<IdClass>... rootDomains) {
        if (Objects.nonNull(rootDomains)) {
            commit(Streams.toList(Arrays.stream(rootDomains), Function.identity()));
        }
    }

    /**
     * 提交后执行
     *
     * @param runnable
     */
    public void afterCommitSuccess(Runnable runnable) {
        getCtx().getAfterCommitSuccessRunners().add(runnable);
    }


    public void afterCommit(Runnable runnable) {
        getCtx().getAfterCommitRunners().add(runnable);
    }


    /**
     * 批量保存
     *
     * @param rootDomains 待保存的聚合根
     * @param <T>
     * @param <IdType>
     */
    private <T extends RootModel<IdType>, IdType extends Serializable> void commit(List<T> rootDomains) {
        if (CollectionUtils.isNotEmpty(rootDomains)) {
            this.batchExecuteWrapper(DEFAULT_BATCH_COMMIT_SIZE, () -> {
                // 普通集合存储
                for (T rootDomain : new ArrayList<>(rootDomains)) {
                    this.commitInternal(rootDomain.getId(), rootDomain.getClass(), true);
                }
                // 处理批量执行
                this.processOnBatchExecute();
            });
        }
    }

    /**
     * 启动批量执行
     *
     * @param batchSize
     */
    public void enableBatchExecute(Integer batchSize) {
        getCtx().enableBatchExecute(batchSize);
    }

    /**
     * 关闭批量执行
     */
    public void disableBatchExecute() {
        getCtx().disableBatchExecute();
    }

    /**
     * 保存(内部)
     *
     * @param id
     * @param clazz
     * @param mustMarked
     * @param <T>
     * @param <IdType>
     */
    private <T extends RootModel<IdType>, IdType extends Serializable> void commitInternal(IdType id, Class<T> clazz,
                                                                                           boolean mustMarked) {
        RepositoryContext repositoryContext = REPOSITORY_CTX.get();
        AggregateRoot<T> aggregateRoot = repositoryContext.getAggregateRoot(id, clazz);
        if (Objects.isNull(aggregateRoot)) {
            if (!mustMarked) {
                log.warn("rootDomain({}#{}) is lazy object without load, don't save!", clazz, id);
                return;
            }
            throw Errors.system(
                    String.format("rootDomain(%s#%s) is not mark in the context, not support to save!", clazz, id));
        }

        try {
            for (RepositoryProcessor processor : processors) {
                processor.beforeCommit(aggregateRoot);
            }
            repositoryFactory.save(aggregateRoot);
        } finally {
            for (RepositoryProcessor processor : processors) {
                processor.afterCommit(aggregateRoot);
            }
        }
        // 刷新快照
        AggregateFactory.refreshSnapshot(aggregateRoot);
    }

    /**
     * 添加处理器
     *
     * @param processor
     */
    public synchronized void addProcessor(RepositoryProcessor processor) {
        if (Objects.isNull(this.processors)) {
            this.processors = Lists.newArrayList();
        }
        this.processors.add(processor);
        this.processors.sort(Comparator.comparingInt(RepositoryProcessor::order));
    }

    /**
     * 处理批量执行
     */
    private void processOnBatchExecute() {
        // 批量执行
        if (getCtx().isEnableBatchExecute()) {
            try {
                repositoryUtils.executeSqlOperation(getCtx().getSqlOperations(), getCtx().getBatchSize());
            } finally {
                getCtx().getSqlOperations().clear();
            }
        }
    }

    private void batchExecuteWrapper(Integer batchSize, Runnable runnable) {
        boolean lastState = getCtx().enableBatchExecute(batchSize);
        try {
            runnable.run();
        } finally {
            if (!lastState) {
                getCtx().disableBatchExecute();
            }
        }
    }

}
