/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class for Port rotation
 *
 * @author suhail.k
 */
@Entity
@Table(name = "loadablestudyportrotation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadableStudyPortRotation extends EntityDoc {

  /** */
  private static final long serialVersionUID = 1L;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "loadablestudyxid")
  private LoadableStudy loadableStudy;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "operationxid")
  private CargoOperation operation;

  @Column(name = "portxid")
  private Long portXId;

  @Column(name = "berthxid")
  private Long berthXId;

  @Column(name = "seawaterdensity")
  private BigDecimal seaWaterDensity;

  @Column(name = "distancebetweenports")
  private BigDecimal distanceBetweenPorts;

  @Column(name = "timeofstay")
  private BigDecimal timeOfStay;

  @Column(name = "maxdraft")
  private BigDecimal maxDraft;

  @Column(name = "airdraftrestriction")
  private BigDecimal airDraftRestriction;;

  @Column(name = "eta")
  private LocalDateTime eta;

  @Column(name = "etd")
  private LocalDateTime etd;

  @Column(name = "isactive")
  private boolean isActive;

  @Column(name = "laycanfrom")
  private LocalDate layCanFrom;

  @Column(name = "laycanto")
  private LocalDate layCanTo;

  @Column(name = "portorder")
  private Long portOrder;

  @PrePersist
  void prePersist() {
    this.isActive = true;
  }
}
