/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRES_NEW)
public interface LoadingInformationCommunicationRepository extends LoadingInformationRepository {}
