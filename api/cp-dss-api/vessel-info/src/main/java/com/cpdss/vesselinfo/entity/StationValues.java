/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author jerin.g */
@Entity
@Table(name = "station_values")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StationValues extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "stattion_from")
  private BigDecimal stattionFrom;

  @Column(name = "station_to")
  private BigDecimal stationTo;

  @Column(name = "frame_number_from")
  private BigDecimal frameNumberFrom;

  @Column(name = "frame_number_to")
  private BigDecimal frameNumberTo;

  @Column(name = "distance")
  private BigDecimal distance;

  @Column(name = "vessel_xid")
  private Long vesselId;
}
