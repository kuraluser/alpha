/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import java.util.Optional;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingInformationStatus;

public interface DischargeInformationStatusRepository
    extends CommonCrudRepository<DischargingInformationStatus, Long> {

	Optional<DischargingInformationStatus> findByIdAndIsActive(Long statusId, boolean b);

}
