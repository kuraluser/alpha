/* Licensed under Apache-2.0 */
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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vesselcharterermapping")
public class VesselChartererMapping extends EntityDoc {

  @ManyToOne
  @JoinColumn(name = "vesselxid")
  private Vessel vessel;

  @ManyToOne
  @JoinColumn(name = "charttererxid")
  private Charterer charterer;

  @Column(name = "isactive")
  private boolean isActive;
}
