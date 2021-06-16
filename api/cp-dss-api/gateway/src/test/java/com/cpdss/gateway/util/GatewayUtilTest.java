/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.util;

import com.cpdss.common.jsonbuilder.ParserUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class GatewayUtilTest {

  @Test
  public void index() {

    ParserUtil.parserMain("com.cpdss.gateway.domain.rule");
    log.info("test");
  }
}
