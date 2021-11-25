/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** JSON data entity */
@Entity
@Table(name = "json_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JsonData extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "json_type_xid")
  private JsonType jsonTypeXId;

  @Column(name = "reference_xid")
  private Long referenceXId;

  @Column(name = "json_data")
  private String jsonData;

  @Transient private Long communicationRelatedEntityId;
}
