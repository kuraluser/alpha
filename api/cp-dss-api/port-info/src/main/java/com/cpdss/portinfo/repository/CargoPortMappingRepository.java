/* Licensed at AlphaOri Technologies */
package com.cpdss.portinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.portinfo.domain.PortInfo;
import com.cpdss.portinfo.entity.CargoPortMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/** Repository class for CargoPortMapping table */
public interface CargoPortMappingRepository extends CommonCrudRepository<CargoPortMapping, Long> {

  @Query(
      "Select new com.cpdss.portinfo.domain.PortInfo(c.portInfo.id, c.portInfo.name) from CargoPortMapping c where c.cargoXId = ?1")
  List<PortInfo> getPortsInfo(long cargoId);

  public List<CargoPortMapping> findByportInfo_idIn(List<Long> portIds);

  public List<CargoPortMapping> findByCargoXId(long cargoId);

  /**
   * Fetching CargoPortMapping
   *
   * @param cargoId
   * @param portId
   * @param isActive
   * @return cargoPortMapping
   */
  @Query(
      "SELECT C FROM CargoPortMapping C WHERE C.cargoXId = :cargoId AND C.portInfo.id = :portId AND C.isActive = :isActive")
  Optional<CargoPortMapping> findByCargoXIdAndPortIdAndIsActive(
      Long cargoId, Long portId, Boolean isActive);

  /**
   * Deleting cargo port mappings by cargoId
   *
   * @param cargoId
   * @return numberOfRowsUpdated
   */
  @Transactional
  @Modifying
  @Query(
      "UPDATE CargoPortMapping SET isActive = false where cargoXId = :cargoId AND isActive = true")
  Integer deleteAllByCargoXId(Long cargoId);
}
