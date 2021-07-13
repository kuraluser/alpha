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

/** @author jerin.g */
@Entity
@Table(name = "loadable_plan_quantity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadablePlanQuantity extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "grade")
  private String grade;

  @Column(name = "cargo_nomination_xid")
  private Long cargoNominationId;

  @Column(name = "estimated_api")
  private BigDecimal estimatedApi;

  @Column(name = "estimated_temperature")
  private BigDecimal estimatedTemperature;

  @Column(name = "order_quantity")
  private BigDecimal orderQuantity;

  @Column(name = "loadable_quantity")
  private BigDecimal loadableQuantity;

  @Column(name = "difference_percentage")
  private String differencePercentage;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "order_bbls_dbs")
  private String orderBblsDbs;

  @Column(name = "order_bbls_60f")
  private String orderBbls60f;

  @Column(name = "min_tolerence")
  private String minTolerence;

  @Column(name = "max_tolerence")
  private String maxTolerence;

  @Column(name = "loadable_bbls_dbs")
  private String loadableBblsDbs;

  @Column(name = "loadable_bbls_60f")
  private String loadableBbls60f;

  @Column(name = "loadable_lt")
  private String loadableLt;

  @Column(name = "loadable_mt")
  private String loadableMt;

  @Column(name = "loadable_kl")
  private String loadableKl;

  @Column(name = "difference_color")
  private String differenceColor;

  @JoinColumn(name = "loadable_pattern_xid")
  @ManyToOne
  private LoadablePattern loadablePattern;

  @Column(name = "cargo_xid")
  private Long cargoXId;

  @Column(name = "priority")
  private Integer priority;

  @Column(name = "cargo_abbreviation")
  private String cargoAbbreviation;

  @Column(name = "cargo_color")
  private String cargoColor;

  @Column(name = "loading_order")
  private Integer loadingOrder;

  @Column(name = "slop_quantity")
  private String slopQuantity;

  @Column(name = "time_required_for_loading")
  private String timeRequiredForLoading;

  @Column(name = "cargo_nomination_temperature")
  private BigDecimal cargoNominationTemperature;
}
