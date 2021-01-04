/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Entity class for Synoptical Table */
@Entity
@Table(name = "loadicator_data_for_synoptical_table")
@Getter
@Setter
@NoArgsConstructor
public class SynopticalTableLoadicatorData extends EntityDoc {

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
  private BigDecimal blindSetor;

  @Column(name = "is_active")
  private boolean isActive;

  @Column(name = "synoptical_table_xid")
  private Long synopticalTableId;
}
