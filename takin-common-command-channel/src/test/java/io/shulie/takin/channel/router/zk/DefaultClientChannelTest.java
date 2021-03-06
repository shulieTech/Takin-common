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

package io.shulie.takin.channel.router.zk;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultClientChannelTest {

    @Test
    public void getPathIp() {


        DefaultClientChannel channel = new DefaultClientChannel();

        String pathIp = channel.getPathIp("dms-mall-8888");

        Assert.assertEquals("dms",pathIp);

        pathIp = channel.getPathIp("192.168.100.47-27248");


        Assert.assertEquals("192.168.100.47",pathIp);
    }
}