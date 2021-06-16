/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author vinoth kumar M */
@Entity
@Table(name = "rule_template_input")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RuleTemplateInput extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "rule_template_xid", referencedColumnName = "id")
  private RuleTemplate ruleTemplate;

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
}
