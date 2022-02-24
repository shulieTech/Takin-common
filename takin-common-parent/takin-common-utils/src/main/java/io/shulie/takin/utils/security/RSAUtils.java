package io.shulie.takin.utils.security;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * RSA算法，实现数据的加密解密。
 * 位数	私钥长度(BASE64)	公钥长度(BASE64)	明文长度	密文长度
 * 512	    428	            128	        1~53	88
 * 1024	    812	            216	        1~117	172
 * 2048	    1588	        392	        1~245	344
 */
public class RSAUtils {
    private String rsaPubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC5z1MpuRqzYPbnvV6XLNSpQPY4HpqjJ95ryPnf\n" +
            "FyHqs0nBEeCtQERX75VMoafEYE0l285lqDYoO0KW1Jgo0UnX9alectC4pOwgOugJJY22lbXXOUv8\n" +
            "eGKKY+brc+Nfv9zlnZogkWFJdzV9GEWqaYxyMBPa/CCg82nE06FoL8nfVQIDAQAB";

    private String rsaPriKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALnPUym5GrNg9ue9Xpcs1KlA9jge\n" +
            "mqMn3mvI+d8XIeqzScER4K1ARFfvlUyhp8RgTSXbzmWoNig7QpbUmCjRSdf1qV5y0Lik7CA66Akl\n" +
            "jbaVtdc5S/x4Yopj5utz41+/3OWdmiCRYUl3NX0YRappjHIwE9r8IKDzacTToWgvyd9VAgMBAAEC\n" +
            "gYBsZGR4J3JLCBqgr1i5yb45ChtYO4or+XsH15LkMGNpvbMdvOcbSi1UOwAWVws+RItctXAe31TQ\n" +
            "fVloEZa94PE42NVH27EbQhhstujDdiLLwcdnPNw4FK3cWwNZReXIvgNBq/pHfteDPHMifqgiQpbF\n" +
            "Oy5f5md/F3l3bszxpnSPFQJBAPYcJPZpd8LD1GJ9a8BWM795YOeFpBE7iAtfiD3Zhdw9lK2f58Aa\n" +
            "v7OQReJkcJE6Cykx6zKeascs1SklnQcW0CcCQQDBRtfxigG5X5SydaXHlNRDxUAxWxEhQ+OtqKii\n" +
            "jkGZq5J7/L5DlBsM8gwpkySSv7Kv1d6jyxiCVU3WsF5e8YYjAkEA2/wEPg2/8uPAb9jT+7QRxVDl\n" +
            "gY0/PfgMfr4Btec9c8PHpfqM2HYQbJzFGrvUsrtDRqtMJI7duvMkBgeM+M03eQJANP7p408GHgA5\n" +
            "U52ysUD72wFRErd3wPlOiqV+Z+Q91JcY+WpZ+Um5CRmtgBDarGpn4pLwE1VzCPm++CkwFzR+pQJB\n" +
            "AJE/Y4GLOI9UaNgU8Y9/TQtZahtFljxFWFr6kBtAvB+jE99ffng8Z2912gAetjfWM59lmd534ZC8\n" +
            "EnttsnUQqak=";

    private Cipher cipher;

    private static RSAUtils instance = null;
    public static RSAUtils getInstance() {
        if (instance == null) {
            synchronized (RSAUtils.class) {
                if(instance==null) {
                    instance = new RSAUtils();
                    try {
                        instance.cipher = Cipher.getInstance("RSA");
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    /**
     * 改key
     * @param pubKey,priKey
     */
    public void changeRsaKey(String pubKey,String priKey) {
        if(StringUtils.isNotBlank(pubKey)&&StringUtils.isNotBlank(priKey)){
            this.rsaPubKey = pubKey;
            this.rsaPriKey = priKey;
        }
    }

    public String encrypt(String plainText){
        try {
            return encrypt(getPublicKey(rsaPubKey),plainText);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public String decrypt(String enStr){
        try {
            return decrypt(getPrivateKey(rsaPriKey),enStr);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 使用公钥对明文进行加密，返回BASE64编码的字符串
     * @param publicKey
     * @param plainText
     * @return
     */
    private String encrypt(PublicKey publicKey,String plainText){
        StringBuffer sf = new StringBuffer();
        try {
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] enBytes = null;
            for (int i = 0; i < plainText.length(); i ++) {
                byte[] data = plainText.getBytes(StandardCharsets.UTF_8);

                // 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
                byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i,i + 64));
                enBytes = ArrayUtils.addAll(enBytes, doFinal);
                if(sf.length()==0){
                    sf.append((new BASE64Encoder()).encode(enBytes));
                }else{
                    sf.append("!@#"+(new BASE64Encoder()).encode(enBytes));
                }
            }
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }finally {
            return sf.toString();
        }
    }

    /**
     * 使用私钥对明文密文进行解密
     * @param privateKey
     * @param enStr
     * @return
     */
    private String decrypt(PrivateKey privateKey,String enStr){
        StringBuffer sf = new StringBuffer();
        try {
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            String[] info = enStr.split("!@#");
            for(String temp:info){
                byte[] deBytes = cipher.doFinal((new BASE64Decoder()).decodeBuffer(temp));
                sf.append(new String(deBytes));
            }
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return sf.toString();
        }
    }

    /**
     * 得到私钥
     */
    private PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 得到公钥
     */
    private PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    public static void main(String[] args) {
        String str =
                "1234567890123456789012345678901234567890123456789012345678901234567890123456" +
                "abcdefabcdefabcdefabcdefabcdefabcdefabcdefabcdefabcdefabcdefabcdefabcdefabcd" +
                "KklmIeXypTccy4wXuMc=";
        String miwen1 = RSAUtils.getInstance().encrypt(str);
        System.out.println("密文1:"+miwen1);
        String minwen1 = RSAUtils.getInstance().decrypt(miwen1);
        System.out.println("明文1:"+minwen1);
    }
}
