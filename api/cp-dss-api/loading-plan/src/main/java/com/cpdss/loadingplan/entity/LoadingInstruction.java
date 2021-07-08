/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Set;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "loading_instructions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadingInstruction extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "loading_instruction")
  private String loadingInstruction;

  @Column(name = "parent_instruction_xid")
  private Long parentInstructionXId;
  
  @Column(name = "is_checked")
  private Boolean isChecked;

  @Column(name = "reference_xid")
  private Long referenceXId;

  @Column(name = "is_active")
  private Boolean isActive;
 
  @ManyToOne
  @JoinColumn(name = "loading_instruction_templatexid")
  private LoadingInstructionTemplate loadingInstructionTemplateXId;
  
  @ManyToOne
  @JoinColumn(name = "loading_xid")
  private LoadingInformation loadingXId;;
 
}
