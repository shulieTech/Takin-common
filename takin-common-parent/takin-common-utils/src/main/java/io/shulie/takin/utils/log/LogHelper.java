package io.shulie.takin.utils.log;

import com.sun.tools.javac.util.Log;
import org.springframework.web.util.JavaScriptUtils;

public class LogHelper {

    /**
     * 转移特殊字符
     * @param content
     * @return
     */
    public static String converterString(String content) {
        return JavaScriptUtils.javaScriptEscape(content);
    }

    /**
     *
     * @param format
     * @param args
     * @return
     */
    public static String format(String format, Object... args){
        String log = Log.format(format, args);
        return JavaScriptUtils.javaScriptEscape(log);
    }
}
