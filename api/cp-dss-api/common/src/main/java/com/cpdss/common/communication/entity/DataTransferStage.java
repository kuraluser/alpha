/* Licensed at AlphaOri Technologies */
package com.cpdss.common.communication.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;

/**
 * DataTransferStage entity
 *
 * @author Selvy Thomas
 */
@Entity
@Table(name = "data_transfer_stage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataTransferStage extends EntityDoc {

  /** */
  private static final long serialVersionUID = 1L;

  @Column(name = "process_id")
  private String processId;

  @Column(name = "process_group_id")
  private String processGroupId;

  @Column(name = "process_identifier")
  private String processIdentifier;

  @Column(name = "process_type")
  private String processType;

  @Column(name = "data")
  private String data;

  @Column(name = "status")
  private String status;

  @Column(name = "status_description")
  private String statusDescription;
}
