/* Licensed at AlphaOri Technologies */
package com.cpdss.cargoinfo.repository;

import com.cpdss.cargoinfo.entity.Cargo;
import com.cpdss.common.springdata.CommonCrudRepository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CargoRepository
    extends CommonCrudRepository<Cargo, Long>, JpaSpecificationExecutor<Cargo> {;

  public List<Cargo> findByIdIn(List<Long> ids);

  public List<Cargo> findByIsActive(Boolean isActive);
}
