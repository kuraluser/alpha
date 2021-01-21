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

/** @author jerin.g */
@Entity
@Table(name = "loadable_pattern_comingle_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadablePatternComingleDetails extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "loadable_pattern_details_xid")
  private Long loadablePatternDetailsId;

  @Column(name = "tank_xid")
  private Long tankId;

  @Column(name = "tank_short_name")
  private String tankShortName;

  @Column(name = "quantity")
  private BigDecimal quantity;

  @Column(name = "api")
  private BigDecimal api;

  @Column(name = "temperature")
  private BigDecimal temperature;

  @Column(name = "cargo1_xid")
  private Long cargo1Id;

  @Column(name = "cargo1_abbrivation")
  private String cargo1Abbrivation;

  @Column(name = "cargo1_percentage")
  private BigDecimal cargo1Percentage;

  @Column(name = "cargo1_quantity")
  private BigDecimal cargo1Quantity;

  @Column(name = "cargo2_xid")
  private Long cargo2Id;

  @Column(name = "cargo2_abbrivation")
  private String cargo2Abbrivation;

  @Column(name = "cargo2_percentage")
  private BigDecimal cargo2Percentage;

  @Column(name = "cargo2_quantity")
  private BigDecimal cargo2Quantity;

  @Column(name = "grade")
  private String grade;

  @Column(name = "is_active")
  private Boolean isActive;
}
