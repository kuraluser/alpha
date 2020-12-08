/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.OnHandQuantity;
import java.util.List;

/**
 * On hand quantity repository
 *
 * @author suhail.k
 */
public interface OnHandQuantityRepository extends CommonCrudRepository<OnHandQuantity, Long> {

  /**
   * Find all on hand quantity entities based on loadable study and port
   *
   * @param loadableStudy
   * @param portXId
   * @return
   */
  public List<OnHandQuantity> findByLoadableStudyAndPortXIdAndIsActive(
      LoadableStudy loadableStudy, Long portXId, boolean isActive);

  /**
   * Find entity by id
   *
   * @param id
   * @param isActive
   * @return
   */
  public OnHandQuantity findByIdAndIsActive(Long id, boolean isActive);

  /**
   * @param loadableStudy
   * @param isActive
   * @return List<OnHandQuantity>
   */
  public List<OnHandQuantity> findByLoadableStudyAndIsActive(
      LoadableStudy loadableStudy, Boolean isActive);
}
