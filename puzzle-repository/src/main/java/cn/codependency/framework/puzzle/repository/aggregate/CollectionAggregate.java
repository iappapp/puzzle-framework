package cn.codependency.framework.puzzle.repository.aggregate;

import com.google.common.collect.Lists;
import cn.codependency.framework.puzzle.common.Streams;
import cn.codependency.framework.puzzle.model.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.function.Function;

@Getter
@Setter
@AllArgsConstructor
public class CollectionAggregate<T extends Model> {

    /**
     * 领域对象
     */
    protected Collection<T> aggregate;

    /**
     * 快照
     */
    protected Collection<T> snapshot;

    /**
     * 删除标记
     */
    private boolean delete;

    /**
     * 聚合根集合
     * @param aggregate
     * @param snapshot
     */
    public CollectionAggregate(Collection<T> aggregate, Collection<T> snapshot) {
        this.aggregate = aggregate;
        this.snapshot = snapshot;
    }

    /**
     * 获取集合类对象
     *
     * @param getFunc
     * @param <R>
     * @return
     */
    public <R extends Model> CollectionAggregate<R> getCollectionAggregate(Function<Collection<T>, Collection<R>> getFunc) {
        return AggregateFactory.buildCollectionAggregate(
                Objects.isNull(aggregate) ? new ArrayList<R>() : getFunc.apply(aggregate),
                isNew() ? new ArrayList<R>() : Objects.isNull(snapshot) ? new ArrayList<R>() : getFunc.apply(snapshot),
                delete);
    }

    /**
     * 拆解聚合集合
     *
     * @param idGetter
     * @param <IdType>
     * @return
     */
    public <IdType> List<Aggregate<T>> fetchAggregateList(Function<T, IdType> idGetter) {
        List<Aggregate<T>> aggregateList = Lists.newArrayListWithCapacity(aggregate.size());
        Map<IdType, List<T>> snapshotMap = Streams.groupBy(snapshot, idGetter);
        for (T t : aggregate) {
            IdType id = idGetter.apply(t);
            if (Objects.isNull(id)) {
                aggregateList.add(AggregateFactory.createAggregate(t, delete));
            } else {
                List<T> ts = snapshotMap.get(id);
                // 未匹配到快照，新增
                if (CollectionUtils.isEmpty(ts)) {
                    aggregateList.add(AggregateFactory.createAggregate(t, delete));
                } else {
                    // 匹配到id一致，创建聚合
                    aggregateList.add(AggregateFactory.buildAggregate(t, ts.get(0), delete));
                }
            }
        }
        return aggregateList;
    }

    public <IdType> List<Aggregate<T>> fetchAggregateListWithDeleted(Function<T, IdType> idGetter) {
        List<Aggregate<T>> aggregateList = Lists.newArrayListWithCapacity(aggregate.size());
        Map<IdType, List<T>> snapshotMap = Streams.groupBy(snapshot, idGetter);
        for (T t : aggregate) {
            IdType id = idGetter.apply(t);
            if (Objects.isNull(id)) {
                aggregateList.add(AggregateFactory.createAggregate(t, delete));
            } else {
                List<T> ts = snapshotMap.remove(id);
                // 未匹配到快照，新增
                if (CollectionUtils.isEmpty(ts)) {
                    aggregateList.add(AggregateFactory.createAggregate(t, delete));
                } else {
                    // 匹配到id一致，创建聚合
                    aggregateList.add(AggregateFactory.buildAggregate(t, ts.get(0), delete));
                }
            }
        }
        if (!snapshotMap.isEmpty()) {
            for (List<T> snapshots : snapshotMap.values()) {
                if (CollectionUtils.isNotEmpty(snapshots)) {
                    T t = snapshots.get(0);
                    AggregateRoot<T> tAggregateRoot = AggregateFactory.buildAggregateRoot(t);
                    tAggregateRoot.setDelete(true);
                    aggregateList.add(tAggregateRoot);
                }
            }
        }
        return aggregateList;
    }

    /**
     * 是否为新增
     *
     * @return
     */
    public boolean isNew() {
        return Objects.isNull(snapshot);
    }
}
