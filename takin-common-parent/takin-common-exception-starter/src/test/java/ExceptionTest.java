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

import io.shulie.takin.parent.exception.entity.BaseException;
import io.shulie.takin.parent.exception.entity.ExceptionCode;
import org.junit.Test;

public class ExceptionTest {

    @Test
    public void test() {

        ///throw new BaseException(ExceptionCode.HTTP_UTIL_URL_EMPTY,"你异常");
        try {
            Integer i = null;
            if(i / 0 == 0) {
                System.out.println(false);
            }
        } catch (Throwable e) {
           throw new BaseException(ExceptionCode.HTTP_UTIL_URL_EMPTY,"sasas");
        }
    }

}
