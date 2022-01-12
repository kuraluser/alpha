/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity for file repo
 *
 * @author gokul.p
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "file_repo")
public class FileRepo extends EntityDoc {

  @Column(name = "voyage_number", length = 32)
  private String voyageNumber;

  @Column(name = "file_name", length = 100)
  private String fileName;

  @Column(name = "file_type", length = 10)
  private String fileType;

  @Column(name = "section", length = 20)
  private String section;

  @Column(name = "file_path", length = 255)
  private String filePath;

  @Column(name = "category", length = 20)
  private String category;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "is_transferred")
  private Boolean isTransferred;

  @Column(name = "is_system_generated")
  private Boolean isSystemGenerated;

  @Column(name = "vessel_xid")
  private Long vesselXId;
}
