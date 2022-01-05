package io.shulie.takin.utils.log;


import org.springframework.web.util.JavaScriptUtils;

public class LogHelper {

    /**
     * 转义特殊字符
     *
     * @param content 内容
     * @return 转义之后的内容
     */
    public static String converterString(String content) {
        return JavaScriptUtils.javaScriptEscape(content);
    }

    /**
     * 转义特殊字符
     *
     * @param format 规则
     * @param args   规则对应参数
     * @return 转义之后的内容
     */
    public static String format(String format, Object... args) {
        String log = String.format(format, args);
        return JavaScriptUtils.javaScriptEscape(log);
    }


}
