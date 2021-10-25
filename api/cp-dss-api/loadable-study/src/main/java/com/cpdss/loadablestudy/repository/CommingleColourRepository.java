/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.CommingleColour;
import java.util.Optional;

public interface CommingleColourRepository extends CommonCrudRepository<CommingleColour, Long> {

  public Optional<CommingleColour> findByAbbreviationAndIsActive(
      String abbreviation, Boolean isActive);
}
