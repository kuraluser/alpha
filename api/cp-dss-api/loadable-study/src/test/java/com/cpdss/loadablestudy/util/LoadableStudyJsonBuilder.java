/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.util;

import com.cpdss.common.utils.ParserUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class LoadableStudyJsonBuilder {

  @Test
  public void index() {
    ParserUtil.parserMain("com.cpdss.loadablestudy.domain");
    log.info("test");
  }
}
