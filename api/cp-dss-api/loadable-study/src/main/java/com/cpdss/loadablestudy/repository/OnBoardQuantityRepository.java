/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.OnBoardQuantity;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

/** @Author jerin.g */
public interface OnBoardQuantityRepository extends CommonCrudRepository<OnBoardQuantity, Long> {
  /**
   * @param loadableStudy
   * @param isActive
   * @return List<OnBoardQuantity>
   */
  public List<OnBoardQuantity> findByLoadableStudyAndIsActive(
      LoadableStudy loadableStudy, Boolean isActive);

  /**
   * @param id
   * @param isActive
   * @return
   */
  public OnBoardQuantity findByIdAndIsActive(Long id, Boolean isActive);

  /**
   * @param loadableStudy
   * @param portId
   * @param isActive
   * @return
   */
  public List<OnBoardQuantity> findByLoadableStudyAndPortIdAndIsActive(
      LoadableStudy loadableStudy, Long portId, Boolean isActive);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM on_board_quantity u where loadable_study_xid IN (SELECT loadablestudy_xid FROM loadable_pattern where id=?1)",
      nativeQuery = true)
  String getOnBoardQuantityWithPatternId(long id);
}
