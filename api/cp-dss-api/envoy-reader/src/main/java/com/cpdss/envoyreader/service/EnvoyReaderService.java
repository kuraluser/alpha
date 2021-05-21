/* Licensed at AlphaOri Technologies */
package com.cpdss.envoyreader.service;

import static com.cpdss.envoyreader.common.Utility.*;

import java.util.zip.ZipInputStream;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/** @Author jerin.g */
@Service
@Log4j2
public class EnvoyReaderService {

  public void getResult() {
    ZipInputStream zipInputStream = null;
    String checkSum = null;

    if (isCheckSum(zipInputStream, checkSum)) {

    } else {
      log.error("Error while hash compare");
    }
  }
}
