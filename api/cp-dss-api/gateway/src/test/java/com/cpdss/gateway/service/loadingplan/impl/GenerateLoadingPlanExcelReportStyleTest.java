/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan.impl;

import com.cpdss.gateway.service.loadingplan.GenerateLoadingPlanExcelReportStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {GenerateLoadingPlanExcelReportStyle.class})
public class GenerateLoadingPlanExcelReportStyleTest {

  @Autowired GenerateLoadingPlanExcelReportStyle generateLoadingPlanExcelReportStyle;
}
