/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.gateway.entity.Roles;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

/**
 * Class for role specification
 *
 * @author sreekumar.k
 */
@Data
@AllArgsConstructor
public class RolesSpecification implements Specification<Roles> {
  private FilterCriteria criteria;

  @Override
  public Predicate toPredicate(Root<Roles> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
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
      //      Partial match
      return builder.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
    }
    return null;
  }
}
