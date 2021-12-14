/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRES_NEW)
@Repository("dischargeCommunicationInformationRepository")
public interface DischargeCommunicationInformationRepository
    extends DischargeInformationRepository {}
