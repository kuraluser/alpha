/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.Charterer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CharterDetailsRepository
    extends CommonCrudRepository<Charterer, Long>, JpaSpecificationExecutor<Charterer> {

  Optional<Charterer> findByIdAndIsActiveTrue(Long id);
}
