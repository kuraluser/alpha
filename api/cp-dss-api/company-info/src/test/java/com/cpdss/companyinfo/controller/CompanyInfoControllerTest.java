/* Licensed under Apache-2.0 */
package com.cpdss.companyinfo.controller;

import static com.cpdss.companyinfo.TestUtils.TEST_DOMAIN;
import static com.cpdss.companyinfo.TestUtils.prepareCompanyInfoResponse;
import static com.cpdss.companyinfo.controller.CompanyInfoController.COMPANY_INFO_URI;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.companyinfo.CompanyInfoTestConfiguration;
import com.cpdss.companyinfo.services.CompanyInfoService;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/** Test class for {@link CompanyInfoController} */
@MockitoSettings
@WebMvcTest(controllers = CompanyInfoController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {CompanyInfoTestConfiguration.class, CompanyInfoController.class})
class CompanyInfoControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private CompanyInfoService companyInfoService;

  /**
   * Test method for positive response scenario
   *
   * @throws Exception
   */
  @Test
  void testGetCompanyByDomain() throws Exception {
    when(this.companyInfoService.findCompanyInfoByDomain(anyString()))
        .thenReturn(prepareCompanyInfoResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(COMPANY_INFO_URI, TEST_DOMAIN)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  /**
   * Test method for service exception scenario
   *
   * @throws Exception
   */
  @Test
  void testGetCompanyByDomainServiceException() throws Exception {
    when(this.companyInfoService.findCompanyInfoByDomain(anyString()))
        .thenThrow(
            new GenericServiceException(
                "Service Exception",
                CommonErrorCodes.E_HTTP_BAD_REQUEST,
                HttpStatusCode.BAD_REQUEST));
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(COMPANY_INFO_URI, TEST_DOMAIN)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }

  /**
   * Test method for RuntimeException scenario
   *
   * @throws Exception
   */
  @Test
  void testGetCompanyByDomainRuntimeException() throws Exception {
    when(this.companyInfoService.findCompanyInfoByDomain(anyString()))
        .thenThrow(RuntimeException.class);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(COMPANY_INFO_URI, TEST_DOMAIN)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }
}
