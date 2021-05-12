/* Licensed at AlphaOri Technologies */
package com.cpdss.task.manager.repository;

import com.cpdss.task.manager.entity.SchedulerJobInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** SchedulerRepository Interface for SchedulerJobInfo model */
@Repository
public interface SchedulerRepository extends JpaRepository<SchedulerJobInfo, Long> {

  Optional<SchedulerJobInfo> findByTaskName(String taskName);
}
