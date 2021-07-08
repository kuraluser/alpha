/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "loading_instructions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadingInstructionTemplate extends EntityDoc {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "loading_insruction_typexid")
	private LoadingInstructionType loading_insruction_typexid;

	@Column(name = "loading_instruction")
	private String loading_instruction;

	@ManyToOne
	@JoinColumn(name = "loading_instruction_header_xid")
	private LoadingInstructionHeader loadingInstructionHeaderXId;

	@Column(name = "reference_xid")
	private Long referenceXId;

	@Column(name = "is_active")
	private Boolean isActive;


}
