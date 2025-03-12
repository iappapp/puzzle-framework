package cn.codependency.framework.puzzle.repository.aggregate;

import com.google.common.collect.ImmutableList;
import cn.codependency.framework.puzzle.common.copy.DeepCopier;
import cn.codependency.framework.puzzle.model.Model;

import java.util.Collection;
import java.util.Iterator;

public class CollectionAggregateRoot<T extends Model> extends CollectionAggregate<T>
        implements Iterable<AggregateRoot<T>> {

    /**
     * 创建聚合根集合
     *
     * @param aggregate
     * @param copier
     */
    CollectionAggregateRoot(Collection<T> aggregate, DeepCopier copier) {
        super(ImmutableList.copyOf(aggregate), ImmutableList.copyOf(copier.copy(aggregate)));
    }

    @Override
    public Iterator<AggregateRoot<T>> iterator() {
        return new AggregateRootIterator<T>(aggregate.iterator(), snapshot.iterator());
    }

    /**
     * 聚合迭代器
     *
     * @param <T>
     */
    public static class AggregateRootIterator<T extends Model> implements Iterator<AggregateRoot<T>> {

        public AggregateRootIterator(Iterator<T> aggregateIterator, Iterator<T> snapshotIterator) {
            this.aggregateIterator = aggregateIterator;
            this.snapshotIterator = snapshotIterator;
        }

        private Iterator<T> aggregateIterator;

        private Iterator<T> snapshotIterator;

        @Override
        public boolean hasNext() {
            return aggregateIterator.hasNext() && snapshotIterator.hasNext();
        }

        @Override
        public AggregateRoot<T> next() {
            return AggregateFactory.buildAggregateRoot(aggregateIterator.next(), snapshotIterator.next());
        }
    }
}

