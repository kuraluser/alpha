/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.LoadingInformationStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LoadingInformationRepository
    extends CommonCrudRepository<LoadingInformation, Long> {

  public List<LoadingInformation> findByVesselXIdAndLoadablePatternXIdNotAndIsActive(
      Long vesselXId, Long loadablePatternXId, Boolean isActive);

  public List<LoadingInformation> findByVesselXIdAndVoyageIdAndIsActive(
      Long vesselXId, Long voyageId, Boolean isActive);

  List<LoadingInformation> findAllByVesselXIdAndVoyageIdAndIsActiveTrue(Long id1, Long id2);

  @Transactional
  @Modifying
  @Query("UPDATE LoadingInformation SET isActive = false WHERE id = ?1")
  public void deleteByLoadingInformationId(Long id);

  Optional<LoadingInformation> findByIdAndVesselXIdAndIsActiveTrue(Long id, Long vesselId);

  Optional<LoadingInformation> findByVesselXIdAndLoadablePatternXIdAndIsActiveTrue(
      Long vesselId, Long patternId);

  Optional<LoadingInformation> findByIdAndIsActiveTrue(Long id);

  Optional<LoadingInformation> findByVesselXIdAndVoyageIdAndPortRotationXIdAndIsActiveTrue(
      Long var1, Long var2, Long var3);

  Optional<LoadingInformation>
      findByVesselXIdAndLoadablePatternXIdAndPortRotationXIdAndIsActiveTrue(
          Long vesselId, Long loadablePatternId, Long portRotationId);

  @Transactional
  @Modifying
  @Query("UPDATE LoadingInformation SET loadingInformationStatus = ?1 WHERE id = ?2")
  public void updateLoadingInformationStatus(
      LoadingInformationStatus loadingInformationStatus, Long id);

  @Transactional
  @Modifying
  @Query("UPDATE LoadingInformation li SET li.isLoadingInfoComplete = ?2 WHERE id = ?1")
  void updateLoadingInformationCompleteStatus(Long id, boolean status);

  @Transactional
  @Modifying
  @Query(
      "UPDATE LoadingInformation SET loadingInformationStatus = ?1, arrivalStatus = ?2, departureStatus = ?3 WHERE id = ?4")
  public void updateLoadingInformationStatuses(
      LoadingInformationStatus loadingInformationStatus,
      LoadingInformationStatus arrivalStatus,
      LoadingInformationStatus departureStatus,
      Long id);

  @Transactional
  @Modifying
  @Query("UPDATE LoadingInformation li SET li.isLoadingInstructionsComplete = ?2 WHERE id = ?1")
  void updateIsLoadingInstructionsCompletedStatus(Long id, boolean status);

  @Transactional
  @Modifying
  @Query("UPDATE LoadingInformation li SET li.isLoadingSequenceGenerated = ?2 WHERE id = ?1")
  void updateIsLoadingSequenceGeneratedStatus(Long id, boolean status);

  @Transactional
  @Modifying
  @Query("UPDATE LoadingInformation li SET li.isLoadingPlanGenerated = ?2 WHERE id = ?1")
  void updateIsLoadingPlanGeneratedStatus(Long id, boolean status);

  @Transactional
  @Modifying
  @Query("UPDATE LoadingInformation SET arrivalStatus = ?1 WHERE id = ?2")
  public void updateLoadingInformationArrivalStatus(
      LoadingInformationStatus arrivalStatus, Long id);

  @Transactional
  @Modifying
  @Query("UPDATE LoadingInformation SET departureStatus = ?1 WHERE id = ?2")
  public void updateLoadingInformationDepartureStatus(LoadingInformationStatus depStatus, Long id);

  @Transactional
  @Modifying
  @Query("UPDATE LoadingInformation SET loadingPlanDetailsFromAlgo = ?2 WHERE id = ?1")
  public void updateLoadingPlanDetailsFromAlgo(Long id, String loadingPlanDetailsFromAlgo);

  @Transactional
  @Modifying
  @Query(
      "UPDATE LoadingInformation SET loadingInformationStatus = ?1, isLoadingSequenceGenerated=?2, isLoadingPlanGenerated=?3 WHERE id = ?4")
  void updateLoadingInfoWithInfoStatus(
      LoadingInformationStatus loadingInformationStatus,
      boolean sequenceGenerated,
      boolean planGenerated,
      Long id);

  List<LoadingInformation> findByLoadablePatternXId(Long patternId);

  @Query(
      value =
          "SELECT LI.id FROM loading_information LI WHERE LI.port_rotation_xid = ?1 and LI.is_active = true",
      nativeQuery = true)
  public Long findByPortRotationXIdAndIsActiveTrue(long portRotationId);

  public List<LoadingInformation> findByVesselXIdAndLoadablePatternXIdAndIsActive(
      Long vesselId, Long loadablePatternId, Boolean isActive);
}
