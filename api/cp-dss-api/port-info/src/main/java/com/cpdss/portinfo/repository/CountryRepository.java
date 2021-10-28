package com.cpdss.portinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.portinfo.entity.Country;

public interface CountryRepository extends CommonCrudRepository<Country, Long> {
	
	  String FIND_ID_AND_NAME = "SELECT cy.id, cy.name from Country cy";

	  @Query(value = FIND_ID_AND_NAME)
	  List<Object[]> findCountryIdAndNames();

}
