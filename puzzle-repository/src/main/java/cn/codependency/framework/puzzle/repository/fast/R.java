package cn.codependency.framework.puzzle.repository.fast;

import cn.codependency.framework.puzzle.repository.Repositorys;
import cn.codependency.framework.puzzle.repository.query.Query;
import cn.codependency.framework.puzzle.repository.query.Relation;
import cn.codependency.framework.puzzle.repository.utils.BeanUtils;
import cn.codependency.framework.puzzle.common.SupplierWithThrowable;
import cn.codependency.framework.puzzle.model.RootModel;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public final class R {

    private static Repositorys repositorys;

    public static <T extends RootModel<IdType>, IdType extends Serializable> void delete(List<T> rootDomains) {
        repo().delete(rootDomains);
    }

    public static boolean isDeleted(RootModel<?> rootModel) {
        return repo().isDeleted(rootModel);
    }

    public static <T extends RootModel<IdType>, IdType extends Serializable> void delete(T rootDomain) {
        repo().delete(rootDomain);
    }

    public static <T extends RootModel<IdType>, IdType extends Serializable> T create(T rootDomain) {
        return repo().create(rootDomain);
    }

    public static <T extends RootModel<IdType>, IdType extends Serializable> T create(Class<T> clazz) {
        return repo().create(clazz);
    }

    public static <T extends RootModel<IdType>, IdType extends Serializable> T create(Class<T> clazz, IdType id) {
        return repo().create(clazz, id);
    }

    public static <T extends RootModel<IdType>, IdType extends Serializable> T getById(IdType id, Class<T> clazz) {
        return repo().getById(id, clazz);
    }

    public static <T extends RootModel<IdType>, IdType extends Serializable> T getById(IdType id, Class<T> clazz, Supplier<? extends RuntimeException> exceptionSupplier) {
        return repo().getById(id, clazz, exceptionSupplier);
    }

    public static <R, T extends RootModel<IdType>, IdType extends Serializable> List<R> listByIds(List<IdType> ids, Class<T> clazz) {
        return repo().listByIds(ids, clazz);
    }

    public static <R, T extends RootModel<IdType>, IdType extends Serializable> R getByRelation(Relation<R, T> relationQuery) {
        return repo().getByRelation(relationQuery);
    }

    public static <R> R query(Query<R> query) {
        return repo().query(query);
    }

    public static void commit() {
        repo().commit();
    }

    public static <E extends Throwable> Object autoCommit(SupplierWithThrowable<Object, E> supplier) throws E {
        return repo().autoCommit(supplier);
    }

    public static <IdClass extends Serializable> void commitNow(RootModel<IdClass>... rootDomains) {
        repo().commitNow(rootDomains);
    }

    public static void afterCommitSuccess(Runnable runnable) {
        repo().afterCommitSuccess(runnable);
    }

    public static void afterCommit(Runnable runnable) {
        repo().afterCommit(runnable);
    }

    public static void clear() {
        repo().clear();
    }

    public static void remove() {
        repo().remove();
    }

    public static Repositorys repo() {
        if (Objects.nonNull(repositorys)) {
            return repositorys;
        }
        synchronized (R.class) {
            if (Objects.nonNull(repositorys)) {
                return repositorys;
            }
            repositorys = BeanUtils.get(Repositorys.class);
        }
        return repositorys;
    }

}
