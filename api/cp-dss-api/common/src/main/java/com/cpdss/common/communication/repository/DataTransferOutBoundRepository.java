/* Licensed at AlphaOri Technologies */
package com.cpdss.common.communication.repository;

import com.cpdss.common.communication.entity.DataTransferOutBound;
import com.cpdss.common.springdata.CommonCrudRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface DataTransferOutBoundRepository
    extends CommonCrudRepository<DataTransferOutBound, Long> {

  public Optional<DataTransferOutBound> findByReferenceAndReferenceId(
      String reference, Long referenceId);
}
