package cn.codependency.framework.puzzle.repository.query;

import cn.codependency.framework.puzzle.model.RootModel;

import java.io.Serializable;

public interface SingleRelationQuery<T extends RootModel<IdType>, IdType extends Serializable>
        extends Query<IdType>, Relation<T, T> {

}
