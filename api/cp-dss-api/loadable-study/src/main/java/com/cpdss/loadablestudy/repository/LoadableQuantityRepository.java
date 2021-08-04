/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadableQuantity;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/** @Author jerin.g */
public interface LoadableQuantityRepository extends CommonCrudRepository<LoadableQuantity, Long> {
  @Query("FROM LoadableQuantity LQ WHERE LQ.loadableStudyXId.id = ?1 and isActive = ?2")
  public List<LoadableQuantity> findByLoadableStudyXIdAndIsActive(
      Long loadableStudyXId, Boolean isActive);

  @Query("FROM LoadableQuantity LQ WHERE LQ.id = ?1 and isActive = ?2")
  public LoadableQuantity findByIdAndIsActive(Long id, Boolean isActive);

  @Query(
      "FROM LoadableQuantity LQ WHERE LQ.loadableStudyXId.id = ?1 and LQ.loadableStudyPortRotation.id = ?2 and isActive = ?3")
  Optional<LoadableQuantity> findByLSIdAndPortRotationId(
      Long loadableStudyId, Long portRotationId, Boolean isActive);

  @Query(
      "FROM LoadableQuantity lq WHERE lq.loadableStudyXId.id = ?1 and isActive = true ORDER BY lq.lastModifiedDateTime DESC")
  List<LoadableQuantity> findByLoadableStudyOrderByUpdateTime(Long var1);

  Optional<LoadableQuantity> findFirstByLoadableStudyXIdOrderByLastModifiedDateTimeDesc(
      LoadableStudy var1);

  @Modifying
  @Query("Update LoadableQuantity set isActive = false where loadableStudyPortRotation.id = ?1")
  public void deleteByPortRotationId(Long id);
}
