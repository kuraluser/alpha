/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The persistent class for the loading_rules database table. */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "loading_rules")
public class LoadingRule extends EntityDoc {
  private static final long serialVersionUID = 1L;

  @Column(name = "display_in_settings")
  private Boolean displayInSettings;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "is_enable")
  private Boolean isEnable;

  @Column(name = "is_hard_rule")
  private Boolean isHardRule;

  @Column(name = "is_modified")
  private Boolean isModified;

  @Column(name = "loading_xid")
  private Long loadingXid;

  @Column(name = "numeric_precision")
  private Long numericPrecision;

  @Column(name = "numeric_scale")
  private Long numericScale;

  @Column(name = "parent_rule_xid")
  private Long parentRuleXid;

  @Column(name = "ruletype_xid")
  private Long ruleTypeXid;

  private Long version;

  @Column(name = "vessel_rule_xid")
  private Long vesselRuleXid;

  @Column(name = "vessel_xid")
  private Long vesselXid;

  // bi-directional many-to-one association to LoadingRuleInput
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "loadingRule", cascade = CascadeType.ALL)
  private List<LoadingRuleInput> loadingRuleInputs;
}
