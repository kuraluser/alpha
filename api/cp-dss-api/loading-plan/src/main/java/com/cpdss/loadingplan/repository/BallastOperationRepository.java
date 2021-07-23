/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.BallastOperation;
import com.cpdss.loadingplan.entity.LoadingSequence;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface BallastOperationRepository extends CommonCrudRepository<BallastOperation, Long> {

  List<BallastOperation> findByLoadingSequenceAndIsActiveTrueOrderById(
      LoadingSequence loadingSequence);
}
