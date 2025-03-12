package cn.codependency.framework.puzzle.common.error;

import cn.codependency.framework.puzzle.common.i18n.MessageKeyable;
import cn.codependency.framework.puzzle.common.i18n.MessageUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * 基本错误码, 带前缀
 *
 * @author Liu Chenwei
 * @date 2021-10-15 16:18
 */
public interface ErrorCode extends MessageKeyable, ResultCode {

    /**
     * 一类错误码数量
     */
    Integer ERROR_CODE_SIZE = 10000;

    /**
     * 错误码前缀
     *
     * @return
     */
    default Integer getCodePrefix() {
        return 0;
    }

    /**
     * 错误码
     *
     * @return
     */
    Integer getErrorCode();

    /**
     * 组装errorCode
     *
     * @return
     */
    @Override
    default Integer getCode() {
        return getCodePrefix() * ERROR_CODE_SIZE + this.getErrorCode();
    }


    /**
     * 获取错误信息
     *
     * @return
     */
    default String getMessage(Object... params) {
        return MessageUtils.get(this, params);
    }

    /**
     * 获取错误信息
     *
     * @return
     */
    @Override
    default String getMessage() {
        return MessageUtils.get(this);
    }

    /**
     * 构建消息format后的异常消息
     *
     * @param params
     * @return
     */
    default InnerResultCode format(Object... params) {
        return new InnerResultCode(this.getCode(), this, params);
    }

    @Setter
    class InnerResultCode implements ResultCode, MessageKeyable {
        /**
         * 错误码
         */
        private Integer code;
        /**
         * 消息code
         */
        private String messageCode;

        /**
         * 默认消息
         */
        @Getter
        private String defaultMessage;

        /**
         * 国际化后的消息
         */
        private String message;
        /**
         * 参数
         */
        private Object[] params;
        /**
         * 是否已经国际化
         */
        private boolean translate;

        public InnerResultCode(Integer code, String messageCode, Object[] params) {
            this.code = code;
            this.messageCode = messageCode;
            this.params = params;
        }

        public InnerResultCode(Integer code, MessageKeyable messageKeyable, Object[] params) {
            this.code = code;
            this.messageCode = messageKeyable.getMessageKey();
            this.defaultMessage = messageKeyable.getDefaultMessage();
            this.params = params;
        }

        @Override
        public String getMessage() {
            if (translate) {
                return message;
            }
            this.message = MessageUtils.get(this, params);
            this.translate = true;
            return message;
        }

        @Override
        public Integer getCode() {
            return code;
        }

        @Override
        public String getMessageKey() {
            return messageCode;
        }

    }
}
