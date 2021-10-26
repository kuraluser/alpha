/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author pranav.k */
@Entity
@Table(name = "port_loadable_plan_commingle_details_temp")
@Getter
@Setter
@NoArgsConstructor
public class PortLoadingPlanCommingleTempDetails extends PortLoadingPlanCommingleEntityDoc {
  private static final long serialVersionUID = 1L;

  @Column(name = "loading_xid")
  private Long loadingInformation;

}
