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
@Table(name = "loading_plan_portwise_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadingPlanPortWiseDetails extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "time")
  private Integer time;

  @Column(name = "is_active")
  private Boolean isActive;

  @ManyToOne
  @JoinColumn(name = "loading_sequences_xid", referencedColumnName = "id")
  private LoadingSequence loadingSequence;

  @OneToMany(fetch = FetchType.LAZY)
  private Set<LoadingPlanStowageDetails> loadingPlanStowageDetails;

  @OneToMany(fetch = FetchType.LAZY)
  private Set<LoadingPlanBallastDetails> loadingPlanBallastDetails;

  @OneToMany(fetch = FetchType.LAZY)
  private Set<LoadingPlanRobDetails> loadingPlanRobDetails;

  @OneToMany(fetch = FetchType.LAZY)
  private Set<LoadingPlanStabilityParameters> loadingPlanStabilityParameters;

  @OneToMany(fetch = FetchType.LAZY)
  private Set<DeballastingRate> deballastingRates;
}
