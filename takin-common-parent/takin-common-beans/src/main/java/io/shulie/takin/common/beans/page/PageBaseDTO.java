/*
 * Copyright 2021 Shulie Technology, Co.Ltd
 * Email: shulie@shulie.io
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
