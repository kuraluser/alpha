/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.domain;

import com.cpdss.vesselinfo.entity.CrewVesselMapping;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

/**
 * Class for CrewVessel specification
 *
 * @author athul.cp
 */
@Slf4j
@Data
@AllArgsConstructor
public class CrewVesselSpecification implements Specification<CrewVesselMapping> {
  private FilterCriteria criteria;

  @Override
  public Predicate toPredicate(
      Root<CrewVesselMapping> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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
