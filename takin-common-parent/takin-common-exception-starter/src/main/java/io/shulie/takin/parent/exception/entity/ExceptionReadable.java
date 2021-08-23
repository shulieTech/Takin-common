package io.shulie.takin.parent.exception.entity;

/**
* @Package io.shulie.takin.exception.entity
* @author 无涯
* @description:可读性的异常接口
* @date 2021/7/1 17:01
*/
public interface ExceptionReadable {

    /**
     * 异常在资源文件中的key
     */
    String getErrorCode();

    /**
     * 如果资源文件中没有找到key，那么对应的默认值
     */
    String getDefaultValue();
}
