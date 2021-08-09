/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author jerin.g */
@Entity
@Table(name = "discharge_quantity_cargo_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DischargePatternQuantityCargoPortwiseDetails extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "cargo_xid")
  private Long cargoId;
  
  @Column(name = "cargo_nomination_xid")
  private Long cargoNominationId;

  @Column(name = "loadable_pattern_xid")
  private Long loadablePatternId;

  @Column(name = "operation_type ")
  private String operationType;
  
  @Column(name = "port_xid")
  private Long portId;
  
  @Column(name = "port_rotation_xid")
  private Long portRotationId;
  
  @Column(name = "cargo_abbreviation ")
  private String cargoAbbreviation;

  @Column(name = "estimated_api")
  private String estimatedAPI;
  
  @Column(name = "estimated_temp")
  private String estimatedTemp;
  
  @Column(name = "priority")
  private Integer priority;

  @Column(name = "color_code")
  private String colorCode;

  @Column(name = "discharge_mt")
  private String dischargeMT;
  
  @Column(name = "ordered_quantity")
  private String orderedQuantity;
  
  @Column(name = "slop_quantity")
  private String slopQuantity;

  @Column(name = "difference_percentage")
  private String differencePercentage;
  
  @Column(name = "loading_order")
  private Integer loadingOrder;
  
  @Column(name = "time_required_for_discharging")
  private String timeRequiredForDischarging;

  @Column(name = "discharging_rate")
  private String dischargingRate;
  
  @Column(name = "cargo_nomination_temperature")
  private String cargoNominationTemperature;

  @Column(name = "is_active")
  private Boolean isActive;
  
  @Column(name = "cowDetails")
  private List<DischargeStudyCowDetail> cowDetails;

}
