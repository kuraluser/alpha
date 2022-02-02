/* Licensed at AlphaOri Technologies */
package com.cpdss.common.communication.repository;

import com.cpdss.common.communication.entity.DataTransferInBound;
import com.cpdss.common.springdata.CommonCrudRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DataTransferInBoundRepository
    extends CommonCrudRepository<DataTransferInBound, Long> {

  public Optional<DataTransferInBound> findByProcessId(String processId);

  @Modifying
  @Query(
      "UPDATE DataTransferInBound dataTransferInbound SET dataTransferInbound.status = ?2 where dataTransferInbound.processId = ?1")
  public void updateStatus(String processId, String status);

  public Optional<DataTransferInBound> findByDependantProcessId(String dependantProcessId);
}
