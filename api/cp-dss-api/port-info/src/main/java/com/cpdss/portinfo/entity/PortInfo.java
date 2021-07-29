/* Licensed at AlphaOri Technologies */
package com.cpdss.portinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Entity class for portinfo table */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "port_info")
public class PortInfo extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "name")
  private String name;

  @Column(name = "code")
  private String code;

  @Column(name = "density_seawater")
  private BigDecimal densitySeaWater;

  @Column(name = "average_tide_height")
  private BigDecimal averageTideHeight;

  @Column(name = "tide_height")
  private BigDecimal tideHeight;

  @Column(name = "is_active")
  private Boolean isActive;

  @OneToMany(mappedBy = "portInfo", fetch = FetchType.EAGER)
  private Set<CargoPortMapping> cargoportmappingSet;

  @OneToMany(mappedBy = "portInfo", fetch = FetchType.EAGER)
  private Set<BerthInfo> berthInfoSet;

  @Column(name = "time_of_sunrise")
  private LocalTime timeOfSunrise;

  @Column(name = "time_of_sunset")
  private LocalTime timeOfSunSet;

  @Column(name = "hw_tide_from")
  private BigDecimal hwTideFrom;

  @Column(name = "hw_tide_to")
  private BigDecimal hwTideTo;

  @Column(name = "hw_tide_time_from")
  private LocalTime hwTideTimeFrom;

  @Column(name = "hw_tide_time_to")
  private LocalTime hwTideTimeTo;

  @Column(name = "lw_tide_from")
  private BigDecimal lwTideFrom;

  @Column(name = "lw_tide_to")
  private BigDecimal lwTideTo;

  @Column(name = "lw_tide_time_from")
  private LocalTime lwTideTimeFrom;

  @Column(name = "lw_tide_time_to")
  private LocalTime lwTideTimeTo;

  @JoinColumn(name = "country_xid")
  @ManyToOne(fetch = FetchType.EAGER)
  private Country country;

  // bi-directional many-to-one association to Timezone
  @ManyToOne
  @JoinColumn(name = "timezone_xid")
  private Timezone timezone;

  @Column(name = "controlling_depth")
  private BigDecimal controllingDepth;

  @Column(name = "under_keel_clearence")
  private String underKeelClearance;

  @Column(name = "lattitude")
  private String lattitude;

  @Column(name = "longitude")
  private String longitude;
}
