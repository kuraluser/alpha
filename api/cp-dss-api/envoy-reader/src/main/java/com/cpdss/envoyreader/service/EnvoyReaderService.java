/* Licensed at AlphaOri Technologies */
package com.cpdss.envoyreader.service;

import com.cpdss.common.generated.EnvoyReader;

/** @Author jerin.g */
public class EnvoyReaderService {

  public EnvoyReader.EnvoyReaderResultReply getDataFromCommunicationServer(
      EnvoyReader.EnvoyReaderResultRequest request,
      EnvoyReader.EnvoyReaderResultReply.Builder builder) {
    return builder.build();
  }
}
