/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingBerthDetail;
import java.util.List;
import java.util.Optional;

public interface DischargeBerthDetailRepository
    extends CommonCrudRepository<DischargingBerthDetail, Long> {

  List<DischargingBerthDetail> findAllByDischargingInformationIdAndIsActiveTrue(Long var1);

  List<DischargingBerthDetail> findByDischargingInformationIdAndIsActive(Long id, boolean b);

  Optional<DischargingBerthDetail> findByIdAndIsActiveTrue(long id);
}
