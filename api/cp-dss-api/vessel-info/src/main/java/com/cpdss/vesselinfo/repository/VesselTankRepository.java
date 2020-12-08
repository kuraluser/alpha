/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.TankCategory;
import com.cpdss.vesselinfo.entity.Vessel;
import com.cpdss.vesselinfo.entity.VesselTank;
import java.util.List;

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
}
