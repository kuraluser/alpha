/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * On hand quantity entity class
 *
 * @author suhail.k
 */
@Entity
@Table(name = "on_hand_quantity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OnHandQuantity extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "loadable_study_xid")
  private LoadableStudy loadableStudy;

  @Column(name = "port_xid")
  private Long portXId;

  @Column(name = "fuel_type_xid")
  private Long fuelTypeXId;

  @Column(name = "tank_xid")
  private Long tankXId;

  @Column(name = "arrival_volume", precision = 10, scale = 4)
  private BigDecimal arrivalVolume;

  @Column(name = "arrival_quantity")
  private BigDecimal arrivalQuantity;

  @Column(name = "departure_volume")
  private BigDecimal departureVolume;

  @Column(name = "departure_quantity")
  private BigDecimal departureQuantity;

  @Column(name = "actual_arrival_quantity")
  private BigDecimal actualArrivalQuantity;

  @Column(name = "actual_departure_quantity")
  private BigDecimal actualDepartureQuantity;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "density")
  private BigDecimal density;

  @ManyToOne
  @JoinColumn(name = "port_rotation_xid")
  private LoadableStudyPortRotation portRotation;
  
  public BigDecimal getDepartureQuantity() {
	 return departureQuantity == null ? BigDecimal.valueOf(0) : departureQuantity;
  }

  public void setDepartureQuantity(BigDecimal departureQuantity) {
	 this.departureQuantity = departureQuantity== null ? BigDecimal.valueOf(0) : departureQuantity;
  }
}
