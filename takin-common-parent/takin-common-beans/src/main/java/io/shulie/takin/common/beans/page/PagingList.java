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

import java.util.ArrayList;
import java.util.List;

/**
 * @author shiyajian
 * create: 2020-09-24
 */
public class PagingList<T> {

    private PagingList() { /* no instance */ }

    /**
     * 返回数据的总条数
     */
    private long total;

    /**
     * 返回的数据
     */
    private List<T> list;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    private PagingList(long total, List<T> list) {
        this.total = total;
        this.list = list;
    }

    public boolean isEmpty() {
        return this.list == null || this.list.isEmpty();
    }

    public static <T> PagingList<T> of(List<T> data, long total) {
        return new PagingList<>(total, data);
    }

    public static <T> PagingList<T> empty() {
        return new PagingList<>(0, new ArrayList<>());
    }

}
