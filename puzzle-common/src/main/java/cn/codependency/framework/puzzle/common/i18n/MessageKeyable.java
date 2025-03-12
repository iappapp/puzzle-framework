package cn.codependency.framework.puzzle.common.i18n;

/**
 * 国际化消息
 * 
 * @author Liu Chenwei
 * @date 2021-11-23 11:42
 */
public interface MessageKeyable {

    /**
     * 获取messageKey
     * 
     * @return
     */
    String getMessageKey();

    /**
     * 获取默认中文
     * 
     * @return
     */
    default String getDefaultMessage() {
        return null;
    }

}
