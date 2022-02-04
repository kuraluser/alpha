/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.ApiTempHistory;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/** Api-Temp history repository */
public interface ApiTempHistoryRepository
    extends CommonCrudRepository<ApiTempHistory, Long>, JpaSpecificationExecutor<ApiTempHistory> {

  @Query(
      "FROM ApiTempHistory ath WHERE "
          //  		+ "ath.isActive = true AND "
          + "ath.cargoId = ?1 AND ath.year >= ?2 order by ath.loadedDate desc")
  public List<com.cpdss.loadablestudy.entity.ApiTempHistory> findApiTempHistoryWithYearAfter(
      Long cargoId, Integer year);

  public List<ApiTempHistory> findByLoadingPortIdAndCargoIdAndIsActiveOrderByCreatedDateTimeDesc(
      Long loadingPortId, Long cargoId, Boolean isActive);

  @Query(
      value =
          "select cn.id, ah.api, ah.temperature, ah.cargo_xid, ah.load_port_xid from "
              + "public.cargo_nomination cn inner join public.cargo_nomination_operation_details cnod on "
              + "cn.id = cnod.cargo_nomination_xid inner join public.api_history ah on "
              + "cnod.port_xid = ah.load_port_xid and ah.cargo_xid = cn.cargo_xid "
              + "where cn.id in ?1 and ah.is_active = ?2 order by ah.created_date_time desc",
      nativeQuery = true)
  public List<Object[]> findByCargoNominationIdInAndIsActiveOrderByCreatedDateTimeDesc(
      List<Long> cargoNominationIds, Boolean b);

  Page<ApiTempHistory> findAllByLoadedDateBetween(
      Pageable pageable, LocalDateTime fromDate, LocalDateTime toDate);

  public List<ApiTempHistory> findByOrderByCreatedDateTimeDesc();

  public List<ApiTempHistory>
      findByLoadingPortIdAndCargoIdAndLoadedDateNotNullOrderByLoadedDateDesc(
          Long loadingPortId, Long cargoId);
}
