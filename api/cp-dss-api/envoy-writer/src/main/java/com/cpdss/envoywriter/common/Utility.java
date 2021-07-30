/* Licensed at AlphaOri Technologies */
package com.cpdss.envoywriter.common;

import static com.cpdss.common.utils.CommunicatonServerUtils.*;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.log4j.Log4j2;

/** @Author jerin.g */
@Log4j2
public class Utility {

  private static SecretKeySpec secretKey;
  private static byte[] key;

  public static void setEncryptionKey(String myKey) {
    MessageDigest sha = null;
    try {
      key = myKey.getBytes("UTF-8");
      sha = MessageDigest.getInstance("SHA-1");
      key = sha.digest(key);
      key = Arrays.copyOf(key, 16);
      System.out.println("key1" + myKey);
      secretKey = new SecretKeySpec(key, "AES");
    } catch (NoSuchAlgorithmException e) {
      log.error("NoSuchAlgorithmException while setEncryptionKey: " + e);
    } catch (UnsupportedEncodingException e) {
      log.error("UnsupportedEncodingException while setEncryptionKey: " + e);
    }
  }

  public static String encrypt(String strToEncrypt, String secret) {
    try {
      setEncryptionKey(secret);
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);
      return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
    } catch (Exception e) {
      log.error("Error while encrypting: " + e);
    }
    return null;
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
