/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.AlgoErrors;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AlgoErrorsRepository
    extends CommonCrudRepository<AlgoErrors, Long>, JpaSpecificationExecutor<AlgoErrors> {}
