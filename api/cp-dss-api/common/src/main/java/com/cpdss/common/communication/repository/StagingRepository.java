/* Licensed at AlphaOri Technologies */
package com.cpdss.common.communication.repository;

import com.cpdss.common.communication.entity.DataTransferStage;
import com.cpdss.common.springdata.CommonCrudRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository interface for DataTransferStage entity
 *
 * @author Selvy Thomas
 */
@NoRepositoryBean
public interface StagingRepository extends CommonCrudRepository<DataTransferStage, Long> {

  public List<DataTransferStage> findByProcessId(String processId);

  @Transactional
  @Modifying
  @Query(
      "UPDATE DataTransferStage staging SET staging.status = ?2 where staging.id = ?1 and staging.status != ?2")
  public int updateStatus(Long id, String status);
}
