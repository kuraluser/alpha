/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargingCargoApiTemperature;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * Repository Class of DischargingCargoApiTemperature entity
 *
 * @author sreemanikandan.k
 * @since 10/01/2022
 */
@Repository
public interface DischargingCargoToBeDischargedRepository
    extends CommonCrudRepository<DischargingCargoApiTemperature, Long> {

  /**
   * Method to fetch DischargingCargoApiTemperature entity
   *
   * @param dischargeInformation DischargeInformation input
   * @param cargoNominationId cargoNominationId input
   * @return DischargingCargoApiTemperature entity
   */
  Optional<DischargingCargoApiTemperature>
      findByDischargeInformationAndCargoNominationIdAndIsActiveTrue(
          DischargeInformation dischargeInformation, Long cargoNominationId);

  /**
   * Method to fetch all DischargingCargoApiTemperature entities against DischargeInformation input
   *
   * @param dischargeInformation DischargeInformation input
   * @return List of DischargingCargoApiTemperature entities
   */
  List<DischargingCargoApiTemperature> findByDischargeInformationAndIsActiveTrue(
      DischargeInformation dischargeInformation);
}
