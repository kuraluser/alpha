/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.CargoNominationPortDetails;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/** Repository interface for cargoNominationOperationDetails entity */
public interface CargoNominationOperationDetailsRepository
    extends CommonCrudRepository<CargoNominationPortDetails, Long> {

  @Transactional
  @Modifying
  @Query("Update CargoNominationPortDetails set isActive = false where cargoNomination.id = ?1 ")
  public void deleteCargoNominationPortDetails(Long cargoNominationId);
}
