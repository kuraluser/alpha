/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadablePatternCargoToppingOffSequence;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadablePatternCargoToppingOffSequenceRepository
    extends CommonCrudRepository<LoadablePatternCargoToppingOffSequence, Long> {

  public List<LoadablePatternCargoToppingOffSequence> findByLoadablePatternAndIsActive(
      LoadablePattern loadablePattern, Boolean isActive);
}
