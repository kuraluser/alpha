/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

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
@Table(name = "inner_bulk_head_values")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InnerBulkHeadValues extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "vessel_xid")
  private Long vesselId;

  @Column(name = "frame_number")
  private BigDecimal frameNumber;

  @Column(name = "aft_alpha")
  private BigDecimal aftAlpha;

  @Column(name = "fore_alpha")
  private BigDecimal foreAlpha;

  @Column(name = "aft_center_cargotankxid")
  private BigDecimal aftCenterCargotankId;

  @Column(name = "fore_center_cargotankxid")
  private BigDecimal foreCenterCargotankId;

  @Column(name = "aft_c1")
  private BigDecimal aftC1;

  @Column(name = "fore_c1")
  private BigDecimal foreC1;

  @Column(name = "aft_wing_tankxid")
  private String aftWingTankId;

  @Column(name = "fore_wing_tankxid")
  private String foreWingTankId;

  @Column(name = "aft_c2")
  private BigDecimal aftC2;

  @Column(name = "fore_c2")
  private BigDecimal foreC2;

  @Column(name = "aft_ballast_tank")
  private String aftBallastTank;

  @Column(name = "fore_ballast_tank")
  private String foreBallastTank;

  @Column(name = "aft_c3")
  private BigDecimal aftC3;

  @Column(name = "fore_c3")
  private BigDecimal foreC3;

  @Column(name = "aft_bw_correction")
  private BigDecimal aftBwCorrection;

  @Column(name = "fore_bw_correction")
  private BigDecimal foreBwCorrection;

  @Column(name = "aft_c4")
  private BigDecimal aftC4;

  @Column(name = "fore_c4")
  private BigDecimal foreC4;

  @Column(name = "aft_max_fl_allowence")
  private BigDecimal aftMaxFlAllowence;

  @Column(name = "aft_min_fl_allowence")
  private BigDecimal aftMinFlAllowence;

  @Column(name = "fore_max_allowence")
  private BigDecimal foreMaxAllowence;

  @Column(name = "fore_min_allowence")
  private BigDecimal foreMinAllowence;
}
