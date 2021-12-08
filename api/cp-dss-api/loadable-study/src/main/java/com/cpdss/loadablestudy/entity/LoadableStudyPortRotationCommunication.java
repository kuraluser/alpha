/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity class for Port rotation
 *
 * @author Selvy Thomas
 */
@Entity
@Table(name = "loadable_study_port_rotation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadableStudyPortRotationCommunication extends EntityDoc {

  /** */
  private static final long serialVersionUID = 1L;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "loadable_study_xid")
  private LoadableStudy loadableStudy;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "operation_xid")
  private CargoOperation operation;

  @Column(name = "port_xid")
  private Long portXId;

  @Column(name = "berth_xid")
  private Long berthXId;

  @Column(name = "sea_water_density")
  private BigDecimal seaWaterDensity;

  @Column(name = "distance_between_ports")
  private BigDecimal distanceBetweenPorts;

  @Column(name = "time_of_stay")
  private BigDecimal timeOfStay;

  @Column(name = "max_draft")
  private BigDecimal maxDraft;

  @Column(name = "air_draft_restriction")
  private BigDecimal airDraftRestriction;;

  @Column(name = "eta")
  private LocalDateTime eta;

  @Column(name = "etd")
  private LocalDateTime etd;

  @Column(name = "is_active")
  private boolean isActive;

  @Column(name = "lay_can_from")
  private LocalDate layCanFrom;

  @Column(name = "lay_can_to")
  private LocalDate layCanTo;

  @Column(name = "port_order")
  private Long portOrder;

  @PrePersist
  void prePersist() {
    this.isActive = true;
  }

  @Column(name = "is_portrotation_ohq_complete")
  private Boolean isPortRotationOhqComplete;

  @Column(name = "is_backloading_enabled")
  private Boolean isbackloadingEnabled;

  @Transient private String portRotationType;

  @Transient private Long communicationRelatedEntityId;

  @Column(name = "fresh_crude_oil")
  private Boolean freshCrudeOil;

  @Column(name = "fresh_crude_oil_quantity")
  private BigDecimal freshCrudeOilQuantity;

  @Column(name = "fresh_crude_oil_time")
  private BigDecimal freshCrudeOilTime;
}
