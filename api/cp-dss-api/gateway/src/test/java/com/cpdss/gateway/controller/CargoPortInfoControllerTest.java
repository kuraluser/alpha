/* Licensed under Apache-2.0 */
package com.cpdss.gateway.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.gateway.GatewayTestConfiguration;
import com.cpdss.gateway.domain.CargosResponse;
import com.cpdss.gateway.domain.TimezoneRestResponse;
import com.cpdss.gateway.service.CargoPortInfoService;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CargoPortInfoController.class)
@ContextConfiguration(classes = {GatewayTestConfiguration.class})
@AutoConfigureMockMvc(addFilters = false)
public class CargoPortInfoControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private CargoPortInfoService cargoPortInfoService;

  @MockBean private CargosResponse cargosResponse;

  private static final String AUTHORIZATION_HEADER = "Authorization";

  @Test
  void getCargosTest() throws Exception {
    when(cargoPortInfoService.getCargos(Mockito.any())).thenReturn(cargosResponse);
    this.mockMvc.perform(get("/api/cloud/cargos")).andExpect(status().isOk());
  }

  @Test
  public void fetchTimezoneTest() throws Exception {
    TimezoneRestResponse response = new TimezoneRestResponse();
    response.setTimezones(new ArrayList<>());
    response.setResponseStatus(new CommonSuccessResponse("200", ""));
    when(cargoPortInfoService.getTimezones()).thenReturn(response);
    this.mockMvc
        .perform(
            get("/api/ship/global-timezones")
                .header(AUTHORIZATION_HEADER, "4b5608ff-b77b-40c6-9645-d69856d4aafa"))
        .andExpect(status().isOk());
  }
}
