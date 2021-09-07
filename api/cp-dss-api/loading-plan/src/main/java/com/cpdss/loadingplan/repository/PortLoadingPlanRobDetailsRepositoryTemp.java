/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.PortLoadingPlanRobTempDetails;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PortLoadingPlanRobDetailsRepositoryTemp
    extends CommonCrudRepository<PortLoadingPlanRobTempDetails, Long> {

  public List<PortLoadingPlanRobTempDetails> findByLoadingInformationAndIsActive(
      LoadingInformation loadingInformation, Boolean isActive);

  @Query(
      "FROM PortLoadingPlanRobTempDetails PL INNER JOIN LoadingInformation LI ON PL.loadingInformation = LI.id AND LI.loadablePatternXId = ?1 AND PL.portRotationXId = ?2 AND PL.isActive = ?3")
  public List<PortLoadingPlanRobTempDetails> findByPatternIdAndPortRotationIdAndIsActive(
      Long patternId, Long portRotationId, Boolean isActive);

  @Modifying
  @Transactional
  @Query("UPDATE PortLoadingPlanRobTempDetails SET isActive = false WHERE id = ?1")
  public void deleteById(Long id);

  @Modifying
  @Transactional
  @Query(
      "UPDATE PortLoadingPlanRobTempDetails SET isActive = false WHERE loadingInformation.id = ?1")
  public void deleteByLoadingInformationId(Long loadingInfoId);

  @Transactional
  @Modifying
  @Query(
      "Update PortLoadingPlanRobTempDetails set quantity = ?1, quantityM3 = ?2"
          + " where tankXId = ?3")
  public void updatePortLoadingPlanRobDetailsRepository(
      @Param("quantity") BigDecimal quantity,
      @Param("quantity_m3") BigDecimal quantityM3,
      @Param("tank_xid") Long tankXId);

  @Modifying
  @Query(
      value =
          "insert into PortLoadingPlanRobTempDetails (loading_information_xid, tank_xid, quantity_mt, density,"
              + "colour_code, actual_planned, arrival_departutre) values"
              + " (:loading_information_xid, :tank_xid, :quantity_mt, :density, :colour_code, :actual_planned, :arrival_departutre)",
      nativeQuery = true)
  void insertPortLoadingPlanRobDetails(
      @Param("loading_information_xid") Long loadingInfoId,
      @Param("tank_xid") Long tankXid,
      @Param("quantity_mt") Long quantity_mt,
      @Param("density") Long density,
      @Param("colour_code") String colourCode,
      @Param("actual_planned") Long actual_planned,
      @Param("arrival_departutre") Long arrival_departutre);
}
