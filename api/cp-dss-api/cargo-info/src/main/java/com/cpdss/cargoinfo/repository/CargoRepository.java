/* Licensed at AlphaOri Technologies */
package com.cpdss.cargoinfo.repository;

import com.cpdss.cargoinfo.entity.Cargo;
import com.cpdss.common.springdata.CommonCrudRepository;
import java.util.List;

public interface CargoRepository extends CommonCrudRepository<Cargo, Long> {;

  public List<Cargo> findByIdIn(List<Long> ids);

  public List<Cargo> findByIsActive(Boolean isActive);
}
