/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.PortTideDetail;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PortTideDetailsRepository extends CommonCrudRepository<PortTideDetail, Long> {

  List<PortTideDetail> findByDischargingXidAndIsActive(Long dischargingXid, boolean active);

  @Transactional
  @Modifying
  @Query("UPDATE PortTideDetail SET isActive = false WHERE dischargingXid = ?1")
  void updatePortDetailActiveState(Long id);
}
