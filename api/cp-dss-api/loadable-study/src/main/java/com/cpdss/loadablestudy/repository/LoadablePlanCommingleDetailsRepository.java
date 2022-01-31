/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.domain.CommingleDetails;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadablePlanCommingleDetails;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/** @Author jerin.g */
public interface LoadablePlanCommingleDetailsRepository
    extends CommonCrudRepository<LoadablePlanCommingleDetails, Long> {

  public List<LoadablePlanCommingleDetails> findByLoadablePatternAndIsActive(
      LoadablePattern loadablePattern, Boolean isActive);

  public Optional<LoadablePlanCommingleDetails> findByIdAndIsActive(Long id, Boolean isActive);

  @Transactional
  @Modifying
  @Query("UPDATE LoadablePlanCommingleDetails SET isActive = ?1 WHERE loadablePattern.id = ?2")
  public void deleteLoadablePlanCommingleDetails(Boolean isActive, Long loadablePatternId);

  /**
   * Method to fetch CommingleDetails using loadablePatternId
   *
   * @param loadablePatternId Loadable pattern Id
   * @param isActive isActive boolean
   * @return List of CommingleDetails objects
   */
  @Query(
      "SELECT new com.cpdss.loadablestudy.domain.CommingleDetails(lpcd.grade, lpcd.tankName, "
          + "lpcd.quantity, lpcd.api, lpcd.temperature, lpcd.cargo1Abbreviation, "
          + "lpcd.cargo2Abbreviation, lpcd.cargo1Percentage, lpcd.cargo2Percentage, "
          + "lpcd.cargo1Mt, lpcd.cargo2Mt, lpcd.commingleColour) FROM LoadablePlanCommingleDetails lpcd WHERE "
          + "lpcd.loadablePattern.id = :loadablePatternId AND lpcd.isActive = :isActive")
  List<CommingleDetails> findByLoadablePatternIdAndIsActive(
      long loadablePatternId, boolean isActive);
}
