/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.loadingplan.entity.LoadingInformation;
import org.springframework.transaction.annotation.Transactional;

public interface LoadingInformationCommunicationRepository extends LoadingInformationRepository {

  @Override
  @Transactional
  LoadingInformation save(LoadingInformation loadingInformation);
}
