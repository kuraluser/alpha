/* Licensed at AlphaOri Technologies */
package com.cpdss.envoywriter.common;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.zip.ZipOutputStream;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
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

  public static String getCheckSum(ZipOutputStream zipInputStream) {

    MessageDigest md;
    try {
      md = MessageDigest.getInstance("MD5");
      try (OutputStream os = zipInputStream;
          DigestOutputStream dis = new DigestOutputStream(os, md)) {}

      return DatatypeConverter.printHexBinary(md.digest()).toUpperCase();
    } catch (NoSuchAlgorithmException e) {
      log.error("Error while hashing: " + e);
    } catch (IOException e) {
      log.error("Error while hashing: " + e);
    }
    return null;
  }
}
