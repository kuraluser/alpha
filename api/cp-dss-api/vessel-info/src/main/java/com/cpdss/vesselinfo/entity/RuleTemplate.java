/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.*;
import lombok.*;

/** @author vinoth kumar M */
@Entity
@Table(name = "rule_template")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RuleTemplate extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "rule_list_master_xid", referencedColumnName = "id")
  private RuleListMaster ruleListMaster;

  @ManyToOne
  @JoinColumn(name = "ruletype_xid", referencedColumnName = "id")
  private RuleType ruleType;

  @Column(name = "display_in_settings")
  private Boolean displayInSettings;

  @Column(name = "is_enable")
  private Boolean isEnable;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "is_hard_rule")
  private Boolean isHardRule;

  @Column(name = "numeric_precision")
  private Long numericPrecision;

  @Column(name = "numeric_scale")
  private Long numericScale;

  @Column(name = "display_order")
  private Long displayOrder;
}
