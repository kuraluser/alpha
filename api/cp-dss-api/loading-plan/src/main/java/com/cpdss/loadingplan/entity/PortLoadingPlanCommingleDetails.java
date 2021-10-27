/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "port_loadable_plan_commingle_details")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PortLoadingPlanCommingleDetails extends PortLoadingPlanCommingleEntityDoc {
  private static final long serialVersionUID = 1L;

  @ManyToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "loading_xid", referencedColumnName = "id")
  private LoadingInformation loadingInformation;
}
