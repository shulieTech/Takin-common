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

package utils.linux;

/**
 * @author 无涯
 * @Package utils.linux
 * @date 2020/12/10 5:44 下午
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Test;

public class LinuxHelperTest {
    private  static ThreadFactory nameThreadFactory =
        new ThreadFactoryBuilder().setNameFormat("linux-run-thread-%d").build();
    private  static ThreadPoolExecutor poolExecutor =
        new ThreadPoolExecutor(20, 20, 60L,
            TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), nameThreadFactory,
            new ThreadPoolExecutor.CallerRunsPolicy());
    @Test
    public void test() throws InterruptedException {
       // String path = "sh /Users/hezhongqi/shulie/test.sh";
       // poolExecutor.execute(() -> {
       //     AtomicReference<Process> shellProcess = new AtomicReference<>() ;
       //     int state = LinuxHelper.runShell(
       //         path, 60L,
       //         process -> shellProcess.set(process),
       //         message -> System.out.println(message)
       //     );
       //     System.out.println(state);
       //});
       // Thread.sleep(11*1000);
    }



    @Test
    public void testShell() {

        //String path = "sh /Users/ranghai/Desktop/test.sh";
        //
        //LinuxHelper.runShell(path, 3000L, new Callback() {
        //    @Override
        //    public void before(Process process) {
        //        System.out.println("exec before!");
        //    }
        //
        //    @Override
        //    public void after(Process process) {
        //        System.out.println("exec after!");
        //    }
        //
        //    @Override
        //    public void exception(Process process, Exception e) {
        //        System.out.println("exec exception!");
        //    }
        //}, new Response() {
        //    @Override
        //    public void onLine(String message) {
        //        System.out.println(message);
        //    }
        //});
    }


}
