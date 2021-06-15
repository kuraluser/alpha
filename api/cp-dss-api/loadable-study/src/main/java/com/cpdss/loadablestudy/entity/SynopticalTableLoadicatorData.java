/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Entity class for Synoptical Table */
@Entity
@Table(name = "loadicator_data_for_synoptical_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SynopticalTableLoadicatorData extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "hog")
  private BigDecimal hog;

  @Column(name = "calculated_draft_fwd_plan")
  private BigDecimal calculatedDraftFwdPlanned;

  @Column(name = "calculated_draft_fwd_actual")
  private BigDecimal calculatedDraftFwdActual;

  @Column(name = "calculated_draft_mid_plan")
  private BigDecimal calculatedDraftMidPlanned;

  @Column(name = "calculated_draft_mid_actual")
  private BigDecimal calculatedDraftMidActual;

  @Column(name = "calculated_draft_aft_plan")
  private BigDecimal calculatedDraftAftPlanned;

  @Column(name = "calculated_draft_aft_actual")
  private BigDecimal calculatedDraftAftActual;

  @Column(name = "calculated_trim_plan")
  private BigDecimal calculatedTrimPlanned;

  @Column(name = "calculated_trim_actual")
  private BigDecimal calculatedTrimActual;

  @Column(name = "blind_sector")
  private BigDecimal blindSector;

  @Column(name = "is_active")
  private boolean isActive;

  @Column(name = "loadable_pattern_xid")
  private Long loadablePatternId;

  @Column(name = "ballast_actual")
  private BigDecimal ballastActual;

  @OneToOne
  @JoinColumn(name = "synoptical_table_xid")
  private SynopticalTable synopticalTable;

  @Column(name = "list")
  private BigDecimal list;

  @Column(name = "port_xid")
  private Long portId;

  @Column(name = "operation_xid")
  private Long operationId;

  @Column(name = "bending_moment")
  private BigDecimal bendingMoment;

  @Column(name = "shearing_force")
  private BigDecimal shearingForce;
}
