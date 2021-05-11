/* Licensed at AlphaOri Technologies */
package com.cpdss.envoywriter.common;

import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.log4j.Log4j2;

/** @Author jerin.g */
@Log4j2
public class Utility {

  private static final String SECRET_KEY = "my_super_secret_key_ho_ho_ho";
  private static final String SALT = "ssshhhhhhhhhhh!!!!";

  public static String encrypt(String strToEncrypt, String secret, String salt) {
    try {
      byte[] iv = new byte[16];
      IvParameterSpec ivspec = new IvParameterSpec(iv);

      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
      KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), 65536, 256);
      SecretKey tmp = factory.generateSecret(spec);
      SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
      return Base64.getEncoder()
          .encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
    } catch (Exception e) {
      log.error("Error while encrypting: " + e);
      return null;
    }
  }

  public static String decrypt(String strToDecrypt, String secret, String salt) {
    try {
      byte[] iv = new byte[16];
      IvParameterSpec ivspec = new IvParameterSpec(iv);

      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
      KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), 65536, 256);
      SecretKey tmp = factory.generateSecret(spec);
      SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
      cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
      return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    } catch (Exception e) {
      log.error("Error while decrypting: " + e);
      return null;
    }
  }
}
