/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The persistent class for the loading_rule_input database table. */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "loading_rule_input")
public class LoadingRuleInput extends EntityDoc {
  private static final long serialVersionUID = 1L;

  @Column(name = "default_value")
  private String defaultValue;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "is_mandatory")
  private Boolean isMandatory;

  @Column(name = "max_value")
  private String maxValue;

  @Column(name = "min_value")
  private String minValue;

  private String prefix;

  private String suffix;

  @Column(name = "type_value")
  private String typeValue;

  private Long version;

  // bi-directional many-to-one association to LoadingRule
  @ManyToOne
  @JoinColumn(name = "loading_rule_xid", referencedColumnName = "id")
  private LoadingRule loadingRule;
}
