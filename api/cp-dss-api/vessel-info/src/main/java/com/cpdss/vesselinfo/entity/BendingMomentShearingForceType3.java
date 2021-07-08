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
@Table(name = "bending_moment_shearing_force_type3")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BendingMomentShearingForceType3 extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "frame_number")
  private BigDecimal frameNumber;

  @Column(name = "draftAp")
  private BigDecimal draftAp;

  @Column(name = "draftFp")
  private BigDecimal draftFp;

  @Column(name = "bendingMoment")
  private BigDecimal bendingMoment;

  @Column(name = "shearingForce")
  private BigDecimal shearingForce;

  @Column(name = "loadCondition")
  private String loadCondition;

  @JoinColumn(name = "vessel_xid", referencedColumnName = "id")
  @ManyToOne
  private Vessel vessel;
}
