/* Licensed at AlphaOri Technologies */
package com.cpdss.envoyreader.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.zip.ZipInputStream;
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

  public static Boolean isCheckSum(ZipInputStream zipInputStream, String checkSum) {
    String checksumGenerated = null;
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      // Using MessageDigest update() method to provide input
      byte[] buffer = new byte[8192];
      int numOfBytesRead;
      while ((numOfBytesRead = zipInputStream.read(buffer)) > 0) {
        md.update(buffer, 0, numOfBytesRead);
      }
      byte[] hash = md.digest();
      checksumGenerated =
          new BigInteger(1, hash).toString(16); // don't use this, truncates leading zero

      return checkSum.equalsIgnoreCase(checksumGenerated) ? true : false;
    } catch (Exception ex) {
    }
    return null;
  }
}
