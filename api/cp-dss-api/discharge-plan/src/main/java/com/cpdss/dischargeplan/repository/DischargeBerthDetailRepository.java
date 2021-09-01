/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingBerthDetail;
import java.util.List;

public interface DischargeBerthDetailRepository
    extends CommonCrudRepository<DischargingBerthDetail, Long> {

  List<DischargingBerthDetail> findAllByDischargingInformationIdAndIsActiveTrue(Long var1);
}
