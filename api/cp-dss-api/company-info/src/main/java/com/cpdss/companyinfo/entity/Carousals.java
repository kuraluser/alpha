/* Licensed at AlphaOri Technologies */
package com.cpdss.companyinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Company data entity
 *
 * @author suhail.k
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Carousals extends EntityDoc {

  @ManyToOne
  @JoinColumn(name = "company_xid")
  private Company company;

  @Column(name = "file_path")
  private String filePath;
}
