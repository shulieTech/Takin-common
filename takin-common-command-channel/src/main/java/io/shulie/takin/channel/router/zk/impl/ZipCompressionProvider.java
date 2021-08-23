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

package io.shulie.takin.channel.router.zk.impl;

import com.google.common.io.ByteStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 封装了 Netflix Curator 的实现
 * @author pamirs
 */
public class ZipCompressionProvider {

    private static final Logger logger = LoggerFactory.getLogger(ZipCompressionProvider.class);

    public static byte[] compress(String path, byte[] data) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ZipOutputStream zipOut = new ZipOutputStream(new BufferedOutputStream(out));
        zipOut.putNextEntry(new ZipEntry("content"));
        zipOut.write(data);
        zipOut.closeEntry();
        zipOut.close();
        byte[] result = out.toByteArray();

        return result;
    }

    public static byte[] decompress(String path, byte[] compressedData) throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream(compressedData);
        ZipInputStream zipIn = new ZipInputStream(in);
        zipIn.getNextEntry();
        byte[] result = ByteStreams.toByteArray(zipIn);
        zipIn.closeEntry();
        zipIn.close();

        return result;
    }
}
