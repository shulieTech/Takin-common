package io.shulie.takin.definition.config.http;

import io.shulie.takin.definition.annotaion.ParamTypes;
import io.shulie.takin.definition.annotaion.ReturnType;
import io.shulie.takin.definition.config.http.output.PressureSwitchStatusOutput;
import io.shulie.takin.definition.config.http.input.TraceUploadInput;
import io.shulie.takin.definition.config.http.output.WhiteListStatusOutput;
import io.shulie.takin.definition.config.http.input.ApplicationInsertInput;

import static io.shulie.takin.definition.config.http.takinHttpConfigConstants.AGENT_VERSION_URL;
import static io.shulie.takin.definition.config.http.takinHttpConfigConstants.APP_INSERT_URL;
import static io.shulie.takin.definition.config.http.takinHttpConfigConstants.APP_PRESSURE_SWITCH_STATUS_URL;
import static io.shulie.takin.definition.config.http.takinHttpConfigConstants.APP_WHITE_LIST_SWITCH_STATUS_URL;
import static io.shulie.takin.definition.config.http.takinHttpConfigConstants.GUARD_URL;
import static io.shulie.takin.definition.config.http.takinHttpConfigConstants.MIDDLE_STAUTS_URL;
import static io.shulie.takin.definition.config.http.takinHttpConfigConstants.PERFORMANCE_BASE_URL;
import static io.shulie.takin.definition.config.http.takinHttpConfigConstants.PERFORMANCE_TRACE_URL;
import static io.shulie.takin.definition.config.http.takinHttpConfigConstants.PREFIX_URL;
import static io.shulie.takin.definition.config.http.takinHttpConfigConstants.REGISTER_URL;
import static io.shulie.takin.definition.config.http.takinHttpConfigConstants.ROCKETMQ_ISO_URL;
import static io.shulie.takin.definition.config.http.takinHttpConfigConstants.SHADOW_DB_TABLE_URL;
import static io.shulie.takin.definition.config.http.takinHttpConfigConstants.takin_REPORT_ERROR_SHADOW_JOB_URL;
import static io.shulie.takin.definition.config.http.takinHttpConfigConstants.takin_SHADOW_JOB_URL;
import static io.shulie.takin.definition.config.http.takinHttpConfigConstants.UPLOAD_ACCESS_STATUS_URL;
import static io.shulie.takin.definition.config.http.takinHttpConfigConstants.UPLOAD_APP_INFO_URL;
import static io.shulie.takin.definition.config.http.takinHttpConfigConstants.UPLOAD_URL;
import static io.shulie.takin.definition.config.http.takinHttpConfigConstants.WHITELIST_FILE_URL;
import static io.shulie.takin.definition.config.http.takinHttpConfigConstants.WHITELIST_URL;

/**
 * @author shiyajian
 * create: 2020-12-09
 */
public enum takinHttpPullConfigUrlEnums {

    /**
     * http 拉取白名单
     */
    ALLOW_LIST(PullMethod.GET, PREFIX_URL + WHITELIST_URL),

    ALLOW_FILE_LIST(PullMethod.POST, PREFIX_URL + WHITELIST_FILE_URL),

    SHADOW_DB_TABLE(PullMethod.GET, PREFIX_URL + SHADOW_DB_TABLE_URL),

    takin_SHADOW_JOB(PullMethod.GET, PREFIX_URL + takin_SHADOW_JOB_URL),

    takin_REPORT_ERROR_SHADOW_JOB(PullMethod.GET, PREFIX_URL + takin_REPORT_ERROR_SHADOW_JOB_URL),

    UPLOAD_ACCESS_STATUS(PullMethod.POST, PREFIX_URL + UPLOAD_ACCESS_STATUS_URL),

    UPLOAD_APP_INFO(PullMethod.POST, PREFIX_URL + UPLOAD_APP_INFO_URL),

    UPLOAD(PullMethod.POST, PREFIX_URL + UPLOAD_URL),

    @ParamTypes(ApplicationInsertInput.class)
    @ReturnType(String.class)// 成功为空，失败返回错误信息
    APP_INSERT(PullMethod.GET, PREFIX_URL + APP_INSERT_URL),

    AGENT_VERSION(PullMethod.GET, PREFIX_URL + AGENT_VERSION_URL),

    @ParamTypes()
    @ReturnType(PressureSwitchStatusOutput.class)
    APP_PRESSURE_SWITCH_STATUS(PullMethod.GET, PREFIX_URL + APP_PRESSURE_SWITCH_STATUS_URL),

    @ParamTypes()
    @ReturnType(WhiteListStatusOutput.class)
    APP_WHITE_LIST_SWITCH_STATUS(PullMethod.GET, PREFIX_URL + APP_WHITE_LIST_SWITCH_STATUS_URL),

    GUARD(PullMethod.GET, PREFIX_URL + GUARD_URL),

    @ParamTypes()// Map<String, List<String>> TODO 不知道后面这个String是啥
    @ReturnType(String.class)// 成功为空，失败返回错误信息
    REGISTER(PullMethod.POST, PREFIX_URL + REGISTER_URL),

    @ParamTypes(String.class)// json，Map<String, JarVersionVo>
    @ReturnType(String.class)// 成功为空，失败返回错误信息
    MIDDLE_STATUS(PullMethod.POST, PREFIX_URL + MIDDLE_STAUTS_URL),

    ROCKETMQ_ISO(PullMethod.GET, PREFIX_URL + ROCKETMQ_ISO_URL),

    PERFORMANCE_BASE(PullMethod.GET, PREFIX_URL + PERFORMANCE_BASE_URL),

    @ParamTypes(TraceUploadInput.class)
    @ReturnType(Void.class)
    PERFORMANCE_TRACE(PullMethod.POST, PREFIX_URL + PERFORMANCE_TRACE_URL);

    private PullMethod method;

    private String url;

    takinHttpPullConfigUrlEnums(PullMethod method, String url) {
        this.method = method;
        this.url = url;
    }

    public PullMethod getMethod() {
        return method;
    }

    public void setMethod(PullMethod method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public enum PullMethod {

        GET,
        POST;
    }
}
