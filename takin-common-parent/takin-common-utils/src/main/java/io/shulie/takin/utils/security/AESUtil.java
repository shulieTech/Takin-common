package io.shulie.takin.utils.security;

import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author fanxx
 * @date 2020/12/30 11:49 上午
 */
public class AESUtil {
    /**
     * 编码方式
     */
    public final String CODE_TYPE = "UTF-8";
    /**
     * 填充类型
     */
    public final String AES_TYPE = "AES/ECB/PKCS5Padding";
    /**
     * 私钥（可自定义）
     */
    @SuppressWarnings("SpellCheckingInspection")
    private String AES_KEY = ")O[NB]6,YF}+efcaj{+oESb9d8>Z'e9M";

    private AESUtil() {
    }

    /**
     * 改key
     * @param aesKey
     */
    public void changeAesKey(String aesKey) {
        if(StringUtils.isNotBlank(aesKey)
                &&(aesKey.length()==16||aesKey.length()==32)
        ){
            this.AES_KEY = aesKey;
        }
    }

    private static AESUtil instance = null;
    public static AESUtil getInstance() {
        if (instance == null) {
            synchronized (AESUtil.class) {
                if(instance==null) {
                    instance = new AESUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 加密
     * @param cleartext -
     * @return -
     */
    public String encrypt(String cleartext) {
        try {
            SecretKeySpec key = new SecretKeySpec(AES_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance(AES_TYPE);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedData = cipher.doFinal(cleartext.getBytes(CODE_TYPE));
            return new BASE64Encoder().encode(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 解密
     * @param encrypted -
     * @return -
     */
    public String decrypt(String encrypted) {
        try {
            byte[] byteMi = new BASE64Decoder().decodeBuffer(encrypted);
            SecretKeySpec key = new SecretKeySpec(AES_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance(AES_TYPE);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedData = cipher.doFinal(byteMi);
            return new String(decryptedData, CODE_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 测试
     * @param args -
     */
    public static void main(String[] args) {
        String content = "123456";
        AESUtil.getInstance().changeAesKey("1234567890123456");

        System.out.println("密码明文：" + content);
        // 加密
        String encryptResult = AESUtil.getInstance().encrypt(content);
        System.out.println("加密后：" + encryptResult);
        // 解密
        String decryptResult = AESUtil.getInstance().decrypt(encryptResult);
        System.out.println("解密完成后：" + decryptResult);
    }
}
