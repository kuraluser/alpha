/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.ChartererType;
import java.util.Optional;

public interface CharterTypeRepository extends CommonCrudRepository<ChartererType, Long> {

  Optional<ChartererType> findByIdAndIsActiveTrue(Long id);
}
