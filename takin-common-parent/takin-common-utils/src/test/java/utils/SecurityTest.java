package utils;

import io.shulie.takin.utils.security.AESUtil;
import io.shulie.takin.utils.security.MD5Utils;
import io.shulie.takin.utils.security.RSAUtils;
import java.io.File;

/**
 * @author pnz.zhao
 * @Description:
 * @date 2022/2/23 11:04
 */
public class SecurityTest {

    public static void main(String[] args) throws Exception {
        String str = "hello...";
        String str2 = TextStr.str;
        long startTime = System.currentTimeMillis();
        String md1 = MD5Utils.getInstance().getMD5(str);
        long endTime = System.currentTimeMillis();
        System.out.println(md1);
        System.out.println("耗时:"+(endTime-startTime));

        String file = "/Users/taotao/IdeaProjects/Takin-web/logs/takin-web/service.log";    //2.3G-7-8s
        startTime = System.currentTimeMillis();
        String md2 = MD5Utils.getInstance().getMD5(new File(file));
        System.out.println(md2);
        endTime = System.currentTimeMillis();
        System.out.println("耗时:"+(endTime-startTime));

        file = "/Users/taotao/Downloads/ck1102.csv";
        startTime = System.currentTimeMillis();
        String md3 = MD5Utils.getInstance().getMD5(new File(file));
        System.out.println(md3);
        endTime = System.currentTimeMillis();
        System.out.println("耗时:"+(endTime-startTime));

        System.out.println("====================RSA1");

        startTime = System.currentTimeMillis();
        String miwen1 = RSAUtils.getInstance().encrypt(str);
        System.out.println("密文1:"+miwen1);
        String minwen1 = RSAUtils.getInstance().decrypt(miwen1);
        System.out.println("明文1:"+minwen1);
        endTime = System.currentTimeMillis();
        System.out.println("耗时1:"+(endTime-startTime));

        System.out.println("====================RSA2");

        startTime = System.currentTimeMillis();
        String miwen12 = RSAUtils.getInstance().encrypt(str);
        System.out.println("密文1:"+miwen12);
        String minwen12 = RSAUtils.getInstance().decrypt(miwen1);
        System.out.println("明文1:"+minwen12);
        endTime = System.currentTimeMillis();
        System.out.println("耗时1:"+(endTime-startTime));

        System.out.println("====================AES");

        startTime = System.currentTimeMillis();
        String miwen2 = AESUtil.getInstance().encrypt(str2);
        System.out.println("密文2:"+miwen2);
        String minwen2 = AESUtil.getInstance().decrypt(miwen2);
        System.out.println("明文2:"+minwen2);
        endTime = System.currentTimeMillis();
        System.out.println("耗时2:"+(endTime-startTime));
    }
}
