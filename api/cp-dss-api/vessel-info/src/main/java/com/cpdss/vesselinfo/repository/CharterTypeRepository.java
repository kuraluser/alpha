/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.ChartererType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CharterTypeRepository
    extends CommonCrudRepository<ChartererType, Long>, JpaSpecificationExecutor<ChartererType> {

  Optional<ChartererType> findByIdAndIsActiveTrue(Long id);
}
