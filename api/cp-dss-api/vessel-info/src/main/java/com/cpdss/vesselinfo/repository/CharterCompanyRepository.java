/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.ChartererCompany;
import java.util.Optional;

public interface CharterCompanyRepository extends CommonCrudRepository<ChartererCompany, Long> {

  Optional<ChartererCompany> findByIdAndIsActiveTrue(Long id);
}
