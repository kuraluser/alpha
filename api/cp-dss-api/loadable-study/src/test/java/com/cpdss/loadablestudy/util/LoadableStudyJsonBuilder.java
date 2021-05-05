/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.util;

import com.cpdss.common.jsonbuilder.ParserUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class LoadableStudyJsonBuilder {

  @Test
  public void index() {
    ParserUtil.parserMain("com.cpdss.loadablestudy.entity");
    log.info("test");
  }
}
