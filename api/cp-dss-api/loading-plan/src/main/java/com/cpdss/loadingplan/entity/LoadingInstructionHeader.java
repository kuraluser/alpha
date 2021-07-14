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
@Table(name = "loading_instructions_header")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoadingInstructionHeader extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "header_name")
  private String headerName;

  @Column(name = "is_active")
  private Boolean isActive;
}
