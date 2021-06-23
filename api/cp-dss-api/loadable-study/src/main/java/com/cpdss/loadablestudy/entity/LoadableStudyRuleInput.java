/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.*;
import lombok.*;

/**
 * Entity class for Loadable Study Rules
 *
 * @author vinothkumar.m
 */
@Entity
@Table(name = "loadable_study_rule_input")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadableStudyRuleInput extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "loadable_study_rule_xid", referencedColumnName = "id")
  private LoadableStudyRules loadableStudyRuleXId;

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
