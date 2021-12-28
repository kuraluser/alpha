/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import com.cpdss.common.utils.MessageTypes;
import com.cpdss.loadablestudy.domain.*;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import java.time.LocalDateTime;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
public class LoadableStudyServiceShore {

  @Autowired
  private LoadableStudyCommunicationStatusRepository loadableStudyCommunicationStatusRepository;

  public void updateCommunicationStatus(String messageId, Long loadablePatternId) {
    LoadableStudyCommunicationStatus lsCommunicationStatus = new LoadableStudyCommunicationStatus();
    lsCommunicationStatus.setMessageUUID(messageId);
    lsCommunicationStatus.setCommunicationStatus(
        CommunicationStatus.RECEIVED_WITH_HASH_VERIFIED.getId());
    lsCommunicationStatus.setReferenceId(loadablePatternId);
    lsCommunicationStatus.setMessageType(MessageTypes.VALIDATEPLAN.getMessageType());
    lsCommunicationStatus.setCommunicationDateTime(LocalDateTime.now());
    this.loadableStudyCommunicationStatusRepository.save(lsCommunicationStatus);
  }
}
