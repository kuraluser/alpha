/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "discharging_instructions_template")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DischargingInstructionTemplate extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "discharging_insruction_typexid")
  private DischargingInstructionType dischargingInsructionType;

  @Column(name = "discharging_instruction")
  private String discharging_instruction;

  @ManyToOne
  @JoinColumn(name = "discharging_instruction_header_xid")
  private DischargingInstructionHeader dischargingInstructionHeaderXId;

  @Column(name = "reference_xid")
  private Long referenceXId;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "parent_instruction_xid")
  private Long parentInstructionXId;

  @Column(name = "is_header_instruction")
  private Boolean isHeaderInstruction;
}
