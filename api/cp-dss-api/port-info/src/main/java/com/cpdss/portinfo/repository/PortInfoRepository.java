/* Licensed under Apache-2.0 */
package com.cpdss.portinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.portinfo.entity.PortInfo;
import java.util.List;

/** Repository for the portinfo table in port master */
public interface PortInfoRepository extends CommonCrudRepository<PortInfo, Long> {

  List<PortInfo> findByIdInAndIsActive(List<Long> primaryKey, Boolean isActive);

  List<PortInfo> findAllByOrderByName();
}
