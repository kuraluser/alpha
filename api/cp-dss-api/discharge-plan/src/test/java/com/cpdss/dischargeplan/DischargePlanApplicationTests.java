/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan;

import com.cpdss.common.jsonbuilder.ParserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DischargePlanApplicationTests {

  @Test
  void contextLoads() {
    String packageName = "com.cpdss.dischargeplan.domain";
    ParserUtil.parserMain(packageName);
  }
}
