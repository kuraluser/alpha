/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.Set;
import javax.persistence.*;
import lombok.*;

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

  @OneToMany(mappedBy = "loadingPlanPortWiseDetails")
  private Set<LoadingPlanStowageDetails> loadingPlanStowageDetails;

  @OneToMany(mappedBy = "loadingPlanPortWiseDetails")
  private Set<LoadingPlanBallastDetails> loadingPlanBallastDetails;

  @OneToMany(mappedBy = "loadingPlanPortWiseDetails")
  private Set<LoadingPlanRobDetails> loadingPlanRobDetails;

  @OneToMany(mappedBy = "loadingPlanPortWiseDetails")
  private Set<LoadingPlanStabilityParameters> loadingPlanStabilityParameters;

  @OneToMany(mappedBy = "loadingPlanPortWiseDetails")
  private Set<DeballastingRate> deballastingRates;

  @Transient private String communicationRelatedEntityId;
}
