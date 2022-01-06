/* Licensed at AlphaOri Technologies */
package com.cpdss.companyinfo.service;

import static com.cpdss.companyinfo.TestUtils.IDP;
import static com.cpdss.companyinfo.TestUtils.INVALID_ERROR_CODE;
import static com.cpdss.companyinfo.TestUtils.INVALID_HTTP_STATUS;
import static com.cpdss.companyinfo.TestUtils.REALM;
import static com.cpdss.companyinfo.TestUtils.TEST_DOMAIN;
import static com.cpdss.companyinfo.TestUtils.prepareCompanyEntity;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.companyinfo.domain.CompanyInfoResponse;
import com.cpdss.companyinfo.entity.Carousals;
import com.cpdss.companyinfo.entity.Company;
import com.cpdss.companyinfo.repository.CompanyRepository;
import com.cpdss.companyinfo.services.CompanyInfoService;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

/** Test class for {@link CompanyInfoService} */
@SpringJUnitConfig(classes = {CompanyInfoService.class})
class CompanyInfoServiceTest {

  @Autowired private CompanyInfoService companyInfoService;

  @MockBean private CompanyRepository companyRepository;

  /**
   * Test method for positive scenario
   *
   * @throws GenericServiceException
   */
  @Test
  void testFindCompanyInfoByDomain() throws GenericServiceException {
    CompanyInfoService spyService = spy(CompanyInfoService.class);
    when(this.companyRepository.findByDomain(anyString())).thenReturn(prepareCompanyEntity());
    when(spyService.isShore()).thenReturn(true);
    ReflectionTestUtils.setField(spyService, "companyRepository", companyRepository);
    CompanyInfoResponse response = spyService.findCompanyInfoByDomain(TEST_DOMAIN);
    assertNotNull(response);
    assertNotNull(response.getRealm());
    assertNotNull(response.getProviders());
  }

  /**
   * Test method for testing invalid domain scenario
   *
   * @throws GenericServiceException
   */
  @Test
  void testFindCompanyInfoByDomainEmptyData() throws GenericServiceException {
    when(this.companyRepository.findByDomain(anyString())).thenReturn(null);
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () -> this.companyInfoService.findCompanyInfoByDomain(TEST_DOMAIN));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), INVALID_ERROR_CODE),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), INVALID_HTTP_STATUS));
  }

  /**
   * Test for invalid data in database
   *
   * @param property - for repeating the test with invalid realm and invalid IDP
   * @throws GenericServiceException
   */
  @ParameterizedTest
  @ValueSource(strings = {REALM, IDP})
  void testFindCompanyInfoByDomainEmptyIdpSettings(final String property)
      throws GenericServiceException {
    Company entity = prepareCompanyEntity();
    if (REALM.contentEquals(property)) {
      entity.setRealm(null);
    } else {
      entity.setKeycloakIdp(null);
    }
    when(this.companyRepository.findByDomain(anyString())).thenReturn(entity);
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () -> this.companyInfoService.findCompanyInfoByDomain(TEST_DOMAIN));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_GEN_INTERNAL_ERR, ex.getCode(), INVALID_ERROR_CODE),
        () ->
            assertEquals(
                HttpStatusCode.INTERNAL_SERVER_ERROR, ex.getStatus(), INVALID_HTTP_STATUS));
  }

  @Test
  void testFindShipCarousals() throws GenericServiceException {
    when(this.companyRepository.findAll()).thenReturn(getCompanyList());
    when(this.companyRepository.findByIsActiveAndDomain(anyBoolean(), anyString()))
        .thenReturn(Optional.of(getCompanyList().get(0)));
    var result = companyInfoService.findShipCarousals("1");
    assertEquals("1", result.getLogo());
  }

  @Test
  void testFindShipCarousalsWithEmptyData() {
    when(this.companyRepository.findAll()).thenReturn(null);
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class, () -> this.companyInfoService.findShipCarousals("1"));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), INVALID_ERROR_CODE),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), INVALID_HTTP_STATUS));
  }

  @Test
  void testIsShore() {
    var result = companyInfoService.isShore();
    assertEquals(false, result);
  }

  private List<Company> getCompanyList() {
    List<Company> companyList = new ArrayList<>();
    Company company = new Company();
    company.setCompanyLogo("1");
    company.setCompanyFavicon("1");
    company.setRealm("1");
    company.setKeycloakIdp("1");
    company.setDocumentationSiteUrl("1");
    company.setSimulatorSiteUrl("1");
    Set<Carousals> carousals = new HashSet<>();
    Carousals ca = new Carousals();
    ca.setFilePath("1");
    carousals.add(ca);
    company.setCarousals(carousals);
    companyList.add(company);
    return companyList;
  }
}
