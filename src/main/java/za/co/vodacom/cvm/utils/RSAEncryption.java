package za.co.vodacom.cvm.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
public class RSAEncryption {

    private static String ivParameter = "1234567890123456";

//    public String encrypt(String code, String key)
//        throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
//        byte[] KeyData = key.getBytes();
//        SecretKeySpec KS = new SecretKeySpec(KeyData, "RSA");
//        Cipher cipher = Cipher.getInstance("RSA/None/OAEPWITHSHA-256ANDMGF1PADDING");
//        cipher.init(Cipher.ENCRYPT_MODE, KS);
//        String encryptedtext = Base64.getEncoder().encodeToString(cipher.doFinal(code.getBytes("UTF-8")));
//        return encryptedtext;
//    }

    public String decrypt(String encryptedtext, String key)
        throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] KeyData = key.getBytes();
        SecretKeySpec KS = new SecretKeySpec(KeyData, "RSA");
        byte[] ecryptedtexttobytes = Base64.getDecoder().decode(encryptedtext);
        Cipher cipher = Cipher.getInstance("RSA/None/OAEPWITHSHA-256ANDMGF1PADDING");
        cipher.init(Cipher.DECRYPT_MODE, KS);
        byte[] decrypted = cipher.doFinal(ecryptedtexttobytes);
        String decryptedString = new String(decrypted, Charset.forName("UTF-8"));
        return decryptedString;
    }

    // encryption
    public String encrypt(String sSrc, String sKey) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes()); // using CBC mode, you need a vector IV to increase the strength of encryption algorithm
        // Python code to create random 16 byte iv string: iv = Random.new().read(16)
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        return Base64.getEncoder().encodeToString(encrypted); // use Base64 for transcoding here.
    }

    // decryption
   /* public String decrypt2(String sSrc, String sKey) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

        byte[] raw = sKey.getBytes("ASCII");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] encrypted1 = Base64.getDecoder().decode(sSrc.getBytes()); // decrypt with Base64 first
        // IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
        IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(encrypted1, 0, 16));
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        // byte[] original = cipher.doFinal(encrypted1);
        byte[] original = cipher.doFinal(Arrays.copyOfRange(encrypted1, 16, encrypted1.length));
        String originalString = new String(original, "utf-8");
        return originalString;

    }*/
    private static String decrypt45(String sSrc, String sKey) throws Exception {
        byte[] encrypted1 = Base64.getDecoder().decode(sSrc.getBytes());
        SecretKeySpec skeySpec = new SecretKeySpec(sKey.getBytes(), "AES");
        final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        AlgorithmParameterSpec gcmIv = new GCMParameterSpec(128, encrypted1, 0, 16);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, gcmIv);

        //use everything from 16 bytes on as ciphertext
        byte[] plainText = cipher.doFinal(encrypted1, 16, encrypted1.length - 16);
        String originalString = new String(plainText, "utf-8");
        return originalString;
    }


}
