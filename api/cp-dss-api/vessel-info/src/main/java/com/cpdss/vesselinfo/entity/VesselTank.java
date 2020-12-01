/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author jerin.g */
@Entity
@Table(name = "vessel_tank")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VesselTank extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "coating_type_xid")
  private Integer coatingTypeXid;

  @Column(name = "tank_name")
  private String tankName;

  @Column(name = "frame_number_from")
  private String frameNumberFrom;

  @Column(name = "frame_number_to")
  private String frameNumberTo;

  @Column(name = "full_capacity_cubm")
  private BigDecimal fullCapacityCubm;

  @Column(name = "full_capacity_cubft")
  private BigDecimal fullCapacityCubft;

  @Column(name = "tank_weight")
  private BigDecimal tankWeight;

  @Column(name = "lcg")
  private BigDecimal lcg;

  @Column(name = "vcg")
  private BigDecimal vcg;

  @Column(name = "tcg")
  private BigDecimal tcg;

  @Column(name = "createdby")
  private Integer createdby;

  @Column(name = "createddate")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createddate;

  @Column(name = "modified_by")
  private Integer modifiedBy;

  @Column(name = "modified_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date modifiedDate;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "fill_capacity_cubm")
  private BigDecimal fillCapacityCubm;

  @Column(name = "density")
  private BigDecimal density;

  @Column(name = "heating_capacity")
  private BigDecimal heatingCapacity;

  @Column(name = "max_temperature")
  private BigDecimal maxTemperature;

  @Column(name = "specific_gravity")
  private BigDecimal specificGravity;

  @Column(name = "shortname")
  private String shortName;

  @Column(name = "filling_ratio")
  private BigDecimal fillingRatio;

  @Column(name = "is_sloptank")
  private Boolean isSlopTank;

  @Column(name = "tank_group")
  private Integer tankGroup;

  @Column(name = "max_loading_limit")
  private BigDecimal maxLoadingLimit;

  @JoinColumn(name = "fuel_type_xid", referencedColumnName = "id")
  @ManyToOne
  private FuelMaster fuelMaster;

  @JoinColumn(name = "tank_category_xid", referencedColumnName = "id")
  @ManyToOne
  private TankCategory tankCategory;

  @JoinColumn(name = "tank_type_xid", referencedColumnName = "id")
  @ManyToOne
  private TankType tankType;

  @JoinColumn(name = "vessel_xid", referencedColumnName = "id")
  @ManyToOne
  private Vessel vessel;

  @OneToMany(mappedBy = "vesselTank")
  private Collection<VesselPipelineTankmapping> vesselPipelineTankmappingCollection;

  @OneToMany(mappedBy = "vesselTank")
  private Collection<VesselPumpTankMapping> vesselPumpTankMappingCollection;

  @OneToMany(mappedBy = "vesselTank")
  private Collection<ConsumptionDetails> consumptionDetailsCollection;

  @Column(name = "height_from")
  private String heightFrom;

  @Column(name = "height_to")
  private String heightTo;

  @Column(name = "tank_order")
  private Integer tankOrder;
}
