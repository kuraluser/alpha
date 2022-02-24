/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.PortWiseTimeRequiredForLoading;
import java.util.List;
import org.springframework.stereotype.Repository;

/** @author pranav.k */
@Repository
public interface PortWiseTimeRequiredForLoadingRepository
    extends CommonCrudRepository<PortWiseTimeRequiredForLoading, Long> {

  List<PortWiseTimeRequiredForLoading> findByLoadablePatternXIdAndIsActiveTrue(
      Long loadablePatternId);
}
