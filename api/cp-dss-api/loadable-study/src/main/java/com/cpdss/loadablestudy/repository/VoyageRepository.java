/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.Voyage;
import java.util.List;

/** @Author jerin.g */
public interface VoyageRepository extends CommonCrudRepository<Voyage, Long> {

  public List<Voyage> findByCompanyXIdAndVesselXIdAndVoyageNoIgnoreCase(
      Long companyId, Long vesselXId, String voyageNo);

  public List<Voyage> findByVesselXIdAndIsActiveOrderByLastModifiedDateTimeDesc(
      Long vesselXId, boolean isActive);
}
