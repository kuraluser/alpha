/* Licensed at AlphaOri Technologies */
package com.cpdss.common.redis;

import com.cpdss.common.utils.Doc;

/**
 * Abstract class for redis documents
 *
 * @author r.krishnakumar
 */
public abstract class RDoc implements Doc {

  private String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
