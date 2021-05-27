/* Licensed at AlphaOri Technologies */
package com.cpdss.envoyreader.common;

import java.io.File;
import java.io.FileInputStream;
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

  public static Boolean isCheckSum(File file, String checkSum) {

    MessageDigest mdigest;
    try {
      mdigest = MessageDigest.getInstance("MD5");
      // Get the checksum
      System.out.println("--- " + checksum(mdigest, file));
      return checksum(mdigest, file).equals(checkSum) ? true : false;
    } catch (IOException e) {
      log.error("Error while getCheckSum: " + e);

    } catch (NoSuchAlgorithmException e) {
      log.error("Error while getCheckSum: " + e);
    }
    return null;
  }

  private static String checksum(MessageDigest digest, File file) throws IOException {
    // Get file input stream for reading the file
    // content
    FileInputStream fis = new FileInputStream(file);

    // Create byte array to read data in chunks
    byte[] byteArray = new byte[1024];
    int bytesCount = 0;

    // read the data from file and update that data in
    // the message digest
    while ((bytesCount = fis.read(byteArray)) != -1) {
      digest.update(byteArray, 0, bytesCount);
    }
    ;

    // close the input stream
    fis.close();

    // store the bytes returned by the digest() method
    byte[] bytes = digest.digest();

    // this array of bytes has bytes in decimal format
    // so we need to convert it into hexadecimal format

    // for this we create an object of StringBuilder
    // since it allows us to update the string i.e. its
    // mutable
    StringBuilder sb = new StringBuilder();

    // loop through the bytes array
    for (int i = 0; i < bytes.length; i++) {

      // the following line converts the decimal into
      // hexadecimal format and appends that to the
      // StringBuilder object
      sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
    }

    // finally we return the complete hash
    return sb.toString();
  }
}
