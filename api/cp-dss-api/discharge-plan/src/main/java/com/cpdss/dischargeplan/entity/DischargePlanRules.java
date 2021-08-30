/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class for Discharging Study Rules
 *
 * @author vinothkumar.m
 */
@Entity
@Table(name = "discharging_rules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DischargePlanRules extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "discharging_xid", referencedColumnName = "id")
  private DischargeInformation dischargeInformation;

  @Column(name = "vessel_rule_xid")
  private Long vesselRuleXId;

  @Column(name = "vessel_xid")
  private Long vesselXId;

  @Column(name = "ruletype_xid")
  private Long ruleTypeXId;

  @Column(name = "display_in_settings")
  private Boolean displayInSettings;

  @Column(name = "is_enable")
  private Boolean isEnable;

  @Column(name = "is_modified")
  private Boolean isModified;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "is_hard_rule")
  private Boolean isHardRule;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "dischargePlanRules", cascade = CascadeType.ALL)
  private List<DischargePlanRuleInput> dischargePlanRuleInputList;

  @Column(name = "parent_rule_xid")
  private Long parentRuleXId;

  @Column(name = "numeric_precision")
  private Long numericPrecision;

  @Column(name = "numeric_scale")
  private Long numericScale;
}
