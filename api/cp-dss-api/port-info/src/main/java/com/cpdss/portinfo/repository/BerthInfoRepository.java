/* Licensed at AlphaOri Technologies */
package com.cpdss.portinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.portinfo.entity.BerthInfo;
import com.cpdss.portinfo.entity.PortInfo;
import java.util.List;

public interface BerthInfoRepository extends CommonCrudRepository<BerthInfo, Long> {

  List<BerthInfo> findAllByPortInfoAndIsActiveTrue(PortInfo portInfo);
}
