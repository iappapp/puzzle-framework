package cn.codependency.framework.puzzle.common.id;

public interface IdGenerator<IdType> {

    /**
     * 生成id
     *
     * @return
     */
    IdType generatorId();
}
