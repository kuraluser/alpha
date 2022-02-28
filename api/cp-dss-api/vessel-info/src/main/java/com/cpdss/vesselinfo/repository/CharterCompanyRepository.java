/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.ChartererCompany;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CharterCompanyRepository
    extends CommonCrudRepository<ChartererCompany, Long>,
        JpaSpecificationExecutor<ChartererCompany> {

  Optional<ChartererCompany> findByIdAndIsActiveTrue(Long id);
}
