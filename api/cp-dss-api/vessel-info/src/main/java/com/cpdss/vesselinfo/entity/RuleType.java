/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.*;
import lombok.*;

/** @author vinoth kumar M */
@Entity
@Table(name = "rule_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RuleType extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "rule_type")
  private String ruleType;

  @Column(name = "is_active")
  private Boolean isActive;
}
