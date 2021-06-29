/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.*;
import lombok.*;

/** @author vinoth kumar M */
@Entity
@Table(name = "rule_list_master")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RuleListMaster extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "rule_list_master")
  private String ruleListMaster;

  @ManyToOne
  @JoinColumn(name = "rule_master_section_xid", referencedColumnName = "id")
  private RuleMasterSection ruleMasterSection;

  @Column(name = "is_active")
  private Boolean isActive;
  
  @Column(name = "rule_order")
  private Long ruleOrder;
}
