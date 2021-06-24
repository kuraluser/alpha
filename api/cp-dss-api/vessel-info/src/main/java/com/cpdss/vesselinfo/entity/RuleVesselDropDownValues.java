/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import javax.persistence.*;

import com.cpdss.common.utils.EntityDoc;

import lombok.*;

/** @author vinoth kumar M */
@Entity
@Table(name = "rule_list_master")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RuleVesselDropDownValues extends EntityDoc{
	 
	private static final long serialVersionUID = 1L;
	
	@Column(name = "rule_vessel_mapping_input_xid")
	private Long rule_vessel_mapping_input_xid;
	
	@Column( name = "drop_down_values")
	private String dropDownValue;
	
	@Column( name = "is_active")
	private Boolean isActive;

}
