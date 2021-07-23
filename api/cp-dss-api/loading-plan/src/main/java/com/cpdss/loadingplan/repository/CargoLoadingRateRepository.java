/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.CargoLoadingRate;
import com.cpdss.loadingplan.entity.LoadingSequence;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoLoadingRateRepository extends CommonCrudRepository<CargoLoadingRate, Long> {

  List<CargoLoadingRate> findByLoadingSequenceAndIsActiveTrueOrderById(
      LoadingSequence loadingSequence);
}
