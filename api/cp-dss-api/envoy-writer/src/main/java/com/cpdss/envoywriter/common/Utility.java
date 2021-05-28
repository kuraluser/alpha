/* Licensed at AlphaOri Technologies */
package com.cpdss.envoywriter.common;

import static com.cpdss.common.utils.CommunicatonServerUtils.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

  public static String getCheckSum(File file) {

    MessageDigest mdigest;
    try {
      mdigest = MessageDigest.getInstance("MD5");
      // Get the checksum
      return getChecksum(mdigest, file);
    } catch (IOException e) {
      log.error("Error while getCheckSum: " + e);
    } catch (NoSuchAlgorithmException e) {
      log.error("Error while getCheckSum: " + e);
    }
    return null;
  }
}
