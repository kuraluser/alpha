/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.Collection;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author jerin.g */
@Entity
@Table(name = "charterer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Charterer extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "name")
  private String name;

  @Column(name = "is_active")
  private Boolean isActive;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "charter_type_xid")
  private ChartererType charterType;

  @ManyToOne
  @JoinColumn(name = "charterer_company_xid") // company_xid
  private ChartererCompany charterCompany;

  @Column(name = "country_xid")
  private Long charterCountry;

  @OneToMany(mappedBy = "charterer")
  private Collection<VesselChartererMapping> vesselChartererMappingCollection;
}
