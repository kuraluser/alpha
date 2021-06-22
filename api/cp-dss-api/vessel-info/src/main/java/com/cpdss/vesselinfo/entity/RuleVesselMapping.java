/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.List;
import javax.persistence.*;
import lombok.*;

/** @author vinoth kumar M */
@Entity
@Table(name = "rule_vessel_mapping")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RuleVesselMapping extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "rule_template_xid", referencedColumnName = "id")
  private RuleTemplate ruleTemplate;

  @ManyToOne
  @JoinColumn(name = "vessel_xid", referencedColumnName = "id")
  private Vessel vessel;

  @ManyToOne
  @JoinColumn(name = "ruletype_xid", referencedColumnName = "id")
  private RuleType ruleType;

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

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "ruleVesselMapping", cascade = CascadeType.ALL)
  private List<RuleVesselMappingInput> ruleVesselMappingInput;
}
