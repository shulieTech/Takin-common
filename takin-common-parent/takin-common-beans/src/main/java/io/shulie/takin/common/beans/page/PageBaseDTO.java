package io.shulie.takin.common.beans.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页基础类
 *
 * @author liuchuan
 * @date 2021/5/12 9:29 上午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PageBaseDTO extends io.shulie.takin.common.beans.page.PagingDevice {

    /**
     * 真正的当前页, 因为分页从0开始
     * 所以要加1
     *
     * @return 当前页
     */
    @ApiModelProperty(hidden = true)
    public int getRealCurrent() {
        return this.getCurrent() + 1;
    }

}
