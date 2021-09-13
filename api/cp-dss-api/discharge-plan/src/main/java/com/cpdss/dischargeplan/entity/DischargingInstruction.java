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
@Table(name = "discharging_instructions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DischargingInstruction extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "discharging_instruction")
  private String dischargingInstruction;

  @Column(name = "parent_instruction_xid")
  private Long parentInstructionXId;

  @Column(name = "discharging_type_xid")
  private Long dischargingTypeXId;

  @Column(name = "discharging_instruction_header_xid")
  private Long dischargingInstructionHeaderXId;

  @Column(name = "is_checked")
  private Boolean isChecked;

  @Column(name = "reference_xid")
  private Long referenceXId;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "discharging_instruction_templatexid")
  private Long dischargingInstructionTemplateXId;

  @Column(name = "discharging_xid")
  private Long dischargingXId;

  @Column(name = "is_header_instruction")
  private Boolean isHeaderInstruction;

  @Column(name = "template_parent_xid")
  private Long templateParentXId;
}
