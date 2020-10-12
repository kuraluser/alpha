/* Licensed under Apache-2.0 */
package com.cpdss.companyinfo.services;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.companyinfo.domain.CompanyInfoResponse;
import com.cpdss.companyinfo.entity.Company;
import com.cpdss.companyinfo.repository.CompanyRepository;
import java.util.Arrays;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/** Service class for company related operations */
@Service
@Log4j2
public class CompanyInfoService {

  @Autowired private CompanyRepository companyRepository;

  /**
   * Find company informations for keycloak authentication This method returns info needed by
   * keycloak, such as the realm and authentication providers for a particular company
   *
   * @param domain - The sub-domain entered by the user
   * @return {@link CompanyInfoResponse} - The response object including realm and authentication
   *     providers
   * @throws CpdssException
   */
  public CompanyInfoResponse findCompanyInfoByDomain(final String domain)
      throws GenericServiceException {
    log.debug("inside findCompanyInfoByDomain, domain: {}", domain);
    Company company = this.companyRepository.findByDomain(domain);
    if (null == company) {
      log.error("Company with domain: {} does not exist", domain);
      throw new GenericServiceException(
          "Company with given domain could not be found",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatus.BAD_REQUEST);
    }
    if (StringUtils.isEmpty(company.getKeycloakIdp()) || StringUtils.isEmpty(company.getRealm())) {
      log.error("Keycloak related info not set for the domain: {}", domain);
      throw new GenericServiceException(
          "Keycloak related info not set",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    CompanyInfoResponse response = new CompanyInfoResponse();
    response.setRealm(company.getRealm());
    response.setProviders(Arrays.asList(company.getKeycloakIdp().split(",")));
    log.debug("Found company by domain, company: {}", response);
    return response;
  }
}
