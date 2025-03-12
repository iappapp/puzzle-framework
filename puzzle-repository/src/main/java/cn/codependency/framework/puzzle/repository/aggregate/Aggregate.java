package cn.codependency.framework.puzzle.repository.aggregate;

import cn.codependency.framework.puzzle.common.DeepEquals;
import cn.codependency.framework.puzzle.common.Streams;
import cn.codependency.framework.puzzle.common.copy.DeepCopier;
import cn.codependency.framework.puzzle.model.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

@Getter
@AllArgsConstructor
public class Aggregate<T extends Model> {

    /**
     * 领域对象
     */
    private T aggregate;

    /**
     * 快照
     */
    private T snapshot;

    /**
     * 删除
     */
    private boolean delete;

    Aggregate(T aggregate, T snapshot) {
        this.aggregate = aggregate;
        this.snapshot = snapshot;
    }

    Aggregate(T aggregate, boolean delete) {
        this.aggregate = aggregate;
        this.delete = delete;
    }

    /**
     * 获取聚合对象
     *
     * @param getFunc
     * @param <R>
     * @return
     */
    public <R extends Model> Aggregate<R> getAggregate(Function<T, R> getFunc) {
        return AggregateFactory.buildAggregate(Objects.isNull(aggregate) ? null : getFunc.apply(aggregate),
                isNew() ? null : getFunc.apply(snapshot), isDelete());
    }

    /**
     * 获取集合类对象
     *
     * @param getFunc
     * @param <R>
     * @return
     */
    public <R extends Model> CollectionAggregate<R> getCollectionAggregate(Function<T, Collection<R>> getFunc) {
        return AggregateFactory.buildCollectionAggregate(
                Objects.isNull(aggregate) ? new ArrayList<>() : getFunc.apply(aggregate),
                isNew() ? new ArrayList<>() : getFunc.apply(snapshot), isDelete());
    }

    /**
     * 是否为新增
     *
     * @return
     */
    public boolean isNew() {
        return Objects.isNull(snapshot);
    }

    /**
     * 设置删除
     *
     * @param delete
     */
    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    /**
     * 获取变更的字段
     *
     * @param ignoredFields
     * @param <T>
     * @return
     */
    public <T> Set<String> getChangedFields(String... ignoredFields) {
        Set<String> results = new HashSet();
        Set<String> ignoreFieldSet = new HashSet(Arrays.asList(ignoredFields));
        Collection<Field> fields = DeepEquals.getDeepDeclaredFields(aggregate.getClass());
        if (isNew()) {
            results = Streams.toSet(fields, Field::getName);
            results.removeAll(ignoreFieldSet);
            return results;
        }

        Iterator fieldIterator = fields.iterator();
        while (fieldIterator.hasNext()) {
            Field field = (Field) fieldIterator.next();
            if (!ignoreFieldSet.contains(field.getName())) {
                try {
                    if (!(new DeepEquals()).isDeepEquals(field.get(snapshot), field.get(aggregate))) {
                        results.add(field.getName());
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return results;
    }

    /**
     * 刷新快照
     *
     * @param copier
     */
    protected void refreshSnapshot(DeepCopier copier) {
        this.snapshot = copier.copy(aggregate);
    }

    public void replace(T domain) {
        this.aggregate = domain;
    }

    public boolean isDelete() {
        if (delete) {
            return true;
        }
        return false;
    }
}
