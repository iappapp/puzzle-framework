package cn.codependency.framework.puzzle.common.copy;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

/**
 * JDK序列化 深度拷贝器
 */
public class JdkSerializationCopier implements DeepCopier {

    @Override
    public <T> T copy(T object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Serializable) {
            return (T) SerializationUtils.clone((Serializable) object);
        }
        throw new IllegalArgumentException("该对象不支持Jdk序列化");
    }
}
