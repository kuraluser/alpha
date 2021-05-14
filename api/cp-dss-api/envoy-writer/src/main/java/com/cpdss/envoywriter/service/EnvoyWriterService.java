/* Licensed at AlphaOri Technologies */
package com.cpdss.envoywriter.service;

import static com.cpdss.envoywriter.common.Utility.*;

import com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson;
import com.cpdss.common.generated.EnvoyWriter.WriterReply;
import com.cpdss.common.generated.EnvoyWriter.WriterReply.Builder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Transactional
@Service
public class EnvoyWriterService {

  @Value("${cpdss.communication.salt}")
  private String salt;

  public WriterReply passDataToCommunicationServer(LoadableStudyJson request, Builder builder) {

    String encryptedString = encrypt(request.getLoadableStudy(), request.getImoNumber(), salt);

    return builder.build();
  }
}
