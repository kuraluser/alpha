package com.cpdss.loadablestudy.entity;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cpdss.common.utils.EntityDoc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity for Cargo Nomination
 *
 */
@Entity
@Table(name = "cargonomination")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CargoNomination extends EntityDoc {

	@Column(name = "loadablestudyxid")
	private Long loadableStudyId;

	@Column(name = "priority")
	private Long priority;

	@Column(name = "cargoxid")
	private Long cargoId;

	@Column(name = "abbreviation")
	private String abbreviation;

	@Column(name = "cargocolor")
	private String color;

	@Column(name = "maxtolerence")
	private BigDecimal maxTolerance;

	@Column(name = "mintolerence")
	private BigDecimal minTolerance;

	@Column(name = "api")
	private BigDecimal api;

	@Column(name = "temperature")
	private BigDecimal temperature;

	@Column(name = "valvesegregationxid")
	private Long segregationId;

	@OneToMany(mappedBy = "cargoNomination", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<CargoNominationPortDetails> cargoNominationPortDetails;
	
}
