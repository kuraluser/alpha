/* Licensed at AlphaOri Technologies */
package com.cpdss.common.communication.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;

/**
 * DataTransferOutBound entity
 *
 * @author Mahesh KM
 */
@Entity
@Table(name = "data_transfer_in_bound")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataTransferInBound extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "process_id")
  private String processId;

  @Column(name = "process")
  private String process;

  @Column(name = "dependant_process_id")
  private String dependantProcessId;

  @Column(name = "dependant_process_module")
  private String dependantProcessModule;

  // refer StagingStatus enum for possible values
  @Column(name = "status")
  private String status;
}
