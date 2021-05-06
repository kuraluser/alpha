/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.HydrostaticTable;
import com.cpdss.vesselinfo.entity.Vessel;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/** @Author jerin.g */
public interface HydrostaticTableRepository
    extends CommonCrudRepository<HydrostaticTable, Long>,
        JpaSpecificationExecutor<HydrostaticTable> {

  @Query(
      "SELECT HT.tpc FROM HydrostaticTable HT WHERE HT.vessel.id  = ?1  ORDER BY ABS(HT.draft - ?2)")
  public List<BigDecimal> getTPCFromDraf(Long vesselId, BigDecimal draft, Boolean isActive);

  public List<HydrostaticTable> findByVesselAndIsActive(Vessel vessel, Boolean isActive);

  Optional<HydrostaticTable> findFirstByVesselOrderByDraftDesc(Vessel vessel);

  Optional<HydrostaticTable> findFirstByVesselOrderByDraftAsc(Vessel vessel);
}
