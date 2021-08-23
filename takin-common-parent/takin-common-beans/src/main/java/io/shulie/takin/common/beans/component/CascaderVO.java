package io.shulie.takin.common.beans.component;

import java.util.List;

import lombok.Data;

/**
 * @author 无涯
 * @Package io.shulie.tro.web.common.vo
 * @date 2021/1/7 5:16 下午
 *
 * 前端联级筛选牧宝
 */
@Data
public class CascaderVO {

    private String label;
    private String value;
    private List<CascaderVO> children;

}
