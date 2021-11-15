/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "port_loadable_plan_commingle_details")
@Getter
@Setter
@NoArgsConstructor
public class PortLoadingPlanCommingleDetails extends PortLoadingPlanCommingleEntityDoc {
  private static final long serialVersionUID = 1L;

  @ManyToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "loading_xid", referencedColumnName = "id")
  private LoadingInformation loadingInformation;
}
