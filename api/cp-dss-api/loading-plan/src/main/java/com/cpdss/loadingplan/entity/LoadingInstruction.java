/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "loading_instructions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoadingInstruction extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "loading_instruction")
  private String loadingInstruction;

  @Column(name = "parent_instruction_xid")
  private Long parentInstructionXId;

  @Column(name = "loading_type_xid")
  private Long loadingTypeXId;

  @Column(name = "loading_instruction_header_xid")
  private Long loadingInstructionHeaderXId;

  @Column(name = "is_checked")
  private Boolean isChecked;

  @Column(name = "reference_xid")
  private Long referenceXId;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "loading_instruction_templatexid")
  private Long loadingInstructionTemplateXId;

  @Column(name = "loading_xid")
  private Long loadingXId;

  @Column(name = "is_header_instruction")
  private Boolean isHeaderInstruction;

  @Column(name = "template_parent_xid")
  private Long templateParentXId;
}
