/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "loadable_study_port_rotation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadableStudyPortRotation extends EntityDoc {

  /** */
  private static final long serialVersionUID = 1L;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "loadable_study_xid")
  private LoadableStudy loadableStudy;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "operation_xid")
  private CargoOperation operation;

  @OneToMany(
      mappedBy = "loadableStudyPortRotation",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  private List<SynopticalTable> synopticalTable;

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
}
