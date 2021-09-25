/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sanalkumar.k
 *
 */
@Slf4j
@Component
public class ExcelExportUtility {

	@Value("${gateway.attachement.rootFolder}")
	private String rootFolder;

	/**
	 * Method to read data from request and Stamp in existing template
	 *
	 * @param requestPayload
	 * @param vesselId
	 * @param voyageId
	 * @param infoId
	 * @param portRotationId
	 * @return
	 * @throws GenericServiceException
	 * @throws IOException
	 */
	public File generateExcel(Object dataObj, String inputFileLocation, String outputFileLocation)
			throws GenericServiceException, IOException {
		log.info("Inside generateExcel utility - Creating " + outputFileLocation + " file using " + inputFileLocation
				+ " template.");
		String outFile = rootFolder + outputFileLocation;
		OutputStream outStream = new FileOutputStream(outFile);

		try (InputStream inStream = this.getClass().getResourceAsStream(inputFileLocation)) {
			if (inStream != null) {
				//Converting object in to excel context
				Context context = getContext(dataObj);
				// Stamping values into excel template
				JxlsHelper.getInstance().processTemplate(inStream, outStream, context);
			} else {
				log.info("Invalid template location - no file present");
				throw new GenericServiceException("Excel generation failed : No input template found",
						CommonErrorCodes.E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new GenericServiceException("Excel generation failed" + e.getMessage(),
					CommonErrorCodes.E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
		} finally {

			closeAndFlushOutput(outStream);
		}
		return new File(outFile);
	}

	/**
	 * Create JSLX context form map
	 * 
	 * @param dataObj
	 * @return
	 */
	private Context getContext(Object dataObj) {
		Context context = new Context();
		ObjectMapper mapper = new ObjectMapper();
		// object to a map
		@SuppressWarnings("unchecked")
		Map<String, Object> dataMap = mapper.convertValue(dataObj, Map.class);
		
		dataMap.entrySet().forEach(entry -> {
			log.info(entry.getKey() + " : " + entry.getValue()); // TODO remove
		});
		// Creating JXLS context
		for (Entry<String, Object> element : dataMap.entrySet()) {
			context.putVar(element.getKey(), element.getValue());
		}
		return context;
	}

	/**
	 * Closing I/O streams
	 * @param outStream
	 */
	private void closeAndFlushOutput(OutputStream outStream) {
		try {
			outStream.flush();
			outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
