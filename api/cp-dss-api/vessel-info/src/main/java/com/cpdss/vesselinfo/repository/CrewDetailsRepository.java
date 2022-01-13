/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.CrewDetails;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CrewDetailsRepository
    extends CommonCrudRepository<CrewDetails, Long>, JpaSpecificationExecutor<CrewDetails> {

  Optional<CrewDetails> findByIdAndIsActiveTrue(Long id);
}
