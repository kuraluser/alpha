/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.AlgoErrorHeading;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AlgoErrorHeadingRepository
    extends CommonCrudRepository<AlgoErrorHeading, Long>,
        JpaSpecificationExecutor<AlgoErrorHeading> {

  Optional<AlgoErrorHeading> findByErrorHeading(String errorHeading);

  Optional<List<AlgoErrorHeading>> findAllByErrorHeading(String errorHeading);
}
