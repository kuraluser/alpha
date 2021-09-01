/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.PortTideDetail;
import com.cpdss.loadingplan.repository.projections.PortTideAlgo;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PortTideDetailsRepository extends CommonCrudRepository<PortTideDetail, Long> {

  List<PortTideDetail> findAllByIsActiveTrue();

  List<PortTideAlgo> findAllByPortXidAndIsActiveTrueOrderByTideDateDescTideTimeDesc(
      Long portId, Pageable page);

  List<PortTideDetail> findByLoadingXidAndIsActive(Long loadingXid, boolean active);

  @Transactional
  @Modifying
  @Query("UPDATE PortTideDetail SET isActive = false WHERE loadingXid = ?1")
  void updatePortDetailActiveState(Long id);
}
