/* Licensed at AlphaOri Technologies */
package com.cpdss.portinfo.domain;

import com.cpdss.portinfo.entity.CargoPortMapping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Class for CargoPortMapping specification
 *
 * @author athul.cp
 */
@Slf4j
@Data
@AllArgsConstructor
public class CargoPortInfoSpecification implements Specification<CargoPortMapping> {
  private FilterCriteria criteria;

  @Override
  public Predicate toPredicate(
      Root<CargoPortMapping> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    if (criteria.getOperation().equalsIgnoreCase("like")) {
      if (criteria.getValue() instanceof String) {
        String val = (String) criteria.getValue();
        return criteriaBuilder.like(
            criteriaBuilder.upper(root.<String>get(criteria.getKey())),
            "%" + val.toUpperCase() + "%");
      }
      return criteriaBuilder.like(
          root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
    } else if ("like-with-join".equalsIgnoreCase(criteria.getOperation())) {
      if (criteria.getValue() instanceof String) {
        String val = (String) criteria.getValue();
        return criteriaBuilder.like(
            root.join(criteria.getAttributeName()).<String>get(criteria.getKey()),
            "%" + val.toUpperCase() + "%");
      }
      return criteriaBuilder.like(
          root.join(criteria.getAttributeName()).<String>get(criteria.getKey()),
          "%" + criteria.getValue() + "%");
    }
    return null;
  }
}
