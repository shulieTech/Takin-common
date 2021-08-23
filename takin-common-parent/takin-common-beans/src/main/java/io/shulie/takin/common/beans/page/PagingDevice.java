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

import java.io.Serializable;

/**
 * @author hezhongqi
 */
public class PagingDevice  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 每页条数
     */
    private int pageSize = 20;

    /**
     * 当前页码
     */
    private int current = 0;

    public int getOffset() {
        return Math.max(getCurrentPage() * pageSize, 0);
    }

    public int getCurrentPage() {
        return current;
    }

    public void setCurrentPage(Integer currentPage) {
        currentPage = currentPage == null ? 0 : currentPage;
        this.current = currentPage < 0 ? 0 : currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = (pageSize == null) ? getPageSize() : pageSize;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }
}
