/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.CrewDetails;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CrewDetailsRepository
    extends CommonCrudRepository<CrewDetails, Long>, JpaSpecificationExecutor<CrewDetails> {}
