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
@Table(name = "bending_moment_type2")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BendingMomentType2 extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "frame_number")
  private BigDecimal frameNumber;

  @Column(name = "displacement")
  private BigDecimal displacement;

  @Column(name = "buay")
  private BigDecimal buay;

  @Column(name = "difft")
  private BigDecimal difft;

  @Column(name = "corrt")
  private BigDecimal corrt;

  @Column(name = "is_active")
  private Boolean isActive;

  @JoinColumn(name = "vessel_xid", referencedColumnName = "id")
  @ManyToOne
  private Vessel vessel;
}
