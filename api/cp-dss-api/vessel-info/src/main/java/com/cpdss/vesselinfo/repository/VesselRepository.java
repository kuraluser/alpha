/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.domain.VesselDetails;
import com.cpdss.vesselinfo.entity.Vessel;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

/**
 * Vessel repository
 *
 * @author suhail.k
 */
public interface VesselRepository extends CommonCrudRepository<Vessel, Long> {

  public List<Vessel> findByCompanyXIdAndIsActive(Long companyXId, boolean isActive);

  public Vessel findByCompanyXIdAndIdAndIsActive(Long companyXId, Long vesselId, boolean isActive);

  @Query(
      "SELECT new com.cpdss.vesselinfo.domain.VesselDetails(VDC.displacement, V.lightWeight, V.deadWeightConstant) from Vessel V INNER JOIN VesselDraftCondition VDC on V.id = VDC.vessel.id AND V.id = ?1 AND VDC.draftCondition.id = ?2 AND VDC.draftExtreme = ?3")
  public VesselDetails findVesselDetailsById(
      Long id, Long vesselDraftConditionId, BigDecimal draftExtreme);
}
