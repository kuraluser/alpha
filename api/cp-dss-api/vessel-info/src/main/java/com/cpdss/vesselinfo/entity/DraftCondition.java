/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Draft condition entity
 *
 * @author suhail.k
 */
@Entity
@Getter
@Setter
@Table(name = "draftcondition")
public class DraftCondition extends EntityDoc {

  @Column(name = "name")
  private String name;

  @Column(name = "isactive")
  private Boolean isActive;
}
