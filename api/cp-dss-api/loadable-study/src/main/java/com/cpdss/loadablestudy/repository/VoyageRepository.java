/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.loadablestudy.entity.Voyage;
import com.cpdss.loadablestudy.entity.VoyageStatus;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** @Author jerin.g */
public interface VoyageRepository
    extends JpaRepository<Voyage, Long>, JpaSpecificationExecutor<Voyage> {

  public List<Voyage> findByCompanyXIdAndVesselXIdAndVoyageNoIgnoreCase(
      Long companyId, Long vesselXId, String voyageNo);

  public List<Voyage> findByVesselXIdAndIsActiveOrderByIdDesc(Long vesselXId, boolean isActive);

  public Voyage findByIdAndIsActive(Long id, boolean isActive);

  /*
   * public Voyage
   * findFirstByVoyageEndDateLessThanAndVesselXIdAndIsActiveAndVoyageStatusOrderByVoyageEndDateDesc(
   * LocalDateTime currentVoyageStartDate, Long vesselId, boolean isActive,
   * VoyageStatus voyageStatus);
   */

  public Voyage findFirstByVesselXIdAndIsActiveAndVoyageStatusOrderByLastModifiedDateDesc(
      Long vesselId, boolean isActive, VoyageStatus voyageStatus);

  @Query(
      "select V from Voyage V WHERE V.isActive = :isActive AND V.vesselXId= :vesselId AND "
          + " Date(V.actualStartDate) >= :from and Date(V.actualStartDate) <= :to ORDER BY V.lastModifiedDate DESC")
  public List<Voyage> findByIsActiveAndVesselXIdAndActualStartDateBetween(
      @Param("isActive") boolean isActive,
      @Param("vesselId") Long vesselXId,
      @Param("from") LocalDate date1,
      @Param("to") LocalDate date2);

  public List<Voyage> findByIsActiveAndVesselXIdOrderByLastModifiedDateTimeDesc(
      boolean isActive, Long vesselXId);

  @Query(
      value =
          "with list1 as (\n"
              + "select * from voyage v \n"
              + "where v.is_active = :isActive \n"
              + "and v.vessel_xid = :vesselXId \n"
              + "and v.voyage_status = 3 \n"
              + "order by v.voyage_status desc, v.created_date_time desc),\n"
              + "list2 as (\n"
              + "select * from voyage v \n"
              + "where v.is_active = :isActive \n"
              + "and v.vessel_xid = :vesselXId\n"
              + "and v.voyage_status notnull\n"
              + "order by v.created_date_time desc)\n"
              + "select * from list1 union all select * from list2;",
      nativeQuery = true)
  public List<Voyage> findByIsActiveAndVesselXIdOrderByVoyageStatusDescAndLastModifiedDateTimeDesc(
      @Param("isActive") boolean isActive, @Param("vesselXId") Long vesselXId);

  @Query("select V from Voyage V WHERE V.voyageStatus.id =?1 AND V.isActive=?2")
  public List<Voyage> findByVoyageStatusAndIsActive(Long voyageId, boolean b);

  @Query(
      "select v from Voyage v WHERE v.voyageStatus.id = ?1 AND v.vesselXId = ?2 AND v.isActive = true ORDER BY v.lastModifiedDate DESC")
  List<Voyage> findActiveVoyagesByVesselId(Long activeStatusId, Long vesselId);

  @Query(
      "select V from Voyage V WHERE V.voyageStatus.id =?1 AND V.vesselXId = ?2 AND V.isActive=?3")
  public List<Voyage> findByVoyageStatusAndVesselIdAndIsActive(
      Long voyageId, Long vesselId, boolean b);
}
