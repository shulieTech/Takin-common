package io.shulie.takin.parent.exception.utils;

import com.alibaba.fastjson.JSON;

import io.shulie.takin.parent.exception.common.ExceptionConstant;
import io.shulie.takin.parent.exception.entity.ExceptionReadable;
import org.apache.commons.lang3.StringUtils;

/**
* @Package io.shulie.takin.exception.utils
* @author 无涯
* @description:
* @date 2021/7/1 16:34
*/
public class ExceptionUtils {


    public static String getCallStackMessage(ExceptionReadable ex,Object source) {
        String message = "";
        if(ex != null) {
            if(source != null) {
                if(source instanceof String) {
                    message = (String)source;
                } else {
                    message = JSON.toJSONString(source);
                }
            }else if(StringUtils.isNotBlank(ex.getDefaultValue())) {
                message = ex.getDefaultValue();
            }else {
                message = "请联系管理员！";
            }
        }
        return message;
    }

    /**
     * 获取异常信息
     * @param ex
     * @param source
     * @return
     */
    public static String getMessage(ExceptionReadable ex,Object source,Throwable e) {
        if(ex != null) {
            String message = "";
            if(source != null) {
                if(source instanceof String) {
                    message = (String)source;
                }else if(source instanceof Throwable ) {
                    if(source instanceof NullPointerException) {
                        message = "NullPointerException";
                    }else {
                        Throwable throwable = (Throwable) source;
                        message = throwable.getMessage();
                    }
                }else {
                    message = JSON.toJSONString(source);
                }
            }else if(StringUtils.isNotBlank(ex.getDefaultValue())){
                message = ex.getDefaultValue();
            }else if(e != null) {
                message = e.getMessage();
            }else {
                message = "请联系管理员！";
            }
            return String.format(ExceptionConstant.ERROR_MESSAGE,ex.getErrorCode(),message);
        }
        // 如果是非自定义异常
       return null;
    }

    /**
     * 通用异常
     * @param e
     * @return
     */
    public static String getPublicMessage(Throwable e) {
        if(e instanceof  NullPointerException) {
            return "NullPointerException";
        }
        if (e.getLocalizedMessage() != null) {
            return e.getLocalizedMessage();
        }
        if (e.getMessage() != null) {
            return e.getMessage();
        }
        return "请联系管理员";
    }
}
