/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class for Loadable Study Rules
 *
 * @author vinothkumar.m
 */
@Entity
@Table(name = "loadable_study_rules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadableStudyRules extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "loadable_study_xid", referencedColumnName = "id")
  private LoadableStudy loadableStudy;

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

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "loadableStudyRuleXId", cascade = CascadeType.ALL)
  private List<LoadableStudyRuleInput> loadableStudyRuleInputs;

  @Column(name = "parent_rule_xid")
  private Long parentRuleXId;
}
