/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vessel_cow_parameters")
public class VesselCowParameters extends EntityDoc {

  @Column(name = "vessel_xid")
  private Long vesselId;

  @Column(name = "top_cow_min_duration")
  private BigDecimal topCowMinDuration;

  @Column(name = "top_cow_max_duration")
  private BigDecimal topCowMaxDuration;

  @Column(name = "bottom_cow_min_duration")
  private BigDecimal bottomCowMinDuration;

  @Column(name = "bottom_cow_max_duration")
  private BigDecimal bottomCowMaxDuration;

  @Column(name = "full_cow_min_duration")
  private BigDecimal fullCowMinDuration;

  @Column(name = "full_cow_max_duration")
  private BigDecimal fullCowMaxDuration;

  @Column(name = "top_wash_min_angle")
  private BigDecimal topWashMinAngle;

  @Column(name = "top_wash_max_angle")
  private BigDecimal topWashMaxAngle;

  @Column(name = "bottom_wash_min_angle")
  private BigDecimal bottomWashMinAngle;

  @Column(name = "bottom_wash_max_angle")
  private BigDecimal bottomWashMaxAngle;

  @Column(name = "is_active")
  private Boolean isActive;
}
