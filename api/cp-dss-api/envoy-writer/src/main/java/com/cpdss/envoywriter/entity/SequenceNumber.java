/* Licensed at AlphaOri Technologies */
package com.cpdss.envoywriter.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author jerin.g */
@Entity
@Table(name = "sequence_number")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SequenceNumber extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "sequence_number")
  private Long sequenceNumber;

  @Column(name = "uuid")
  private String uuid;
}
