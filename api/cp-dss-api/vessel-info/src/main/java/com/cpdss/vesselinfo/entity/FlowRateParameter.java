/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class for VesselFlowRate table
 *
 * @author arun.j
 */
@Entity
@Table(name = "flow_rate_parameters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlowRateParameter extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "flow_rate_parameter")
  private String flowRateParameter;

  @Column(name = "isactive")
  private Boolean isAstive;
}
