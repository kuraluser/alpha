/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.PortTideDetail;
import com.cpdss.loadingplan.repository.projections.PortTideAlgo;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface PortTideDetailsRepository extends CommonCrudRepository<PortTideDetail, Long> {

  List<PortTideDetail> findAllByIsActiveTrue();

  List<PortTideAlgo> findAllByPortXidAndIsActiveTrueOrderByTideDateDescTideTimeDesc(
      Long portId, Pageable page);
}
