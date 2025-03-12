package cn.codependency.framework.puzzle.repository;

import cn.codependency.framework.puzzle.model.RootModel;
import cn.codependency.framework.puzzle.repository.aggregate.AggregateRoot;
import cn.codependency.framework.puzzle.repository.query.Query;

import java.io.Serializable;
import java.util.List;


/**
 * 仓储工厂
 */
public interface RepositoryFactory {

    /**
     * 注册仓储
     *
     * @param repository
     */
    <T extends RootModel<IdType>, IdType extends Serializable> void register(Repository<T, IdType> repository);

    /**
     * 生成id
     *
     * @param rootDomain
     * @param <T>
     * @param <IdType>
     * @return
     */
    <T extends RootModel<IdType>, IdType extends Serializable> IdType generateId(T rootDomain);

    /**
     * 通过id查询聚合根
     *
     * @param id
     * @param clazz
     * @param <T>
     * @return
     */
    <T extends RootModel<IdType>, IdType extends Serializable> T getById(IdType id, Class<T> clazz);

    /**
     * 通过id查询聚合根列表
     *
     * @param ids
     * @param clazz
     * @param <T>
     * @param <IdType>
     * @return
     */
    <T extends RootModel<IdType>, IdType extends Serializable> List<T> getByIds(List<IdType> ids, Class<T> clazz);

    /**
     * 保存聚合根
     *
     * @param rootDomain
     */
    void save(AggregateRoot<?> rootDomain);

    /**
     * 查询
     *
     * @param query
     * @param <R>
     * @return
     */
    <R> R query(Query<R> query);
}
