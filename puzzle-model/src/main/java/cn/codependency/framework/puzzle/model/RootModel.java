package cn.codependency.framework.puzzle.model;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 根模型基类
 */
@Getter
@Setter
public class RootModel<IdType extends Serializable> extends Model {

    /**
     * 根模型id
     */
    protected IdType id;
}
