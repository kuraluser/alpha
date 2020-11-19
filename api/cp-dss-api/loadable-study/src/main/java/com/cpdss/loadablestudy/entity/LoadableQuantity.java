/* Licensed under Apache-2.0 */
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
@Table(name = "loadablequantity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadableQuantity extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "draftrestriction")
  private BigDecimal draftRestriction;

  @Column(name = "estimatedseadensity")
  private BigDecimal estimatedSeaDensity;

  @Column(name = "tpcatdraft")
  private BigDecimal tpcatDraft;

  @Column(name = "estimatedsagging")
  private BigDecimal estimatedSagging;

  @Column(name = "displacementatdraftrestriction")
  private BigDecimal displacementAtDraftRestriction;

  @Column(name = "lightweight")
  private BigDecimal lightWeight;

  @Column(name = "deadweight")
  private BigDecimal deadWeight;

  @Column(name = "sgcorrection")
  private BigDecimal sgCorrection;

  @Column(name = "saggingdeduction")
  private BigDecimal saggingDeduction;

  @Column(name = "estimatedfoonboard")
  private BigDecimal estimatedFOOnBoard;

  @Column(name = "estimateddoonboard")
  private BigDecimal estimatedDOOnBoard;

  @Column(name = "estimatedfwonboard")
  private BigDecimal estimatedFWOnBoard;

  @Column(name = "constant")
  private BigDecimal constant;

  @Column(name = "otherifany")
  private BigDecimal otherIfAny;

  @Column(name = "totalquantity")
  private BigDecimal totalQuantity;

  @Column(name = "distancefromlastport")
  private BigDecimal distanceFromLastPort;

  @Column(name = "vesselaveragespeed")
  private BigDecimal vesselAverageSpeed;

  @Column(name = "foconsumptionperday")
  private BigDecimal foConsumptionPerDay;

  @Column(name = "totalfoconsumption")
  private BigDecimal totalFoConsumption;

  @Column(name = "isactive")
  private Boolean isActive;

  @Column(name = "portxid")
  private BigDecimal portId;

  @Column(name = "boilerwateronboard")
  private BigDecimal boilerWaterOnBoard;

  @Column(name = "ballast")
  private BigDecimal ballast;

  @Column(name = "runninghours")
  private BigDecimal runningHours;

  @Column(name = "runningdays")
  private BigDecimal runningDays;

  @Column(name = "foconsumptioninsz")
  private BigDecimal foConsumptionInSZ;

  @Column(name = "subtotal")
  private BigDecimal subTotal;

  @JoinColumn(name = "loadablestudyxid", referencedColumnName = "id")
  @ManyToOne
  private LoadableStudy loadableStudyXId;
}
