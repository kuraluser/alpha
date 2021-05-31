/* Licensed at AlphaOri Technologies */
package com.cpdss.envoyreader.common;

import static com.cpdss.common.utils.CommunicatonServerUtils.*;

import java.io.File;
import java.io.IOException;
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

  public static boolean isCheckSum(File file, String checkSum) {

    MessageDigest mdigest;
    try {
      mdigest = MessageDigest.getInstance("MD5");
      // Get the checksum
      return getChecksum(mdigest, file).equals(checkSum) ? true : false;
    } catch (IOException e) {
      log.error("IOException at isCheckSum: " + e);
    } catch (NoSuchAlgorithmException e) {
      log.error("NoSuchAlgorithmException at isCheckSum: " + e);
    }
    return false;
  }
}
