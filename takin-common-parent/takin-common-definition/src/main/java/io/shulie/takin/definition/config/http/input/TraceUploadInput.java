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

package io.shulie.takin.definition.config.http.input;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author shiyajian
 * create: 2020-12-09
 */
public class TraceUploadInput implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 追踪实例对象
     */
    private String traceDeployObject;

    /**
     * 追踪凭证
     */
    private String sampleId;

    /**
     * 状态0:待采集;1:采集中;2:采集结束
     */
    private Integer status;

    /**
     * 行号
     */
    private Integer lineNum;

    /**
     * 评价耗时
     */
    private BigDecimal avgTime;

    /**
     * 追踪实例下一级的信息
     */
    private List<TraceUploadInput> children;

}
