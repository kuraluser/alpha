/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class for Discharge Study Rule Input
 *
 * @author vinothkumar.m
 */
@Entity
@Table(name = "discharging_rule_input")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DischargeStudyRuleInput extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "discharging_rule_xid", referencedColumnName = "id")
  private DischargeStudyRules dischargeStudyRules;

  @Column(name = "prefix")
  private String prefix;

  @Column(name = "default_value")
  private String defaultValue;

  @Column(name = "type_value")
  private String typeValue;

  @Column(name = "max_value")
  private String maxValue;

  @Column(name = "min_value")
  private String minValue;

  @Column(name = "suffix")
  private String suffix;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "is_mandatory")
  private Boolean isMandatory;
}
