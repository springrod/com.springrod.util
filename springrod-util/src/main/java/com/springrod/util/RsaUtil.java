package com.springrod.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Properties;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.util.Base64; 

public class RsaUtil {

    public static void main(String[] args) {

        RsaUtil mRSAUniUtil = new RsaUtil();
        // mRSAUniUtil.initKeys();
        // mRSAUniUtil.saveKeyToFile("D:/tt/test.keys");
        String sString = "YHmWSrOloYLZHD6h3dUFWJoe/LYonsHqWircvhPKqAXp2GVOCnDtFeEZkvG1GzAxUbARltUdCMra0zO4GIBVPyrJq30bTkvll88B3cBwXb3Vm5n5kV+s8LjRDkvE2etbc0IypimHAtSNVmyU7cpDBO+KxMiQrtROFBhxKxTF368=";
        // mRSAUniUtil.encode("席日飞");
        // System.out.println("SString:"+sString);
        RSAPublicKey pubk = mRSAUniUtil.getPublicKeyFromFile("D:/tt/test.keys");
        System.out.println("DString:" + mRSAUniUtil.decode(sString, pubk));

    }

    /** 算法名称 */
    private static final String ALGORITHM = "RSA";
    /** 默认密钥大小 */
    private static final int KEY_SIZE = 1024; 
    /** 密钥对生成器 */
    private static KeyPairGenerator keyPairGenerator = null;

    private static KeyFactory keyFactory = null;
    /** 缓存的密钥对 */
    private static KeyPair keyPair = null;
    /** 初始化密钥工厂 */
    static {
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            keyFactory = KeyFactory.getInstance(ALGORITHM);
        } catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private String publicKey;
    private String privateKey;

    public boolean initKeys() {
        try {
            keyPairGenerator.initialize(KEY_SIZE,
                    new SecureRandom(UUID.randomUUID().toString().replaceAll("-", "").getBytes()));
            keyPair = keyPairGenerator.generateKeyPair();
            final RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            final RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
            String publicKeyString = Base64.getEncoder().encodeToString(rsaPublicKey.getEncoded());
            String privateKeyString = Base64.getEncoder().encodeToString(rsaPrivateKey.getEncoded());
            setPublicKey(publicKeyString);
            setPrivateKey(privateKeyString);

            return true;
        } catch (final InvalidParameterException e) {
            System.out.println("KeyPairGenerator does not support a key length of " + KEY_SIZE + "."+e);
            return false;
        } catch (final NullPointerException e) {
            System.out.println("KSRSAUtils#key_pair_gen is null,can not generate KeyPairGenerator instance."+e);
            return false;
        }

    }

    /** #持久化私钥 */
    public boolean saveKeyToFile(final String filePath) {
        /**
         * 数据位置 final Properties config =
         * AppUtil.getAppProperties("keystore/config.properties"); String path =
         * config.getProperty("env.data_path") + "/" + app_package + "/private.key";
         */
        // 存放密钥的绝对地址
        File file = new File(filePath);
        File filePar = file.getParentFile();
        try {
            if (!filePar.exists()) {
                filePar.mkdir();
            }
            if (file.exists()) {
                return false;
            } else {
                file.createNewFile();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        try {
            Properties properties = new Properties();
            OutputStream out = new FileOutputStream(filePath);
            properties.setProperty("private_key", this.getPrivateKey());
            properties.setProperty("public_key", this.getPublicKey());
            properties.store(out, "Save Completed");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取密钥字符串
     * 
     * @param keyName  需要获取的密钥名
     * @param fileName 密钥所在文件
     * @return Base64编码的密钥字符串
     */

    public RSAPrivateKey getPrivateKeyFromFile(final String path) {
        /**
         * final Properties config =
         * AppUtil.getAppProperties("keystore/config.properties"); String path =
         * config.getProperty("env.data_path") + "/" + app_package + "/private.key";
         * #数据位置
         */
        try {
            InputStream in = new FileInputStream(path);
            Properties properties = new Properties();
            properties.load(in);
            return getPrivateKeyFromString(properties.getProperty("private_key"));
        } catch (Exception e) {
            System.out.println("getKeyString()#" + e.getMessage() );
        }
        return null;
    }

    public RSAPrivateKey getPrivateKeyFromString(String privateKeyStr) throws Exception {
        try {

            byte[] buffer = Base64.getDecoder().decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);

            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    public RSAPublicKey getPublicKeyFromFile(final String path) {
        /**
         * final Properties config =
         * AppUtil.getAppProperties("keystore/config.properties"); String path =
         * config.getProperty("env.data_path") + "/" + app_package + "/private.key";
         * #数据位置
         */
        try {
            InputStream in = new FileInputStream(path);
            Properties properties = new Properties();
            properties.load(in);
            return getPublicKeyFromString(properties.getProperty("public_key"));
        } catch (Exception e) {
             e.printStackTrace();
        }
        return null;
    }

    public RSAPublicKey getPublicKeyFromString(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = Base64.getDecoder().decode(publicKeyStr);

            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    public String encode(String data, RSAPrivateKey key) {
 
        String encodeStr = "";
        int sum=data.length();
        int span=sum/50+1; 
        if(sum>0){
        for (int index = 0; index <span; index ++) {
                int end = (index +1)*50;
                if( end > (sum-1))  end=sum-1; 
                if((index * 50) < end){ 
                        String childStr = data.substring(index * 50, end);
                        try {
                            encodeStr += Base64.getEncoder().encodeToString(encrypt(key, childStr.getBytes("utf-8"))) + "_r_n";
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }
        }
    }
        return encodeStr;

    }

    public String encode(String data) {
        try {
            return encode(data, getPrivateKeyFromString(getPrivateKey()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
     }
     public   String decode(String data, RSAPublicKey key) {

          String[] da=data.split("_r_n");
          String deStr="";
          for(int i=0;i<da.length;i++){
             if(!da[i].equals("")){
                 try {
                    deStr+=  new String(decrypt(key, Base64.getDecoder().decode(da[i])),"utf-8");
                  } catch (Exception e) {
                     e.printStackTrace();
                  }
 
             }
          } 
        return deStr; 
     }
     public   String decode(String data ) {
        try {
            return decode(data,getPublicKeyFromString(getPublicKey()));
        } catch (Exception e) {
         
            e.printStackTrace();
        }
        return null; 
     }
    /**
     * 公钥加密过程
     *
     * @param publicKey     公钥
     * @param plainTextData 明文数据
     * @return
     * @throws Exception 加密过程中的异常信息
     */
    public   byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData)
            throws Exception {
        if (publicKey == null) {
            throw new Exception("加密公钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(plainTextData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * 私钥加密过程
     *
     * @param privateKey    私钥
     * @param plainTextData 明文数据
     * @return
     * @throws Exception 加密过程中的异常信息
     */
    public   byte[] encrypt(RSAPrivateKey privateKey, byte[] plainTextData)
            throws Exception {
        if (privateKey == null) {
            throw new Exception("加密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(plainTextData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * 私钥解密过程
     *
     * @param privateKey 私钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public   byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData)
            throws Exception {
        if (privateKey == null) {
            throw new Exception("解密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }

    /**
     * 公钥解密过程
     *
     * @param publicKey  公钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public   byte[] decrypt(RSAPublicKey publicKey, byte[] cipherData)
            throws Exception {
        if (publicKey == null) {
            throw new Exception("解密公钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    /**
     * 字节数据转十六进制字符串
     *
     * @param data 输入数据
     * @return 十六进制内容
     */
}