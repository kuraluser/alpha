/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.Vessel;
import java.util.List;

/**
 * Vessel repository
 *
 * @author suhail.k
 */
public interface VesselRepository extends CommonCrudRepository<Vessel, Long> {

  public List<Vessel> findByCompanyXIdAndIsActive(Long companyXId, boolean isActive);
}
