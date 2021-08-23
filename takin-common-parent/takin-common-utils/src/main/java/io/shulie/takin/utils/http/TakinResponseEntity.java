package io.shulie.takin.utils.http;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.type.TypeReference;
import io.shulie.takin.utils.json.JsonHelper;
import lombok.Data;
import okhttp3.Response;

/**
 * @author 无涯
 * @Package io.shulie.takin.utils.http
 * @date 2020/11/4 10:21 上午
 */
@Data
public class TakinResponseEntity<T> {

    private static final Integer OK_CODE = 200;
    private static final Integer BAD_CODE = 400;

    private Boolean success;
    private Integer httpStatus;
    private T body;
    private String errorMsg;

    public TakinResponseEntity() {
        this.success = true;
        this.httpStatus = OK_CODE;
        this.body = null;
        this.errorMsg = "";
    }

    public static <T> TakinResponseEntity<T> create(Response response, Class<T> clazz, TypeReference<T> typeRef) {
        TakinResponseEntity<T> takinResponseEntity = new TakinResponseEntity<>();

        String responseStr = null;
        String errorMsg = null;
        try {
            if (response.body() != null) {
                responseStr = new String(response.body().string().getBytes(
                    StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            errorMsg = e.getMessage();
        }
        if (!response.isSuccessful()) {
            takinResponseEntity.setHttpStatus(response.code());
            takinResponseEntity.setSuccess(false);
            takinResponseEntity.setBody(null);
            takinResponseEntity.setErrorMsg(responseStr != null ? responseStr : errorMsg);
            return takinResponseEntity;
        }
        if (typeRef != null && responseStr != null) {
            takinResponseEntity.setBody(JsonHelper.string2Obj(responseStr, typeRef));
            return takinResponseEntity;
        }
        if (clazz != null && responseStr != null) {
            takinResponseEntity.setBody(JsonHelper.string2Obj(responseStr, clazz));
            return takinResponseEntity;
        }
        throw new IllegalArgumentException("clazz，typeRef 必须有一个有值");
    }

    public static <T> TakinResponseEntity<T> error(String s) {
        TakinResponseEntity<T> takinResponseEntity = new TakinResponseEntity<>();
        takinResponseEntity.setHttpStatus(BAD_CODE);
        takinResponseEntity.setErrorMsg(s);
        return takinResponseEntity;
    }

}
