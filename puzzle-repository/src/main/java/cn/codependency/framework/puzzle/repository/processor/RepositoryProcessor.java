package cn.codependency.framework.puzzle.repository.processor;

import cn.codependency.framework.puzzle.model.RootModel;
import cn.codependency.framework.puzzle.repository.aggregate.AggregateRoot;

public interface RepositoryProcessor {

    default int order() {
        return Integer.MAX_VALUE;
    }

    /**
     * 提交前执行
     */
    default <T extends RootModel<?>> void beforeCommit(AggregateRoot<T> model) {

    }

    /**
     * 提交后执行
     */
    default <T extends RootModel<?>> void afterCommit(AggregateRoot<T> model) {

    }

}
