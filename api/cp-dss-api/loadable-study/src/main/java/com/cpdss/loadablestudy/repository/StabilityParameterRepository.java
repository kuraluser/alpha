package com.cpdss.loadablestudy.repository;

import java.util.List;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.StabilityParameters;

/**
 * @Author jerin.g
 *
 */
public interface StabilityParameterRepository extends CommonCrudRepository<StabilityParameters, Long>{
public List<StabilityParameters> findByLoadablePatternAndIsActive(LoadablePattern loadablePattern, Boolean isActive);
}
