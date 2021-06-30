/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.*;
import lombok.*;

/** @author vinoth kumar M */
@Entity
@Table(name = "rule_drop_down_values")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RuleVesselDropDownValues extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "rule_template_xid")
  private Long ruleTemplateXid;

  @Column(name = "drop_down_values")
  private String dropDownValue;

  @Column(name = "is_active")
  private Boolean isActive;
}
