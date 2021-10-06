/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import java.math.BigDecimal;
import javax.persistence.*;

import com.cpdss.common.utils.EntityDoc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author sanalkumar.k
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vessel_venting_capacity")
public class VesselVentingCapacity extends EntityDoc {

	private static final long serialVersionUID = 1L;

	@Column(name = "vessel_name")
	private String vesselName;
	
	@Column(name = "vessel_xid")
	private Long vesselId;

	@Column(name = "high_velocity_pressure")
	private BigDecimal highVelocityPressure;

	@Column(name = "high_velocity_vaccum")
	private BigDecimal highVelocityVaccum;

	@Column(name = "pv_breaker_pressure")
	private BigDecimal pvBreakerPressure;

	@Column(name = "pv_breaker_vaccum")
	private BigDecimal pvBreakerVaccum;

	@Column(name = "is_active")
	private Boolean isActive;

}
