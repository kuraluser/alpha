/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import com.cpdss.loadablestudy.entity.ApiTempHistory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
@AllArgsConstructor
public class ApiTempHistorySpecification implements Specification<ApiTempHistory> {

  SearchCriteria criteria;

  @Override
  public Predicate toPredicate(
      Root<ApiTempHistory> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    List<Predicate> predicates = new ArrayList<>();
    if (criteria.getOperation().equalsIgnoreCase("EQUALS")) {
      predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
    }
    if (criteria.getOperation().equalsIgnoreCase("IN")) {
      predicates.add(builder.in(root.get(criteria.getKey())).value(criteria.getValue()));
    }
    if (criteria.getOperation().equalsIgnoreCase("GREATER_THAN")) {
      predicates.add(
          builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
    }
    if (criteria.getOperation().equalsIgnoreCase("BETWEEN")) {
      List<LocalDateTime> dateRange = (List) criteria.getValue();
      predicates.add(
          builder.between(root.get(criteria.getKey()), dateRange.get(0), dateRange.get(1)));
    }
    if (criteria.getOperation().equalsIgnoreCase("LIKE")) {
      predicates.add(
          builder.like(
              builder.lower(root.get(criteria.getKey()).as(String.class)),
              "%" + criteria.getValue().toString() + "%"));
    }
    return builder.and(predicates.toArray(new Predicate[0]));
  }
}
