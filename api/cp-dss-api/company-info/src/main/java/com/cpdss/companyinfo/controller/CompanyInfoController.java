/* Licensed at AlphaOri Technologies */
package com.cpdss.companyinfo.controller;

import com.cpdss.common.exception.CommonRestException;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.companyinfo.domain.CompanyInfoResponse;
import com.cpdss.companyinfo.services.CompanyInfoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

/**
 * Company info controller class . This controller will be handling unauthenticated request for
 * company public data
 */
@RestController
@Validated
@Log4j2
public class CompanyInfoController {

  public static final String COMPANY_INFO_URI = "/api/cloud/companies/{domain}/idp-info";
  public static final String SHIP_CAROUSAL_URI = "/api/ship/company/carousals";

  @Autowired private CompanyInfoService companyInfoService;

  /**
   * Api to retrieve public company info like, keycloak realm, identity providers configured
   *
   * @param domain - The subdomain corresponding to the company
   * @return {@link CompanyInfoResponse}
   * @throws CommonRestException
   */
  @GetMapping(
      value = COMPANY_INFO_URI,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public CompanyInfoResponse getCompanyByDomain(
      @PathVariable @NotEmpty(message = CommonErrorCodes.E_HTTP_BAD_REQUEST) final String domain)
      throws CommonRestException {
    try {
      log.debug("inside company info controller, domain:{}", domain);
      return this.companyInfoService.findCompanyInfoByDomain(domain);
    } catch (GenericServiceException ge) {
      throw new CommonRestException(ge.getCode(), null, ge.getStatus(), ge.getMessage(), ge);
    } catch (Exception e) {
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          null,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  @GetMapping(
      value = SHIP_CAROUSAL_URI,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public CompanyInfoResponse getCarousals(
      @RequestParam(required = false, defaultValue = "mol") final String domain)
      throws CommonRestException {
    try {
      log.debug("inside get carousal info controller");
      return this.companyInfoService.findShipCarousals(domain);
    } catch (GenericServiceException ge) {
      throw new CommonRestException(ge.getCode(), null, ge.getStatus(), ge.getMessage(), ge);
    } catch (Exception e) {
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          null,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }
}
