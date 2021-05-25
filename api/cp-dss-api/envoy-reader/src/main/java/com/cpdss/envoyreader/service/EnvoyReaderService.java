/* Licensed at AlphaOri Technologies */
package com.cpdss.envoyreader.service;

import static com.cpdss.envoyreader.common.Utility.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import com.cpdss.common.generated.EnvoyReader.ReaderReply.Builder;
import com.cpdss.common.generated.EnvoyReader.ResultJson;

/** @Author jerin.g */
@Service
@Log4j2
public class EnvoyReaderService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	  @Value("${cpdss.communication.salt}")
	  private String salt;

  public void getResult(ResultJson request, Builder builder) {
    ZipInputStream zipInputStream = null;
    String checkSum = null;
		/*
		 * ResponseEntity<Resource> responseEntity = null; responseEntity =
		 * restTemplate.exchange( "http://192.168.2.89:4800/download/kazusa/42/9513402",
		 * HttpMethod.GET, responseEntity, Resource.class );
		 * 
		 * InputStream responseInputStream; try { responseInputStream =
		 * responseEntity.getBody().getInputStream(); zipInputStream = new
		 * ZipInputStream(responseInputStream); } catch (IOException e) { throw new
		 * RuntimeException(e); }
		 * 
		 * // if (isCheckSum(zipInputStream, checkSum)) {
		 * 
		 * //ByteArrayOutputStream bos = new
		 * ByteArrayOutputStream(encryptedString.length()); ByteArrayOutputStream bos =
		 * new ByteArrayOutputStream("".length());
		 * 
		 * byte[] zipBytes = bos.toByteArray(); ByteArrayInputStream bis = new
		 * ByteArrayInputStream(zipBytes); ZipInputStream zis = new ZipInputStream(bis);
		 * 
		 * ByteArrayOutputStream buffer = (ByteArrayOutputStream) bos; byte[] bytes =
		 * buffer.toByteArray(); InputStream inputStream = new
		 * ByteArrayInputStream(bytes);
		 * 
		 * String imoNumber = ""; System.out.println("== " + responseInputStream);
		 * String encryptedString = new BufferedReader(new
		 * InputStreamReader(responseInputStream, StandardCharsets.UTF_8)) .lines()
		 * .collect(Collectors.joining("\n"));
		 * 
		 * System.out.println("--- " + encryptedString);
		 * 
		 * String decryptedString = decrypt(encryptedString, imoNumber, salt);
		 * 
		 * System.out.println("--- " + decryptedString);
		 */
    	
		/*
		 * } else { log.error("Error while hash compare"); }
		 */
         
         File file = restTemplate.execute("http://192.168.2.89:4800/download/kazusa/54/9513402", HttpMethod.GET, null, clientHttpResponse -> {
        	    File ret = File.createTempFile("temp", ".zip");
        	    StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
        	    return ret;
        	});
         
         
		try {
		
			ZipFile zipFile = new ZipFile(file);
			 for (Enumeration e = zipFile.entries(); e.hasMoreElements(); ) {
			        ZipEntry entry = (ZipEntry) e.nextElement();
			        if (!entry.isDirectory()) {
			            
			                StringBuilder out = getTxtFiles(zipFile.getInputStream(entry));
			                System.out.println("00 " + out);
			            
			        }
			    }} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  }
  
  private  static StringBuilder getTxtFiles(InputStream in)  {
	    StringBuilder out = new StringBuilder();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	    String line;
	    try {
	        while ((line = reader.readLine()) != null) {
	            out.append(line);
	        }
	    } catch (IOException e) {
	        // do something, probably not a text file
	        e.printStackTrace();
	    }
	    return out;
	}
}
