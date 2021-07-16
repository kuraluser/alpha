/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingSequence;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadingSequenceRepository extends CommonCrudRepository<LoadingSequence, Long> {}
