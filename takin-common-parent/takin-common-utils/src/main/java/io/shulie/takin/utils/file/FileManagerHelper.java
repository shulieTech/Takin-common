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

package io.shulie.takin.utils.file;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 文件操作工具类
 *
 * @author zhaoyong
 */
@Slf4j
public final class FileManagerHelper {

    private FileManagerHelper() { /* no instance */ }

    private static final int DEFAULT_READ_BUFFER_SIZE = 4096;

    /**
     * 读取文件内容
     * @param file
     * @param encoding
     * @return
     */
    public static String readFileToString(File file,String encoding) throws IOException {
       return FileUtils.readFileToString(file,encoding);
    }

    /**
     * 将对应的文件复制到目标文件目录
     *
     * @param sourcePaths
     * @param targetPath
     * @return
     */
    public static void copyFiles(List<String> sourcePaths, String targetPath) throws IOException {
        File targetFileDir = FileUtil.file(targetPath);
        //目标文件夹不存在时，创建改文件夹
        if (!targetFileDir.exists()) {
            targetFileDir.mkdirs();
        }
        for (String sourcePath : sourcePaths) {
            String targetFilePath;
            if (sourcePath.contains(File.separator)) {
                String substring = sourcePath.substring(sourcePath.lastIndexOf(File.separator));
                targetFilePath = targetPath + substring;
            } else {
                targetFilePath = targetPath + sourcePath;
            }
            //当目标文件不存在时，复制文件
            File targetFile = FileUtil.file(targetFilePath);
            if (!targetFile.exists()) {
                Files.copy(FileUtil.file(sourcePath).toPath(), targetFile.toPath());
            }
        }
    }

    /**
     * 根据路径删除文件
     *
     * @param paths
     * @return
     */
    public static Boolean deleteFiles(List<String> paths) {
        for (String path : paths) {
            if (!deleteFilesByPath(path)) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 循环删除该目录下的所有文件
     *
     * @param path
     * @return
     */
    public static Boolean deleteFilesByPath(String path) {
        //不能删除根目录
        if (StringUtils.isBlank(path) || "/".equals(path)){
            return Boolean.FALSE;
        }
        File file = FileUtil.file(path);
        return deleteFileAndChildren(file);
    }

    public static Boolean deleteFileAndChildren(File file){
        if (!file.exists()) {
            return Boolean.TRUE;
        }
        if (file.isFile()){
            return file.delete();
        }

        boolean flag = true;
        File[] files = file.listFiles();
        if (files != null && files.length > 0){
            // 删除文件夹中的所有文件包括子目录
            for (File childFile : files) {
                flag = deleteFileAndChildren(childFile);
            }
        }
        // 删除目录成功
        if (flag) {
            // 删除当前目录
            if (file.delete()) {
                log.warn("删除目录[{}]成功！", file.getName());
            } else {
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        }
        log.warn("删除目录失败！");
        return Boolean.FALSE;
    }

    public static String readTextFileContent(File file) {
        InputStreamReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            reader = new InputStreamReader(new FileInputStream(file));
            char[] buffer = new char[32];
            int length;
            while ((length = reader.read(buffer)) > 0) {
                stringBuilder.append(buffer, 0, length);
            }
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    log.warn(e.getMessage(), e);
                }
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 将字符串创建为指定文件
     */
    public static Boolean createFileByPathAndString(String filePath,String fileContent){
        String substring = filePath.substring(0, filePath.lastIndexOf("/"));
        File file = FileUtil.file(substring);
        if (!file.exists()){
            file.mkdirs();
        }
        BufferedWriter bf = null;
        try {
            bf = new BufferedWriter(new FileWriter(filePath));
            bf.write(fileContent);
            bf.flush();
            bf.close();
            return Boolean.TRUE;
        } catch (IOException e) {
            log.error("字符串转换为文件操作失败！",e);
            return Boolean.FALSE;
        }finally {
            try {
                if (bf != null){
                    bf.close();
                }
            } catch (IOException e) {
                log.error("字符串转换为文件,文件流关闭失败！",e);
            }
        }
    }


    /**
     * 将原文件打包到目标路径
     *
     * @param sourcePaths 源路径
     * @param targetPath  目标文件路径
     * @param zipFileName zip文件名称
     * @param isCovered   是否覆盖，如果目标文件已存在，为true，删除原文件，重新打包；为false，不打包直接返回
     * @throws IOException
     */
    public static void zipFiles(List<String> sourcePaths, String targetPath, String zipFileName, boolean isCovered)
        throws IOException {
        zipFiles(sourcePaths, targetPath, zipFileName, isCovered,"/tmp");
    }

    /**
     * 将原文件打包到目标路径
     *
     * @param sourcePaths 源路径
     * @param targetPath  目标文件路径
     * @param zipFileName zip文件名称
     * @param isCovered   是否覆盖，如果目标文件已存在，为true，删除原文件，重新打包；为false，不打包直接返回
     * @throws IOException
     */
    public static void zipFiles(List<String> sourcePaths, String targetPath, String zipFileName, boolean isCovered,String tmpFilePath)
        throws IOException {
        File zipFilePath = FileUtil.file(targetPath);
        if (!zipFilePath.exists()) {
            zipFilePath.mkdirs();
        }
        File zipFile = FileUtil.file(targetPath + zipFileName);
        if (zipFile.exists()) {
            //如果文件已存在
            if (!isCovered) {
                return;
            } else {
                deleteFilesByPath(targetPath + zipFileName);
            }
        } else {
            zipFile.createNewFile();
        }
        //FIXME 替换成 File.createTempFile 这种方式
        UUID uuid = UUID.randomUUID();
        String tmpPath = tmpFilePath+ File.separator + uuid;
        File tmpPathFile = FileUtil.file(tmpPath);
        if (!tmpPathFile.exists()) {
            tmpPathFile.mkdirs();
        }
        copyFiles(sourcePaths, tmpPath);
        compress(tmpPath, zipFile);
        deleteFilesByPath(tmpPath);
    }

    private static void compress(String srcPathName, File zipFile) throws IOException {
        File file = FileUtil.file(srcPathName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,
                new CRC32());
            ZipOutputStream out = new ZipOutputStream(cos);
            String basedir = "";
            compress(file, out, basedir);
            out.close();
        } catch (Exception e) {
            log.error("压缩文件失败！srcPathName={} targetPath={} zipFileName={}", srcPathName, zipFile, e);
        }
    }

    private static void compress(File file, ZipOutputStream out, String basedir) {
        /* 判断是目录还是文件 */
        if (file.isDirectory()) {
            System.out.println("压缩：" + basedir + file.getName());
            compressDirectory(file, out, basedir);
        } else {
            System.out.println("压缩：" + basedir + file.getName());
            compressFile(file, out, basedir);
        }
    }

    /**
     * 压缩一个目录
     */
    private static void compressDirectory(File dir, ZipOutputStream out, String basedir) {
        if (!dir.exists()) {
            return;
        }

        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            /* 递归 */
            compress(file, out, basedir + dir.getName() + "/");
        }
    }

    /**
     * 压缩一个文件
     */
    private static void compressFile(File file, ZipOutputStream out, String basedir) {
        if (!file.exists()) {
            return;
        }
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            ZipEntry entry = new ZipEntry(basedir + file.getName());
            out.putNextEntry(entry);
            int count;
            byte[] data = new byte[DEFAULT_READ_BUFFER_SIZE];
            while ((count = bis.read(data, 0, DEFAULT_READ_BUFFER_SIZE)) != -1) {
                out.write(data, 0, count);
            }
            bis.close();
        } catch (Exception e) {
            log.error("压缩单个文件失败! fileName={}", file.getName(), e);
            //用于递归的阻断
            throw new RuntimeException(e);
        }
    }

}
