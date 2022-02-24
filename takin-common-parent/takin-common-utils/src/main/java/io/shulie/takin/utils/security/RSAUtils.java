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
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA算法，实现数据的加密解密。
 * 位数	私钥长度(BASE64)	公钥长度(BASE64)	明文长度	密文长度
 * 512	    428	            128	        1~53	88
 * 1024	    812	            216	        1~117	172
 * 2048	    1588	        392	        1~245	344
 * 4096
 */
public class RSAUtils {
    private String rsaPubKey = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAwAzW5QPHM6tU7i2z/u57\n" +
            "HoXKD73jjq1IPU/nfOcFeVHK0ErZ6fwIHlyEa9gFPXcIqouHDcxJpUGG1xiiotbS\n" +
            "OocVwNvJitfMrfxj826c+rrOCZ6mr+MpG4G8R6N389twjQOAcLfbuIgxbvAfHCog\n" +
            "jqpTVXGZ9zTuQUbGg0hfzli+OXW+prDoVW/gzJtsmuwFIQ4n1vBs3k8MyLRqqc6x\n" +
            "ze5mn8SMg5H8lXkkC44YQvAVLHmWv49z2nWKdhfCrWmJUiFURFyuk+7Xvsz1yDML\n" +
            "DTGKPa4sygnXbJjXeiFvPSsST3AYYWWVghfZpn11Mce9AIrecr1TvP8zleIx3/wH\n" +
            "vRssbqqPydqW/KG9TSIqmuuuEWVWV0MWYWLiQsMuQrnwQh9OW49c5j1rBTtrrINJ\n" +
            "3qgeFJIAXP9OV6kcQ32w9c4LojXbWhK5hTQLYWR3VvOL6VdxMA9e47pPhFzvrB7f\n" +
            "ycryT4wwfxkqIVwUhY1o1bbhrtwryrELt34EfxXxeHJsjibt5IE5RU58FxWJIAkA\n" +
            "insEYtb9UXRvJIizfwqB4qjKJY9DbQFbqMQOZiAyfRyjwPmgONCY/Vh4tgby8tFX\n" +
            "C96RmlSeLx/fwuDNraJBNVWvEWpVEjBxmo7LVHaNwgay5/AblkXX5nxpftsZQK3e\n" +
            "VLiJ2N4SNSoby11CoFBuwA0CAwEAAQ==";

    private String rsaPriKey = "MIIJQwIBADANBgkqhkiG9w0BAQEFAASCCS0wggkpAgEAAoICAQDADNblA8czq1Tu\n" +
            "LbP+7nsehcoPveOOrUg9T+d85wV5UcrQStnp/AgeXIRr2AU9dwiqi4cNzEmlQYbX\n" +
            "GKKi1tI6hxXA28mK18yt/GPzbpz6us4Jnqav4ykbgbxHo3fz23CNA4Bwt9u4iDFu\n" +
            "8B8cKiCOqlNVcZn3NO5BRsaDSF/OWL45db6msOhVb+DMm2ya7AUhDifW8GzeTwzI\n" +
            "tGqpzrHN7mafxIyDkfyVeSQLjhhC8BUseZa/j3PadYp2F8KtaYlSIVREXK6T7te+\n" +
            "zPXIMwsNMYo9rizKCddsmNd6IW89KxJPcBhhZZWCF9mmfXUxx70Ait5yvVO8/zOV\n" +
            "4jHf/Ae9Gyxuqo/J2pb8ob1NIiqa664RZVZXQxZhYuJCwy5CufBCH05bj1zmPWsF\n" +
            "O2usg0neqB4UkgBc/05XqRxDfbD1zguiNdtaErmFNAthZHdW84vpV3EwD17juk+E\n" +
            "XO+sHt/JyvJPjDB/GSohXBSFjWjVtuGu3CvKsQu3fgR/FfF4cmyOJu3kgTlFTnwX\n" +
            "FYkgCQCKewRi1v1RdG8kiLN/CoHiqMolj0NtAVuoxA5mIDJ9HKPA+aA40Jj9WHi2\n" +
            "BvLy0VcL3pGaVJ4vH9/C4M2tokE1Va8RalUSMHGajstUdo3CBrLn8BuWRdfmfGl+\n" +
            "2xlArd5UuInY3hI1KhvLXUKgUG7ADQIDAQABAoICAHSXluLkH0xsCJysnGj5iT9n\n" +
            "g9t4sazN4FxwpR279yPHFzTLLG6T9q3QBwid1diR6m33VR94VYGBPYZaS2pGtsyX\n" +
            "dApopHupNXavElMYZS8Gt805RwLjCvLLGW34fPtLzpGdfZ1w0XC/JhuupYRowuk5\n" +
            "2Gu4Evv0Y2y3tg6/ooJYd+vUYtE592LfD//Hn/CRty9xM5iWz9FK9nzmoxbCCEn4\n" +
            "IM9/+Lprp4drSwnE00/YB1K/4lgbk/8detjk5eh3eMmmuEzkY4wl5U7Nia1n556z\n" +
            "47Q8iK6DuQgR5kccAzeEbZJSgEqo7FJVfmpIIhz9oO2yYQgRjsPfvwaQkyzjCiKj\n" +
            "9FVkcKS8P5yh0Icf2n4SLxXRDu1eOYl9U1UcaHubTC/v3RgK+aqNIxrnjDbkwmMP\n" +
            "J+qe4WTU436iQ5cPatrqdk9wsg4OybJ4MVL1DMMIcDOoTC9iO/X8gRmm5RsfMUij\n" +
            "rGOunCVWyteFsOb4e7dXL25vvwrP5kJxSD6OW1iPBXW3p5q/Wz3x06yJ/7qLE/7c\n" +
            "S1YYjAo3PDb20xKtUTTQwI7ad9MgzvpUpeTu+pia0qZv5hy2Oh6yMPQOKZindGYv\n" +
            "nBMVZDTnJTFTdoHmr0KBWk05VCk2qxZxRDlYkHJWzUoNjJ+iXx/Mybd0zp0jEcEY\n" +
            "ZxLPTJVD49pougrTovBVAoIBAQDl6B1Y13U3OHCtqAMC89geoh4ar2c7I3Rq2LYZ\n" +
            "imXfJl8V62LCxoo5zHDT5ZQ3asPY2M64jz16ZZJm3a4oNijFYCkQhAZ4Dvapgfwu\n" +
            "PLijNJSoMw5gz0QXpo0g4HU4slRPrN6i66EKBDTffOZsQumwtVMGwangs6wWVUC4\n" +
            "H68Aa3P+3C1KlxcmgOz5e77GUWJ1oJxsCghDiRSa/ybEvUHlCSUZmL5y/vzR/uEM\n" +
            "E72pWUBz3CHf4QCo+MlTyf69njge68kAxIspBvuK/ca1/dmKln9VEolKoO68VrBZ\n" +
            "f4VyTvGyeZ0ymSzLe0WBAUHrjkhJ7ZCL6VM1ocSbm2zveQu3AoIBAQDV2M/P1NOr\n" +
            "peJWTRgjXCuUHYW3FyKzK/RRVUjLtKfyditOVFw6NbGxrMX+2BItdHbksL7q8w1S\n" +
            "Pmg/IqF8+Ubtzik04G3vJlF0eZXdhYgxCXTQZJ3wClEt732e4oP5Jkx0+nXXANT0\n" +
            "gLCcyV33nEz4Q4zckP50i7w3DnCH0q6Kk55SbINuQ6PGKDO0hfRa+6JmG7pmOgng\n" +
            "R/ID8OhE0Qnwe4VOxGpCPnY/YtpIK32I+rI65/7L+UUC1qnO+j+QQBcsVw0pxk9g\n" +
            "jf8ipd8AR3FvK8D/ZNAh7bIHZHfDdBKEbEuRCSOD/oD+M+YqmhvyZy9jbt48onKt\n" +
            "hNPdOJXygRpbAoIBAC0IYz80rNQNLR0GzNg2NT4C2Uy20MynrDUFj92+2ydzl6pu\n" +
            "eeDXDNo3bl5DGN4l/oiqJwvFmvAvlxapE2Ty/tdyd3ixs6aun6giOug/opbtRLUR\n" +
            "kx5pWtgbGoe3D570Z0lX/iABcYDJxCWGjIfmL2oailHHe2P1LZ8OQegO5LN7WLQy\n" +
            "Inj60WEMXd6cW4jzBgsuU5Vh/a5k2nFlyw08WPzo5qh139AcnB7yAtjfwQW74sEO\n" +
            "/F+i7gMrABx2c3xm0m1UGAIbfXqi0ICwJs0dFRBtXDGouNWyYjDn9hEBxnzWc8dW\n" +
            "xPDVSg3tbnbPbT8blpNMgkS1hkP6FM26CwN5PU8CggEBALWV75DJZhwhNjqWKGMO\n" +
            "Ryr0jrDUAnEmZ8YQp0ni06H9WfSfidkb8xGuZkpqa7URakvzEYssbClkON0zqPEY\n" +
            "M3SHVVJDKNakMRK4k9uRIf6RyxGR/tLvz9Mzw/YA3pPYQ6ApdZrmZ+5ewJnCG/rA\n" +
            "TFOb+KbqsEeCyIXvyvpqmi+t/gh7gHdHFHCVfqJSBRalCleuP+/CZNSAAy/5hyG4\n" +
            "Z7o/CQnDm384zuwwIq6Zz3Vbe7CtgxztQ/6PDY5lzUfORF8wZEsOzCRvLzDarKvt\n" +
            "/pKqEq5LirjkwAGq/E+J5pdv5hL4MaJJoHs60r1mM0j4+bJzWF8gikRxv/3fqORN\n" +
            "YHUCggEBAKaaUZgWq+GrCwr0LsnQpRuoc1oFhB/bpSVF6VJYcDfkS75UGhTihp+o\n" +
            "adB6qzdZ+p6GPGZJreEJyl1rxfm9qH9w3cR0fMt4cXqmPDYl3NQrTuKQi6//FrxA\n" +
            "C62+uL0Nk6iGnDQXbchK87muVlDAsvfTv9/Nq7OTcI7nfggtzSbMjKHuwcqpv9w9\n" +
            "zI0pTUgKAKf8qmK9CbuYBY7YpTT/sZFsdm3Nt3uBmylS+LUmNEej4I3btN2faXKa\n" +
            "i5n6pPIbiDb9jsyjZTMFAGEdtjvqPRSWLkdh7UTrscQ9fhcghsTodnaQ0JE3wvfc\n" +
            "6kzmkUooj6amBcyUbED1yT4ugSAYqas=";

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
        try {
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] b = plainText.getBytes("utf-8");
            byte[] b1 = null;
            /** 执行加密操作 */
            for (int i = 0; i < b.length; i += 501) {
                byte[] doFinal  = cipher.doFinal(ArrayUtils.subarray(b, i,i + 501));
                b1 = ArrayUtils.addAll(b1, doFinal);
            }
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(b1);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用私钥对明文密文进行解密
     * @param privateKey
     * @param enStr
     * @return
     */
    private String decrypt(PrivateKey privateKey,String enStr){
        try {
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b1 = decoder.decodeBuffer(enStr);
            /** 执行解密操作 */
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < b1.length; i += 512) {
                byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(b1, i, i + 512));
                sb.append(new String(doFinal,"utf-8"));
            }
            return sb.toString();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
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
