/* Licensed at AlphaOri Technologies */
package com.cpdss.portinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.portinfo.entity.PortInfo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

/** Repository for the portinfo table in port master */
public interface PortInfoRepository extends CommonCrudRepository<PortInfo, Long> {

  List<PortInfo> findByIdInAndIsActive(List<Long> primaryKey, Boolean isActive);

  List<PortInfo> findAllByOrderByName();

  String FIND_ID_AND_NAME = "select pi.id, pi.name from port_info pi";

  @Query(value = FIND_ID_AND_NAME, nativeQuery = true)
  List<Object[]> findPortsIdAndNames();

  Optional<PortInfo> findByIdAndIsActiveTrue(Long id);
}
