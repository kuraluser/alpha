/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

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
@Table(name = "vessel_draft_condition")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VesselDraftCondition extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "depth")
  private BigDecimal depth;

  @Column(name = "freeboard")
  private BigDecimal freeboard;

  @Column(name = "draft_extreme")
  private BigDecimal draftExtreme;

  @Column(name = "displacement")
  private BigDecimal displacement;

  @Column(name = "deadweight")
  private BigDecimal deadweight;

  @Column(name = "deadweight_lt")
  private BigDecimal deadweightLt;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "load_line_number")
  private Integer loadLineNumber;

  @JoinColumn(name = "draftcondition_xid", referencedColumnName = "id")
  @ManyToOne
  private DraftCondition draftCondition;

  @JoinColumn(name = "vessel_xid", referencedColumnName = "id")
  @ManyToOne
  private Vessel vessel;
}
