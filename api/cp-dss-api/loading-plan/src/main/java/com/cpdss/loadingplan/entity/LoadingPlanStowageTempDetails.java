package com.cpdss.loadingplan.entity;

import com.cpdss.common.utils.EntityDoc;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "loading_plan_stowage_details_temp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadingPlanStowageTempDetails extends EntityDoc {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(
            name = "loading_plan_portwise_details_xid",
            referencedColumnName = "id",
            nullable = true)
    private LoadingPlanPortWiseDetails loadingPlanPortWiseDetails;

    @Column(name = "tank_xid")
    private Long tankXId;

    @Column(name = "cargo_nomination_xid")
    private Long cargoNominationId;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "ullage")
    private BigDecimal ullage;

    @Column(name = "quantity_m3")
    private BigDecimal quantityM3;

    @Column(name = "api")
    private BigDecimal api;

    @Column(name = "temperature")
    private BigDecimal temperature;

    @Column(name = "is_active")
    private Boolean isActive;

}