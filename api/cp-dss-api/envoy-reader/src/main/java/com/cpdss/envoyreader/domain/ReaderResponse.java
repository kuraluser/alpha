/* Licensed at AlphaOri Technologies */
package com.cpdss.envoyreader.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.File;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/** @Author jerin.g */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReaderResponse {
  private File file;
  private HttpHeaders responseHeader;
  private HttpStatus httpStatus;
  private String checksum;
}
