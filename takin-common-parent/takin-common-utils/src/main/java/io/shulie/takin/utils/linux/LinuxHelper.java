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

package io.shulie.takin.utils.linux;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName LinuxUtil
 * @Description
 * @Author qianshui
 * @Date 2020/4/18 下午4:14
 */
@Slf4j
public class LinuxHelper {

    /**
     *执行curl命令
     * @param cmds
     * @return
     */
    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
            value = {"COMMAND_INJECTION"},
            justification = "忽略命令执行报警，在一些地方目前必须使用到")
    public static String execCurl(String[] cmds) {
        ProcessBuilder process = new ProcessBuilder(cmds);

        try {
            Process p = process.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;

            while((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }

            return builder.toString();
        } catch (IOException e) {
            log.error("execCurl异常，异常信息:",e);
            return null;
        }
    }


    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
            value = {"COMMAND_INJECTION"},
            justification = "忽略命令执行报警，在一些地方目前必须使用到")
    public static Boolean executeLinuxCmd(String cmd) {
        BufferedReader read = null;
        try {
            Process pro = Runtime.getRuntime().exec(cmd);
            pro.waitFor();
            read = new BufferedReader(new InputStreamReader(pro.getInputStream()));
        } catch (Exception e) {
            log.error("executeLinuxCmd 执行命令异常，异常信息:",e);
            return Boolean.FALSE;
        } finally {
            if(read != null) {
                try {
                    read.close();
                } catch (IOException e) {
                    log.error("executeLinuxCmd 流关闭异常，异常信息:",e);
                }
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 执行改变shell脚本权限命令
     *
     * @param shellPath shell脚本路径
     * @return
     */
    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
            value = {"COMMAND_INJECTION"},
            justification = "忽略命令执行报警，在一些地方目前必须使用到")
    public static boolean runChmod(String shellPath) throws Exception {
        // 添加shell的执行权限
        String chmod = "chmod +x " + shellPath;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(chmod);
            int waitFor = process.waitFor();
            if (waitFor != 0) {
                // 执行出现异常
                log.warn("runChmod 改变Shell脚本执行权限发生异常，异常信息:");
                return false;
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
        } catch (IOException e) {
            throw e;
        } catch (InterruptedException e) {
            throw e;
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return true;
    }



    public static int runShell(String commands, Long timeout, final Callback callback, final Response response) {
        return run(timeout,callback,response,commands);
    }

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
            value = {"COMMAND_INJECTION"},
            justification = "忽略命令执行报警，在一些地方目前必须使用到")
    private static int run(Long timeout, final Callback callback, final Response response,String commands) {
        int status = -1;
        Process process = null;
        BufferedReader in = null;
        try {
            process = Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c", commands});
            if (callback != null) {
                callback.before(process);
            }

            in  = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String lineTxt;
            while ((lineTxt = in.readLine()) != null) {
                response.onLine(lineTxt);
            }

            in  = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((lineTxt = in.readLine()) != null) {
                response.onLine(lineTxt);
            }

            if (timeout == null || timeout <= 0) {
                status = process.waitFor();
            } else {
                if (!process.waitFor(timeout, TimeUnit.SECONDS)) {
                    throw new RuntimeException(String.format("Command run timeout, timeout: %s, command: %s", timeout, commands));
                } else {
                    status = process.exitValue();
                }
            }
        } catch (IOException | InterruptedException e) {
            response.onLine(e.getLocalizedMessage());
            e.printStackTrace();
            if (callback != null){
                callback.exception(process,e);
            }
        } finally {
            if (process != null) {
                process.destroy();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    System.out.println("关闭输出流发生异常");
                }
            }
            if (callback != null) {
                callback.after(process);
            }
        }
        return status;
    }
    public interface Response {
        void onLine(String message);
    }

    public interface Callback {
        /**
         * 开始执行
         * @param process 执行对象
         */
        void before(Process process);
        /**
         * 执行后回调
         * @param process 执行对象
         */
        void after(Process process);

        /**
         * 执行异常回调
         * @param process 执行对象
         * @param e
         */
        void exception(Process process, Exception e);
    }
}
