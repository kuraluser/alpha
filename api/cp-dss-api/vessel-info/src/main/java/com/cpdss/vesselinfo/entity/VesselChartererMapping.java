/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author jerin.g */
@Entity
@Table(name = "vessel_charterer_mapping")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VesselChartererMapping extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "is_active")
  private Boolean isActive;

  @JoinColumn(name = "chartterer_xid", referencedColumnName = "id")
  @ManyToOne
  private Charterer charterer;

  @JoinColumn(name = "vessel_xid", referencedColumnName = "id")
  @ManyToOne
  private Vessel vessel;
}
