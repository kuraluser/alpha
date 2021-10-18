package com.cpdss.vesselinfo.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.cpdss.vesselinfo.entity.Vessel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VesselInfoSpecification implements Specification<Vessel> {

	SearchCriteria criteria;
	
	@Override
	public Predicate toPredicate(Root<Vessel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		if (criteria.getOperation().equalsIgnoreCase("LIKE")) {
		      predicates.add(
		    		  criteriaBuilder.like(
		    				  criteriaBuilder.upper(root.get(criteria.getKey()).as(String.class)),
		              "%" + criteria.getValue().toString() + "%"));
		    }
		
		if (criteria.getOperation().equalsIgnoreCase("EQUALS")) {
		      predicates.add(
		    		  criteriaBuilder.equal(root.get(criteria.getKey()), 
		    				  criteria.getValue()));
		    }
		
		if (criteria.getOperation().equalsIgnoreCase("GREATER_THAN")) {
		      predicates.add(
		    		  criteriaBuilder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
		    }
		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}

}
