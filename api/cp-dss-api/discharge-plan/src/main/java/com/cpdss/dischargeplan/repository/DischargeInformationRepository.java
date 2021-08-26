/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import java.util.Optional;

public interface DischargeInformationRepository
    extends CommonCrudRepository<DischargeInformation, Long> {

  Optional<DischargeInformation> findByIdAndIsActiveTrue(Long var1);

  Optional<DischargeInformation> findBySynopticTableXidAndIsActiveTrue(Long var1);

  Optional<DischargeInformation> findByVesselXidAndVoyageXidAndPortRotationXidAndIsActiveTrue(
      Long var1, Long var2, Long var3);
}
