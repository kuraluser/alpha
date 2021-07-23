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
@Table(name = "loading_instructions_template")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoadingInstructionTemplate extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "loading_insruction_typexid")
  private LoadingInstructionType loadingInsructionType;

  @Column(name = "loading_instruction")
  private String loading_instruction;

  @ManyToOne
  @JoinColumn(name = "loading_instruction_header_xid")
  private LoadingInstructionHeader loadingInstructionHeaderXId;

  @Column(name = "reference_xid")
  private Long referenceXId;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "parent_instruction_xid")
  private Long parentInstructionXId;

  @Column(name = "is_header_instruction")
  private Boolean isHeaderInstruction;
}
