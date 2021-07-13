/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "loading_sequence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadingSequence extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "port_xid")
  private Long portXId;

  @Column(name = "cargo_nomination_xid")
  private Long cargoNominationId;

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
  @JoinColumn(name = "loading_information_xid", referencedColumnName = "id")
  private LoadingInformation loadingInformation;

  @OneToMany(fetch = FetchType.LAZY)
  private Set<LoadingPlanPortWiseDetails> loadingPlanPortWiseDetails;

  @OneToMany(fetch = FetchType.LAZY)
  private Set<CargoLoadingRate> cargoLoadingRates;

  @OneToMany(fetch = FetchType.LAZY)
  private Set<CargoValve> cargoValves;

  @OneToMany(fetch = FetchType.LAZY)
  private Set<BallastValve> ballastValves;

  @OneToMany(fetch = FetchType.LAZY)
  private Set<DeballastingRate> deballastingRates;
}
