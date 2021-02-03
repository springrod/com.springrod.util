package com.springrod.util;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec; 
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
/**#通用的AES机密工具类
 * 
 */
public class AesUtil {
 private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";
    //加密
    //@key 密钥(16位)
    public static String encode(String str, String key) {
        if (str == null || key == null)
            return null; 
        Cipher cipher;
        try {
             cipher = Cipher.getInstance(ALGORITHMSTR);  
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.substring(0, 16).getBytes("utf-8"), "AES"));
        byte[] bytes = cipher.doFinal(str.getBytes("utf-8"));
       
        return  Base64.getEncoder().encodeToString(bytes);
    }   catch ( Exception e) { 
        e.printStackTrace();
    }
    return "";
    }

    public static String decode(String str, String key) throws Exception {
        if (str == null || key == null) return null; 
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.substring(0, 16).getBytes("utf-8"), "AES"));
        byte[] bytes = Base64.getDecoder().decode(str);
        bytes = cipher.doFinal(bytes);
        return new String(bytes, "utf-8");
    }
} 