/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.domain.VesselDetails;
import com.cpdss.vesselinfo.domain.VesselInfo;
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

  public List<Vessel> findByIsActive(boolean isActive);

  public Vessel findByIdAndIsActive(Long vesselId, boolean isActive);

  @Query(
      "SELECT new com.cpdss.vesselinfo.domain.VesselDetails"
          + "(VDC.displacement, V.lightweight, VDC.deadweight, VDC.draftCondition.name, V.deadweightConstant) "
          + "from Vessel V "
          + "LEFT JOIN VesselDraftCondition VDC on V.id = VDC.vessel.id "
          + "WHERE V.id = ?1 AND VDC.draftCondition.id = ?2 AND VDC.draftExtreme = ?3")
  public VesselDetails findVesselDetailsById(
      Long id, Long vesselDraftConditionId, BigDecimal draftExtreme);

  @Query(
      "SELECT new com.cpdss.vesselinfo.domain.VesselInfo"
          + "(V.id, V.name, V.imoNumber,V.typeOfShip,V.code,V.deadweightConstant,V.provisionalConstant) "
          + "from Vessel V "
          + "WHERE V.id = ?1 AND V.isActive = ?2")
  public VesselInfo findVesselDetailsByVesselId(Long id, Boolean isActive);
}
