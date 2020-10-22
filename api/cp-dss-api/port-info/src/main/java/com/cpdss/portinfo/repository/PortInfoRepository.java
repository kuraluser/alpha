/* Licensed under Apache-2.0 */
package com.cpdss.portinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.portinfo.entity.PortInfo;

/** Repository for the portinfo table in port master */
public interface PortInfoRepository extends CommonCrudRepository<PortInfo, Long> {}
