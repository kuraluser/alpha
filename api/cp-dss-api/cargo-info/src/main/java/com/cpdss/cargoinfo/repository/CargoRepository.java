/* Licensed at AlphaOri Technologies */
package com.cpdss.cargoinfo.repository;

import com.cpdss.cargoinfo.entity.Cargo;
import com.cpdss.common.springdata.CommonCrudRepository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CargoRepository
    extends CommonCrudRepository<Cargo, Long>, JpaSpecificationExecutor<Cargo> {;

  public List<Cargo> findByIdIn(List<Long> ids);

  public List<Cargo> findByIsActive(Boolean isActive);

  /**
   * Deleting cargo by cargo id
   *
   * @param cargoId
   * @return numberOfRowsUpdated
   */
  @Transactional
  @Modifying
  @Query("UPDATE Cargo SET isActive = false where id = :id")
  Integer deleteByCargoId(@Param("id") Long cargoId);
}
