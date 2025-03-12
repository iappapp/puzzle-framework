package cn.codependency.framework.puzzle.repository.processor;

import cn.codependency.framework.puzzle.model.DataPermissionAction;
import cn.codependency.framework.puzzle.model.RootModel;
import cn.codependency.framework.puzzle.repository.aggregate.AggregateRoot;
import cn.hutool.core.util.ReflectUtil;

import java.util.Objects;

public class DataPermissionRepositoryProcessor implements RepositoryProcessor {

    @Override
    public <T extends RootModel<?>> void beforeCommit(AggregateRoot<T> model) {
        if (!model.isDelete()) {
            T aggregate = model.getAggregate();
            if (Objects.nonNull(aggregate)) {
                try {
                    Object $act = ReflectUtil.invoke(aggregate, "$act");
                    if (Objects.nonNull($act)) {
                        if ($act instanceof DataPermissionAction) {
                            ((DataPermissionAction) $act).setDataPermission();
                        }
                    }
                } catch (Exception ignore) {
                }
            }
        }
    }
}
