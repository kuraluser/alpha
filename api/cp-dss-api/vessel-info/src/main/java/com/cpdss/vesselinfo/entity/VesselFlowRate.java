/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.*;

/**
 * Entity class for VesselFlowRate table
 *
 * @author arun.j
 */
@Entity
@Table(name = "vessel_flow_rate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VesselFlowRate extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @JoinColumn(name = "vesselxid", referencedColumnName = "id")
  @OneToOne
  private Vessel vessel;

  @JoinColumn(name = "flow_rate_parameter_xid", referencedColumnName = "id")
  @OneToOne
  private FlowRateParameter flowRateParameter;

  @Column(name = "flow_rate_1")
  private BigDecimal flowRateOne;

  @Column(name = "flow_rate_6")
  private BigDecimal flowRateSix;

  @Column(name = "flow_rate_7")
  private BigDecimal flowRateSeven;

  @Column(name = "flow_rate_12")
  private BigDecimal flowRateTwelve;

  @Column(name = "vessel_name")
  private String vesselName;

  @Column(name = "isactive")
  private Boolean isActive;
}
