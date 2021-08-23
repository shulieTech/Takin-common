package io.shulie.takin.common.beans.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 无涯
 * @Package io.shulie.tro.web.common.vo.component
 * @date 2021/1/26 8:51 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectVO {
    private String label;
    private String value;
    private Boolean disable = true;

    public SelectVO(String label,String value) {
        this.label = label;
        this.value = value;
    }
}
