/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Entity class for comingle_cargo */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comingle_cargo")
public class CommingleCargo extends EntityDoc {

  @Column(name = "loadable_study_xid")
  private Long loadableStudyXId;

  @Column(name = "purpose_xid")
  private Long purposeXid;

  @Column(name = "tank_ids")
  private String tankIds;

  @Column(name = "cargo1_xid")
  private Long cargo1Xid;

  @Column(name = "cargo1_percentage")
  private BigDecimal cargo1Pct;

  @Column(name = "cargo2_xid")
  private Long cargo2Xid;

  @Column(name = "cargo2_percentage")
  private BigDecimal cargo2Pct;

  @Column(name = "quantity")
  private BigDecimal quantity;

  @Column(name = "is_slop_only")
  private Boolean isSlopOnly;

  @Column(name = "isactive")
  private Boolean isActive;

  @Column(name = "priority")
  private Integer priority;

  @Column(name = "cargo_nomination1_xid")
  private Long cargoNomination1Id;

  @Column(name = "cargo_nomination2_xid")
  private Long cargoNomination2Id;
}
