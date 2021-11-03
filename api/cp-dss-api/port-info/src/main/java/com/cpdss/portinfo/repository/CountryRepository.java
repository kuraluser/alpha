/* Licensed at AlphaOri Technologies */
package com.cpdss.portinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.portinfo.entity.Country;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface CountryRepository extends CommonCrudRepository<Country, Long> {

  String FIND_ID_AND_NAME = "SELECT cy.id, cy.name from Country cy";

  @Query(value = FIND_ID_AND_NAME)
  List<Object[]> findCountryIdAndNames();
}
