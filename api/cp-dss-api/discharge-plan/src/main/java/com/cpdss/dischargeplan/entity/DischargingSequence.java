/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "discharging_sequence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DischargingSequence extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "port_xid")
  private Long portXId;

  @Column(name = "cargo_nomination_xid")
  private Long cargoNominationXId;

  @Column(name = "sequence_number")
  private Integer sequenceNumber;

  @Column(name = "start_time")
  private Integer startTime;

  @Column(name = "end_time")
  private Integer endTime;

  @Column(name = "stage_name")
  private String stageName;

  @Column(name = "to_loadicator")
  private Boolean toLoadicator;

  @Column(name = "is_active")
  private Boolean isActive;

  @ManyToOne
  @JoinColumn(name = "discharging_information_xid", referencedColumnName = "id")
  private DischargeInformation dischargeInformation;

  @OneToMany(mappedBy = "dischargingSequence", fetch = FetchType.LAZY)
  private Set<DischargingPlanPortWiseDetails> dischargingPlanPortWiseDetails;

  @OneToMany(mappedBy = "dischargingSequence", fetch = FetchType.LAZY)
  private Set<CargoDischargingRate> cargoDischargingRates;

  @OneToMany(mappedBy = "dischargingSequence", fetch = FetchType.LAZY)
  private Set<CargoValve> cargoValves;

  @OneToMany(mappedBy = "dischargingSequence", fetch = FetchType.LAZY)
  private Set<BallastValve> ballastValves;

  @OneToMany(mappedBy = "dischargingSequence", fetch = FetchType.LAZY)
  private Set<BallastingRate> deballastingRates;

  @OneToMany(mappedBy = "dischargingSequence", fetch = FetchType.LAZY)
  private Set<BallastOperation> ballastOperations;

  @Column(name = "cargo_discharging_rate_1")
  private BigDecimal cargoDischargingRate1;

  @Column(name = "cargo_discharging_rate_2")
  private BigDecimal cargoDischargingRate2;

  @Transient private Long communicationRelatedEntityId;
}
