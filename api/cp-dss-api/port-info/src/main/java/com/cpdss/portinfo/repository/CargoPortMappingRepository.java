/* Licensed under Apache-2.0 */
package com.cpdss.portinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.portinfo.domain.PortInfo;
import com.cpdss.portinfo.entity.CargoPortMapping;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

/** Repository class for CargoPortMapping table */
public interface CargoPortMappingRepository extends CommonCrudRepository<CargoPortMapping, Long> {

  @Query(
      "Select new com.cpdss.portinfo.domain.PortInfo(c.portInfo.id, c.portInfo.name) from CargoPortMapping c where c.cargoXId = ?1")
  List<PortInfo> getPortsInfo(long cargoId);
}
