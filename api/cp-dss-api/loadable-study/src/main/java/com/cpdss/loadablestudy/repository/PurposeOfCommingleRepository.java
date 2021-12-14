/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.PurposeOfCommingle;

public interface PurposeOfCommingleRepository
    extends CommonCrudRepository<PurposeOfCommingle, Long> {

  Iterable<PurposeOfCommingle> findByIsActive(Boolean isActive);
}
