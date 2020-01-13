package cn.ob767.systemservice.rsa;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class RSAEncrypt {
    public static void main(String[] args) {
        String s = "obb767";
        Map<Integer, String> map = RSAEncrypt.genKeyPair();
        assert map != null;
        String s2 = RSAEncrypt.encrypt(s, map.get(0));
        System.out.println("公钥加密后：" + s2);
        System.out.println("私钥解密后：" + RSAEncrypt.decrypt(s2, map.get(1)));
    }

    /**
     * 随机生成密钥对
     */
    public static Map<Integer, String> genKeyPair() {

        Map<Integer, String> keyMap = new HashMap<>();  //用于封装随机产生的公钥与私钥

        try {
            // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");

            // 初始化密钥对生成器，密钥大小为96-1024位
            keyPairGen.initialize(1024, new SecureRandom());

            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥

            // 得到公钥字符串
            String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
            // 得到私钥字符串
            String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));

            // 将公钥和私钥保存到Map
            keyMap.put(0, publicKeyString);  //0表示公钥
            keyMap.put(1, privateKeyString);  //1表示私钥
        } catch (Exception e) {
            log.info("生成公钥私钥异常：" + e.getMessage());
            return null;
        }

        return keyMap;
    }

    /**
     * RSA公钥加密
     *
     * @param str       需要加密的字符串
     * @param publicKey 公钥
     * @return 公钥加密后的内容
     */
    public static String encrypt(String str, String publicKey) {
        String outStr = null;
        try {
            //base64编码的公钥
            byte[] decoded = Base64.decodeBase64(publicKey);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            log.info("使用公钥加密【" + str + "】异常：" + e.getMessage());
        }
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 私钥解密后的内容
     */
    public static String decrypt(String str, String privateKey) {
        String outStr = null;
        try {
            //64位解码加密后的字符串
            byte[] inputByte = Base64.decodeBase64(str.getBytes(StandardCharsets.UTF_8));
            //base64编码的私钥
            byte[] decoded = Base64.decodeBase64(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            outStr = new String(cipher.doFinal(inputByte));
        } catch (Exception e) {
            log.info("使用私钥解密异常：" + e.getMessage());
        }
        return outStr;
    }
}
