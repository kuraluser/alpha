/* Licensed at AlphaOri Technologies */
package com.cpdss.common.communication.repository;

import com.cpdss.common.communication.entity.DataTransferStage;
import com.cpdss.common.springdata.CommonCrudRepository;

import java.time.LocalDateTime;
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

  @Query("select staging from DataTransferStage staging where staging.status =?1")
  public List<DataTransferStage> getAllWithStatus(String status);

  @Modifying
  @Query("UPDATE DataTransferStage staging SET staging.status = ?2 where staging.processId = ?1")
  public void updateStatusForProcessId(String processId, String status);

  @Query("select staging from DataTransferStage staging where staging.status =?1 and staging.lastModifiedDateTime=?2")
  public List<DataTransferStage> getAllWithStatusAndTime(String status, LocalDateTime dateTime);

  @Modifying
  @Query("UPDATE DataTransferStage staging SET staging.status = ?2, staging.lastModifiedDateTime=?3 where staging.processId = ?1")
   public void updateStatusAndModifiedDateTimeForProcessId(String processId, String status, LocalDateTime modifiedDateTime);

  @Modifying
  @Query("UPDATE DataTransferStage staging SET staging.status = ?2, staging.statusDescription =?3, staging.lastModifiedDateTime=?4 where staging.id = ?1")
  public  void updateStatusAndStatusDescriptionForId(Long id, String status, String statusDescription, LocalDateTime modifiedDateTime);
}
