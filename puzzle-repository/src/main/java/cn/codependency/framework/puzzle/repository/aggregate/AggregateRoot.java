package cn.codependency.framework.puzzle.repository.aggregate;

import cn.codependency.framework.puzzle.common.copy.DeepCopier;
import cn.codependency.framework.puzzle.model.Model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AggregateRoot<T extends Model> extends Aggregate<T> {

    /**
     * 聚合根创建
     *
     * @param root
     * @param copier
     */
    AggregateRoot(T root, DeepCopier copier) {
        super(root, copier.copy(root));
    }

    AggregateRoot(T root) {
        super(root, (T) null);
    }


    AggregateRoot(T aggregate, T snapshot) {
        super(aggregate, snapshot);
    }


}
