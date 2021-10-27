package com.cpdss.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;

import org.apache.poi.poifs.crypt.CipherAlgorithm;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.crypt.HashAlgorithm;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility for generate password protect file
 * @author sajith.m
 *
 */
@Slf4j
public class GenerateProtectedFile {
	
	private GenerateProtectedFile() {
		super();
	}

	/**
	 * To generate password protected excel file
	 * @param unProtectedFile
	 * @param password
	 * @return File
	 * @throws IOException
	 */
	public static File generatePasswordProtectedFile(File unProtectedFile, String password) throws IOException{
		//code for password protection

		String originalFileName = unProtectedFile.getName() == null ? "" : unProtectedFile.getName();
	    if (originalFileName != null
	        && ((originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase())
	            .equals("xlsx")
	        || (originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase())
	            .equals("xls"))) {
			FileInputStream fileInput = new FileInputStream(unProtectedFile);
			try(BufferedInputStream bufferInput = new BufferedInputStream(fileInput)) {			

				try(POIFSFileSystem fileSystem = new POIFSFileSystem()){
			        EncryptionInfo info = new EncryptionInfo(
			        		EncryptionMode.agile, CipherAlgorithm.aes192, HashAlgorithm.sha384, -1, -1, null);
		
			        Encryptor enc = info.getEncryptor();
			        enc.confirmPassword(password);
			        
			        try(Workbook workbook = new XSSFWorkbook(bufferInput)){
						try(FileOutputStream fileOut = new FileOutputStream(unProtectedFile)){
					        OutputStream outputStream = enc.getDataStream(fileSystem);
					        workbook.write(outputStream);		        
					        outputStream.close();
					        fileSystem.writeFilesystem(fileOut);
						}
				        return unProtectedFile;
			        }
				}
				}catch(Exception e){
					log.info("Exception in generate password protected file "+e.getLocalizedMessage());
					return unProtectedFile;
				}
	    	}else {
	    		return unProtectedFile;
	    	}
		}

		/**
		 * generate password protected excel file with custom password
		 * @param unProtectedFile
		 * @param voyageNum
		 * @param fileCreatedDate
		 * @return File
		 * @throws IOException
		 */
		public static File generatePasswordProtectedFile(File unProtectedFile, String voyageNum, 
				LocalDate fileCreatedDate) throws IOException{
		//combine voyageNum + fileCreatedDate with out time
		String password = voyageNum + fileCreatedDate.toString();
		return generatePasswordProtectedFile(unProtectedFile, password);
		}


}
