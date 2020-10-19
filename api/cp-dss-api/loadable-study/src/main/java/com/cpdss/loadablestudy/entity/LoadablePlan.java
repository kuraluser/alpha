/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author jerin.g */
@Entity
@Table(name = "loadableplan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadablePlan extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "loadablestudyxid")
  private Integer loadableStudyXId;

  @Column(name = "details")
  private String details;

  @Column(name = "comments")
  private String comments;

  @Column(name = "loadablestudystatus")
  private Integer loadableStudyStatus;

  @Column(name = "isactive")
  private Boolean isActive;


  @OneToMany(mappedBy = "loadablePlanXId")
  private Collection<LoadablePlanQuantity> loadablePlanquantityCollection;

  @JoinColumn(name = "loadablePatternXId", referencedColumnName = "id")
  @ManyToOne
  private LoadablePattern loadablePatternXId;
}
