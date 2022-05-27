package za.co.vodacom.cvm.utils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
public class RSAEncryption {

    public static String encrypt(String sSrc,String sKey) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        byte[] pText = sSrc.getBytes();
        SecretKeySpec secret = new SecretKeySpec(sKey.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secret, new GCMParameterSpec(128, iv));
        byte[] cipherText = cipher.doFinal(pText);

        byte[] cipherTextWithIv = ByteBuffer.allocate(iv.length + cipherText.length)
            .put(iv)
            .put(cipherText)
            .array();
        return new String(Base64.getEncoder().encode(cipherTextWithIv));
    }
}
