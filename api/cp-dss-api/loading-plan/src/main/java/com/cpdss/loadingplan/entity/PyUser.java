/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TypeDefs({@TypeDef(name = "json", typeClass = JsonBinaryType.class)})
@Table(name = "pyuser")
public class PyUser {

  @Id
  @Column(name = "id", nullable = false)
  private String id;

  @Column(name = "log_file")
  private String logFile;

  @Type(type = "json")
  @Column(name = "message", columnDefinition = "json")
  private String message;

  @Column(name = "status")
  private String status;

  @Column(name = "timestamp")
  private String timeStamp;
}
