/* Licensed at AlphaOri Technologies */
package com.cpdss.envoyreader.common;

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

  public static void setDecryptionKey(String myKey) {
    MessageDigest sha = null;
    try {
      key = myKey.getBytes("UTF-8");
      sha = MessageDigest.getInstance("SHA-1");
      key = sha.digest(key);
      key = Arrays.copyOf(key, 16);
      secretKey = new SecretKeySpec(key, "AES");
    } catch (NoSuchAlgorithmException e) {
      log.error("NoSuchAlgorithmException while setEncryptionKey: " + e);
    } catch (UnsupportedEncodingException e) {
      log.error("UnsupportedEncodingException while setEncryptionKey: " + e);
    }
  }

  public static String decrypt(String strToDecrypt, String secret) {
    try {
      setDecryptionKey(secret);
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
      cipher.init(Cipher.DECRYPT_MODE, secretKey);
      return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    } catch (Exception e) {
      log.error("Error while decrypting: " + e);
    }
    return null;
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
