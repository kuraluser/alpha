/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.loadablestudy.entity.VoyageStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoyageStatusRepository extends JpaRepository<VoyageStatus, Long> {

  Optional<VoyageStatus> findByIdAndIsActive(Long confirmedVoyageStatus, boolean b);
}
