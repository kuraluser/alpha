/* Licensed at AlphaOri Technologies */
package com.cpdss.loadicatorintegration.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadicatorintegration.entity.StowageDetails;
import com.cpdss.loadicatorintegration.entity.StowagePlan;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface StowageDetailsRepository extends CommonCrudRepository<StowageDetails, Long> {

  @Transactional
  @Modifying
  @Query(
      "UPDATE StowageDetails SD SET SD.cargoId = ?2 WHERE SD.stowagePlan = ?1 and SD.cargoName = ?3")
  public void updateCargoIdInStowageDetailsByStowagePlan(
      StowagePlan stowagePlan, Long cargoId, String abbr);

  @Transactional
  @Modifying
  @Query(
      value =
          "DELETE FROM ld_stowage_details s USING ld_stowage_details d WHERE s.id < d.id"
              + " AND s.stowageplan_id = d.stowageplan_id AND s.cargo_id = d.cargo_id AND s.tank_id = d.tank_id",
      nativeQuery = true)
  public void deleteDuplicatesFromStowageDetails();
}
