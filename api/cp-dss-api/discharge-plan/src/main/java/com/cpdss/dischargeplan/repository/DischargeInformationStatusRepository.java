/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingInformationStatus;
import java.util.Optional;

public interface DischargeInformationStatusRepository
    extends CommonCrudRepository<DischargingInformationStatus, Long> {

  Optional<DischargingInformationStatus> findByIdAndIsActive(Long statusId, boolean b);
}
