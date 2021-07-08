/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author ravi.r */
@Entity
@Table(name = "shearing_force_type4")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShearingForceType4 extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "frame_number")
  private BigDecimal frameNumber;

  @Column(name = "trim_m1")
  private BigDecimal trim_m1;

  @Column(name = "trim_0")
  private BigDecimal trim_0;

  @Column(name = "trim_1")
  private BigDecimal trim_1;

  @Column(name = "trim_2")
  private BigDecimal trim_2;

  @Column(name = "trim_3")
  private BigDecimal trim_3;

  @Column(name = "trim_4")
  private BigDecimal trim_4;

  @Column(name = "trim_5")
  private BigDecimal trim_5;

  @JoinColumn(name = "vessel_xid", referencedColumnName = "id")
  @ManyToOne
  private Vessel vessel;
}
