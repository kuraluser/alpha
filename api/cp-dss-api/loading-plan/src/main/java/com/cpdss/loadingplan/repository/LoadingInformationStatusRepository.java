/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingInformationStatus;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadingInformationStatusRepository
    extends CommonCrudRepository<LoadingInformationStatus, Long> {

  Optional<LoadingInformationStatus> findByIdAndIsActive(Long id, Boolean isActive);
}
