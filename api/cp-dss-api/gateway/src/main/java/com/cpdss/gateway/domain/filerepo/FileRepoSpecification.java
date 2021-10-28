package com.cpdss.gateway.domain.filerepo;

import com.cpdss.gateway.entity.FileRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Data
@AllArgsConstructor
public class FileRepoSpecification implements Specification<FileRepo> {

    SearchCriteria criteria;

    @Override
    public Predicate toPredicate(
            Root<FileRepo> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        List<Predicate> predicates = new ArrayList<>();
        Path<Object> value = root.get(criteria.getKey());
        if (criteria.getOperation().equalsIgnoreCase("EQUALS")) {
            predicates.add(
                    builder.equal(value, criteria.getValue()));
        }
        else if (criteria.getOperation().equalsIgnoreCase("IN")) {
            predicates.add(builder.in(root.get(criteria.getKey())).value(criteria.getValue()));
        }
        else if (criteria.getOperation().equalsIgnoreCase("GREATER_THAN")) {
            predicates.add(
                    builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
        }
        else if (criteria.getOperation().equalsIgnoreCase("BETWEEN")) {
            List<LocalDateTime> dateRange = (List) criteria.getValue();
            predicates.add(
                    builder.between(root.get(criteria.getKey()), dateRange.get(0), dateRange.get(1)));
        }
        else if (criteria.getOperation().equalsIgnoreCase("LIKE")) {
            predicates.add(
                    builder.like(
                            builder.lower(root.get(criteria.getKey()).as(String.class)),
                            criteria.getValue().toString().toLowerCase() ));
        }
        return builder.and(predicates.toArray(new Predicate[0]));
    }
}

