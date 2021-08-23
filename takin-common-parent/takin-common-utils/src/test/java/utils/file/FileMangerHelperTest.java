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

package utils.file;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;
import io.shulie.takin.utils.file.FileManagerHelper;
import io.shulie.takin.utils.linux.LinuxHelper;
import org.junit.Test;

/**
 * @author 无涯
 * @Package utils.file
 * @date 2020/12/17 2:01 下午
 */
public class FileMangerHelperTest {
    @Test
    public void test() throws IOException {
        String path = "/Users/hezhongqi/shulie/test/report.zip";
        if(new File(path).exists()) {
            System.out.println(true);
        }else {
            // 开始压缩
            //FileUtils.
            StringBuilder sb = new StringBuilder();
            sb.append("zip -r /Users/hezhongqi/shulie/test/report.zip /Users/hezhongqi/shulie/test/report");
            //Process process = Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", sb.toString()});

            System.out.println(LinuxHelper.executeLinuxCmd("zip -r /Users/hezhongqi/shulie/test/report.zip /Users/hezhongqi/shulie/test/report"));
        }
    }
    @Test
    public void testDelete() {
        List<String> paths = Lists.newArrayList();
        paths.add("/Users/hezhongqi/shulie/test/logs");
        FileManagerHelper.deleteFiles(paths);
    }
}
