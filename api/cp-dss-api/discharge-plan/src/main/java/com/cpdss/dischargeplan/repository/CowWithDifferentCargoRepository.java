/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.CowPlanDetail;
import com.cpdss.dischargeplan.entity.CowWithDifferentCargo;
import java.util.List;

public interface CowWithDifferentCargoRepository
    extends CommonCrudRepository<CowWithDifferentCargo, Long> {

  List<CowWithDifferentCargo> findAllByCowPlanDetail(CowPlanDetail cpd);
}
