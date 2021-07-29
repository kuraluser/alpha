/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author arun.j */
@Entity
@Table(name = "discharge_study_port_instruction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DischargeStudyPortInstruction extends EntityDoc {

  private static final long serialVersionUID = 6722941798154545445L;

  @Column(name = "discharge_xid")
  private Long dischargeStudyId;

  @Column(name = "port_instruction_xid")
  private Long portInstructionId;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "port_xid")
  private Long portId;
}
