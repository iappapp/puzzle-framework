package cn.codependency.framework.puzzle.common.copy;

public interface DeepCopier {

    /**
     * 深度拷贝
     *
     * @param object
     * @param <T>
     * @return
     */
    <T> T copy(T object);

}
