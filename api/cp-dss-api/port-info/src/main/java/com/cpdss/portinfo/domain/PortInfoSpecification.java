/* Licensed at AlphaOri Technologies */
package com.cpdss.portinfo.domain;

import com.cpdss.portinfo.entity.PortInfo;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

/**
 * Class for role specification
 *
 * @author sreekumar.k
 */
@Slf4j
@Data
@AllArgsConstructor
public class PortInfoSpecification implements Specification<PortInfo> {
  private FilterCriteria criteria;

  @Override
  public Predicate toPredicate(
      Root<PortInfo> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    if (criteria.getOperation().equalsIgnoreCase(">")) {
      //      Greater than
      return builder.greaterThanOrEqualTo(
          root.<String>get(criteria.getKey()), criteria.getValue().toString());
    } else if (criteria.getOperation().equalsIgnoreCase("<")) {
      //      Less than
      return builder.lessThanOrEqualTo(
          root.<String>get(criteria.getKey()), criteria.getValue().toString());
    } else if (criteria.getOperation().equalsIgnoreCase(":")) {
      //      Exact match
      return builder.equal(root.get(criteria.getKey()), criteria.getValue());
    } else if (criteria.getOperation().equalsIgnoreCase("like")) {
      if (criteria.getValue() instanceof String) {
        String val = (String) criteria.getValue();
        return builder.like(
            builder.upper(root.<String>get(criteria.getKey())), "%" + val.toUpperCase() + "%");
      }
      //      Partial match
      return builder.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
    } else if ("like-with-join".equalsIgnoreCase(criteria.getOperation())) {
      return builder.like(
          root.join(criteria.getAttributeName()).<String>get(criteria.getKey()),
          "%" + criteria.getValue() + "%");
    }
    return null;
  }
}
