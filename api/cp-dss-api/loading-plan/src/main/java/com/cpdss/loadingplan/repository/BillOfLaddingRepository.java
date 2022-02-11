/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.BillOfLadding;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

/** @Author jerin.g */
public interface BillOfLaddingRepository extends CommonCrudRepository<BillOfLadding, Long> {

  @Query(
      "FROM BillOfLadding BL INNER JOIN LoadingInformation LI ON BL.loadingInformation.id = LI.id AND LI.loadablePatternXId = ?1 AND BL.portId = ?2 AND BL.isActive = ?3 ORDER BY BL.id")
  public List<BillOfLadding> findByLoadablePatternXIdAndPortIdAndIsActive(
      Long patternId, Long portId, Boolean isActive);

  public List<BillOfLadding> findByCargoNominationIdAndIsActive(
      Long cargoNominationId, Boolean isActive);

  public List<BillOfLadding> findByCargoNominationIdInAndIsActive(
      List<Long> cargoNominationId, Boolean isActive);

  public List<BillOfLadding> findByPortIdInAndIsActive(
      List<Long> cargoNominationId, Boolean isActive);

  @Query(
      "FROM BillOfLadding BL INNER JOIN LoadingInformation LI ON BL.loadingInformation.id = LI.id AND LI.loadablePatternXId = ?1 AND BL.isActive = ?2")
  public List<BillOfLadding> findByLoadablePatternXIdAndIsActive(
      Long loadablePatternId, Boolean isActive);

  public List<BillOfLadding>
      findByCargoNominationIdInAndLoadingInformation_LoadablePatternXIdAndIsActive(
          List<Long> cargoIds, long patternId, boolean b);
}
