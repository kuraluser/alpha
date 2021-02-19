/* Licensed under Apache-2.0 */
package com.cpdss.loadableplan.entity;

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
@Table(name = "loadablestudyattachments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadableStudyAttachments extends EntityDoc {

  @ManyToOne
  @JoinColumn(name = "loadablestudyxid")
  private LoadableStudy loadableStudy;

  @Column(name = "uploadedfilename", length = 500)
  private String uploadedFileName;

  @Column(name = "filepath", length = 500)
  private String filePath;

  @Column(name = "isactive")
  private Boolean isActive;
}
