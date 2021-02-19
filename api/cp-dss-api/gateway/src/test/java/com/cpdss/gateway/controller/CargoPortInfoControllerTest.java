/* Licensed under Apache-2.0 */
package com.cpdss.gateway.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cpdss.gateway.GatewayTestConfiguration;
import com.cpdss.gateway.domain.CargosResponse;
import com.cpdss.gateway.service.CargoPortInfoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CargoPortInfoController.class)
@ContextConfiguration(classes = {GatewayTestConfiguration.class})
public class CargoPortInfoControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private CargoPortInfoService cargoPortInfoService;

  @MockBean private CargosResponse cargosResponse;

  @Test
  void getCargosTest() throws Exception {
    when(cargoPortInfoService.getCargos(Mockito.any())).thenReturn(cargosResponse);
    this.mockMvc.perform(get("/api/cloud/cargos")).andExpect(status().isOk());
  }
}
