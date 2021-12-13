/* Licensed at AlphaOri Technologies */
package com.cpdss.companyinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.companyinfo.entity.Company;
import java.util.Optional;

/**
 * Repository interface for {@link Company}
 *
 * @author suhail.k
 */
public interface CompanyRepository extends CommonCrudRepository<Company, Long> {

  /**
   * Find a company by domain
   *
   * @param domain - The domain of company
   * @return {@link Company}
   */
  public Company findByDomain(String domain);

  /**
   * Find active company based on domain
   *
   * @param isActive
   * @param domain
   * @return {@link Optional<Company>}
   */
  public Optional<Company> findByIsActiveAndDomain(Boolean isActive, String domain);
}
