/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Vessel draft condition entity
 *
 * @author suhail.k
 */
@Entity
@Getter
@Setter
@Table(name = "vesseldraftcondition")
public class VesselDraftCondition extends EntityDoc {

  @ManyToOne
  @JoinColumn(name = "vesselxid")
  private Vessel vessel;

  @ManyToOne
  @JoinColumn(name = "draftConditionxid")
  private DraftCondition draftCondition;

  @Column(name = "draftextreme")
  private BigDecimal draftExtreme;

  @Column(name = "isactive")
  private Boolean isActive;
}
