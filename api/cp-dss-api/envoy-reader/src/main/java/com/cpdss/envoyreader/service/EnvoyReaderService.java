/* Licensed at AlphaOri Technologies */
package com.cpdss.envoyreader.service;

import static com.cpdss.envoyreader.common.Utility.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.zip.ZipInputStream;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/** @Author jerin.g */
@Service
@Log4j2
public class EnvoyReaderService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	  @Value("${cpdss.communication.salt}")
	  private String salt;

  public void getResult() {
    ZipInputStream zipInputStream = null;
    String checkSum = null;
    ResponseEntity<Resource> responseEntity = null;
     responseEntity = restTemplate.exchange( "", HttpMethod.GET, responseEntity, Resource.class );

    InputStream responseInputStream;
    try {
        responseInputStream = responseEntity.getBody().getInputStream();
        zipInputStream =  new ZipInputStream(responseInputStream);
    }
    catch (IOException e) {
        throw new RuntimeException(e);
    }

    if (isCheckSum(zipInputStream, checkSum)) {

    	//ByteArrayOutputStream bos = new ByteArrayOutputStream(encryptedString.length());
    	ByteArrayOutputStream bos = new ByteArrayOutputStream("".length());
    	
    	 byte[] zipBytes = bos.toByteArray();
         ByteArrayInputStream bis = new ByteArrayInputStream(zipBytes);
         ZipInputStream zis = new ZipInputStream(bis);

         ByteArrayOutputStream buffer = (ByteArrayOutputStream) bos;
         byte[] bytes = buffer.toByteArray();
         InputStream inputStream = new ByteArrayInputStream(bytes);

         String imoNumber = "";
         
         String encryptedString =
             new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                 .lines()
                 .collect(Collectors.joining("\n"));
         
         String decryptedString = decrypt(encryptedString, imoNumber, salt);

         System.out.println("--- " + decryptedString);
    	
    	
    } else {
      log.error("Error while hash compare");
    }
  }
}
