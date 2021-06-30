/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.*;
import lombok.*;

/** @author vinoth kumar M */
@Entity
@Table(name = "rule_vessel_mapping_input")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RuleVesselMappingInput extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "rule_vessel_mapping_xid", referencedColumnName = "id")
  private RuleVesselMapping ruleVesselMapping;

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
