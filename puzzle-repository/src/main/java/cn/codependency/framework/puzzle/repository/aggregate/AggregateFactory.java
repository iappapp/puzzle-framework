package cn.codependency.framework.puzzle.repository.aggregate;

import cn.codependency.framework.puzzle.common.copy.DeepCopier;
import cn.codependency.framework.puzzle.common.copy.JdkSerializationCopier;
import cn.codependency.framework.puzzle.model.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class AggregateFactory {

    private AggregateFactory() {}

    /**
     * 快照生成器
     */
    private static DeepCopier copier = new JdkSerializationCopier();

    /**
     * 创建新的聚合根
     *
     * @param root
     * @param <T>
     * @return
     */
    public static <T extends Model> AggregateRoot<T> createAggregateRoot(T root) {
        return new AggregateRoot(root);
    }

    /**
     * 构造聚合根
     *
     * @param root
     * @param <T>
     * @return
     */
    public static <T extends Model> AggregateRoot<T> buildAggregateRoot(T root) {
        return new AggregateRoot(root, copier);
    }

    /**
     * 构造聚合根
     *
     * @param root
     * @param copier
     * @param <T>
     * @return
     */
    public static <T extends Model> AggregateRoot<T> buildAggregateRoot(T root, DeepCopier copier) {
        return new AggregateRoot(root, copier);
    }

    /**
     * 构造聚合根集合
     *
     * @param aggregate
     * @param <R>
     * @return
     */
    public static <R extends Model> CollectionAggregateRoot<R>
    buildCollectionAggregateRoot(Collection<R> aggregate) {
        return new CollectionAggregateRoot<R>(aggregate, copier);
    }

    /**
     * 刷新聚合根内部快照
     *
     * @param root
     * @param <T>
     */
    public static <T extends Model> void refreshSnapshot(AggregateRoot<T> root) {
        root.refreshSnapshot(copier);
    }


    /////////////////////  包内可访问 内部方法 /////////////////////

    /**
     * 构造聚合
     *
     * @param aggregate
     * @param <R>
     * @return
     */
    static <R extends Model> Aggregate<R> createAggregate(R aggregate, boolean delete) {
        return new Aggregate<>(aggregate, delete);
    }

    /**
     * 构造聚合
     *
     * @param aggregate
     * @param snapshot
     * @param <R>
     * @return
     */
    static <R extends Model> Aggregate<R> buildAggregate(R aggregate, R snapshot, boolean delete) {
        return new Aggregate<>(aggregate, snapshot, delete);
    }


    /**
     * 构建聚合根，包内使用，不开放给外部
     *
     * @param root
     * @param snapshot
     * @param <T>
     * @return
     */
    static <T extends Model> AggregateRoot<T> buildAggregateRoot(T root, T snapshot) {
        return new AggregateRoot<>(root, snapshot);
    }

    /**
     * 构造聚合根集合
     *
     * @param aggregate
     * @param snapshot
     * @param <R>
     * @return
     */
    static <R extends Model> CollectionAggregate<R> buildCollectionAggregate(Collection<R> aggregate,
                                                                             Collection<R> snapshot, boolean delete) {
        return new CollectionAggregate<R>(Objects.isNull(aggregate) ? new ArrayList<>() : aggregate,
                Objects.isNull(snapshot) ? new ArrayList<>() : snapshot, delete);
    }
}
