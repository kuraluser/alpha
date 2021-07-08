/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.*;
import lombok.*;

/** @author vinoth kumar M */
@Entity
@Table(name = "rule_master_section")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RuleMasterSection extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "rule_master_setions")
  private String ruleMasterSection;

  @Column(name = "is_active")
  private Boolean isActive;
}
