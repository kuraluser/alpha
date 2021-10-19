/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadablePatternCargoToppingOffSequence;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LoadablePatternCargoToppingOffSequenceRepository
    extends CommonCrudRepository<LoadablePatternCargoToppingOffSequence, Long> {

  public List<LoadablePatternCargoToppingOffSequence> findByLoadablePatternAndIsActive(
      LoadablePattern loadablePattern, Boolean isActive);

  @Transactional
  @Modifying
  @Query(
      "UPDATE LoadablePatternCargoToppingOffSequence SET isActive = false WHERE loadablePattern.id = ?1")
  public void deleteByLoadablePatternId(Long loadablePatternId);
}
