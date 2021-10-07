/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;

import com.cpdss.loadingplan.entity.LoadingPlanCommunicationStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/** author:Selvy Thomas */
public interface LoadingPlanCommunicationStatusRepository
    extends CommonCrudRepository<LoadingPlanCommunicationStatus, Long> {


}
