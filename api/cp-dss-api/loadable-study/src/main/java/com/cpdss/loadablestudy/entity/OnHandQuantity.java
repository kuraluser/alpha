/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * On hand quantity entity class
 *
 * @author suhail.k
 */
@Entity
@Table(name = "onhandquantity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OnHandQuantity extends EntityDoc {

  @ManyToOne
  @JoinColumn(name = "loadablestudyxid")
  private LoadableStudy loadableStudy;

  @Column(name = "portxid")
  private Long portXId;

  @Column(name = "fueltypexid")
  private Long fuelTypeXId;

  @Column(name = "tankxid")
  private Long tankXId;

  @Column(name = "arrivalvolume", precision = 10, scale = 4)
  private BigDecimal arrivalVolume;

  @Column(name = "arrivalquantity")
  private BigDecimal arrivalQuantity;

  @Column(name = "departurevolume")
  private BigDecimal departureVolume;

  @Column(name = "departurequantity")
  private BigDecimal departureQuantity;

  @Column(name = "isactive")
  private Boolean isActive;
}
