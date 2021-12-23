/* Licensed at AlphaOri Technologies */
package com.cpdss.cargoinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cargo_master")
public class Cargo extends EntityDoc {

  @Column(name = "crude_type")
  private String crudeType;

  @Column(name = "api")
  private String api;

  @Column(name = "from_rvp")
  private String fromRvp;

  @Column(name = "to_rvp")
  private String toRvp;

  @Column(name = "gas_c4")
  private String gasC4;

  @Column(name = "total_wax")
  private String totalWax;

  @Column(name = "min_pour_point")
  private String minPourPoint;

  @Column(name = "max_pour_point")
  private String maxPourPoint;

  @Column(name = "min_cloud_point")
  private String minCloudPoint;

  @Column(name = "max_cloud_point")
  private String maxCloudPoint;

  @Column(name = "viscocity_t1")
  private String viscocityT1;

  @Column(name = "viscocity_v1")
  private String viscocityV1;

  @Column(name = "viscocity_t2")
  private String viscocityT2;

  @Column(name = "viscocity_v2")
  private String viscocityV2;

  @Column(name = "min_load_temp")
  private String minLoadTemp;

  @Column(name = "min_on_carriage_temp")
  private String minOnCarriageTemp;

  @Column(name = "mind_is_charge_temp")
  private String mindIsChargeTemp;

  @Column(name = "cow_code_recommended_winter")
  private String cowCodeRecommendedWinter;

  @Column(name = "cow_code_recommended_summer")
  private String cowCodeRecommendedSummer;

  @Column(name = "h2s_oil_phase")
  private String h2sOilPhase;

  @Column(name = "h2s_vapour_phase_confirmed")
  private String h2sVapourPhaseConfirmed;

  @Column(name = "benzene")
  private String benzene;

  @Column(name = "remarks")
  private String remarks;

  @Column(name = "company_xid")
  private Long companyId;

  @Column(name = "abbreviation")
  private String abbreviation;

  @Column(name = "is_condensate_cargo")
  private Boolean isCondensateCargo;

  @Column(name = "isactive")
  private Boolean isActive;

  @Column(name = "last_updated")
  private LocalDate lastUpdated;
}
