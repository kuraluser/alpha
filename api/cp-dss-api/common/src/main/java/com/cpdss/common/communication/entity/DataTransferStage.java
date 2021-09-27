/* Licensed at AlphaOri Technologies */
package com.cpdss.common.communication.entity;

import com.cpdss.common.utils.EntityDoc;
import com.google.gson.JsonArray;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

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
@TypeDefs({@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)})
@ToString
public class DataTransferStage extends EntityDoc {

  @Column(name = "process_id")
  private String processId;

  @Column(name = "process_group_id")
  private String processGroupId;

  @Column(name = "process_identifier")
  private String processIdentifier;

  @Column(name = "process_type")
  private String processType;

  @Type(type = "jsonb")
  @Column(columnDefinition = "jsonb")
  private JsonArray data;

  @Column(name = "status")
  private String status;
}
