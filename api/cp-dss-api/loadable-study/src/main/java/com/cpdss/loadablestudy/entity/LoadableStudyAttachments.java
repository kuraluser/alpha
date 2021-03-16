/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "loadable_study_attachments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadableStudyAttachments extends EntityDoc {

  @ManyToOne
  @JoinColumn(name = "loadable_study_xid")
  private LoadableStudy loadableStudy;

  @Column(name = "uploaded_filename", length = 500)
  private String uploadedFileName;

  @Column(name = "file_path", length = 500)
  private String filePath;

  @Column(name = "is_active")
  private Boolean isActive;
}
