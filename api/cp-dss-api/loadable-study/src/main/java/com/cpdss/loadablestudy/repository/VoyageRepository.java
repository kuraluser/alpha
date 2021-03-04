/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.loadablestudy.entity.Voyage;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

  public List<Voyage> findByVesselXIdAndIsActiveOrderByLastModifiedDateTimeDesc(
      Long vesselXId, boolean isActive);

  public Voyage findByIdAndIsActive(Long id, boolean isActive);

  public Voyage findFirstByVoyageEndDateLessThanAndVesselXIdAndIsActiveOrderByVoyageEndDateDesc(
      LocalDateTime currentVoyageStartDate, Long vesselId, boolean isActive);

  @Query(
      "select V from Voyage V WHERE V.isActive = :isActive AND V.vesselXId= :vesselId AND "
          + " Date(V.voyageStartDate) >= :from and Date(V.voyageStartDate) <= :to ORDER BY V.lastModifiedDate DESC")
  public List<Voyage> findByIsActiveAndVesselXIdAndActualStartDateBetween(
      @Param("isActive") boolean isActive,
      @Param("vesselId") Long vesselXId,
      @Param("from") LocalDate date1,
      @Param("to") LocalDate date2);

  public List<Voyage> findByIsActiveAndVesselXIdOrderByLastModifiedDateTimeDesc(
      boolean isActive, Long vesselXId);

  @Query("select V from Voyage V WHERE V.voyageStatus.id =?1 AND V.isActive=?2")
  public List<Voyage> findByVoyageStatusAndIsActive(Long voyageId, boolean b);
}
