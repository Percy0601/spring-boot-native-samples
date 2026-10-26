package io.samples.thrift.api.fn;

/**
 * 自定义业务异常：当 FreeMarker 自定义函数执行失败时抛出。
 *
 * @author baoxin.zhao
 */
public class FreemarkerFnException extends RuntimeException {

    public FreemarkerFnException(String message) {
        super(message);
    }

    public FreemarkerFnException(String message, Throwable cause) {
        super(message, cause);
    }
}
