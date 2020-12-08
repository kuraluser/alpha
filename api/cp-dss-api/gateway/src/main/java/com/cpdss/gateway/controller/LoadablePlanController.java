/* Licensed under Apache-2.0 */
package com.cpdss.gateway.controller;

import com.cpdss.gateway.service.LoadablePlanService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** @Author jerin.g */
@Log4j2
@Validated
@RestController
@RequestMapping({"/api/cloud", "/api/ship"})
public class LoadablePlanController {
  private static final String CORRELATION_ID_HEADER = "correlationId";

  @Autowired private LoadablePlanService loadablePlanService;
}
