/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.domain.CargoTankMaster;
import com.cpdss.vesselinfo.domain.VesselTankDetails;
import com.cpdss.vesselinfo.entity.TankCategory;
import com.cpdss.vesselinfo.entity.Vessel;
import com.cpdss.vesselinfo.entity.VesselTank;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

/**
 * Vessel tank repository
 *
 * @author suhail.k
 */
public interface VesselTankRepository extends CommonCrudRepository<VesselTank, Long> {

  /**
   * find vessel tanks by category
   *
   * @param vessel
   * @param categories
   * @param isActive
   * @return
   */
  public List<VesselTank> findByVesselAndTankCategoryInAndIsActive(
      Vessel vessel, List<TankCategory> categories, boolean isActive);

  /**
   * @param vessel
   * @param isActive
   * @return List<VesselTank>
   */
  public List<VesselTank> findByVesselAndIsActive(Vessel vessel, Boolean isActive);

  @Query(
      "SELECT new com.cpdss.vesselinfo.domain.VesselTankDetails(id,shortName,tankName,tankDisplayOrder)"
          + " from VesselTank VT"
          + " WHERE VT.id IN (?1)")
  public List<VesselTankDetails> findTankDetailsByTankIds(List<Long> tankIds);

  @Query(
      "SELECT new com.cpdss.vesselinfo.domain.CargoTankMaster(id,shortName)"
          + " from VesselTank VT"
          + " WHERE VT.tankCategory.id = 1 AND VT.vessel.id = ?1 AND isActive = ?2")
  public List<CargoTankMaster> findCargoTankMaster(Long vesselId, boolean isActive);

  /**
   * @param tankIds
   * @param isActive
   * @return List<VesselTank>
   */
  public List<VesselTank> findByIdInAndIsActive(List<Long> tankIds, Boolean isActive);
}
