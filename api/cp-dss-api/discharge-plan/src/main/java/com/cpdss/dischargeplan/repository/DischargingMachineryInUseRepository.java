/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargingMachineryInUse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DischargingMachineryInUseRepository
    extends CommonCrudRepository<DischargingMachineryInUse, Long> {

  @Query(
      "FROM DischargingMachineryInUse dmu WHERE dmu.dischargingInformation.id = ?1 and dmu.isActive = true")
  List<DischargingMachineryInUse> findAllByDischargingInfoId(Long id);

  Optional<DischargingMachineryInUse>
      findByDischargingInformationAndMachineXidAndMachineTypeXidAndIsActiveTrue(
          DischargeInformation dischargingInformation, long machineId, int machineTypeValue);
}
