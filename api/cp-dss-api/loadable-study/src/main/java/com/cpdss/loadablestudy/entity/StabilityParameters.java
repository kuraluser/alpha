/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpdss.loadablestudy.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cpdss.common.utils.EntityDoc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author jerin.g
 */
@Entity
@Table(name = "stability_parameters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StabilityParameters extends EntityDoc {

    private static final long serialVersionUID = 1L;

    @Column(name = "port_xid")
    private Long portXid;
    @Column(name = "fwd_draft")
    private String fwdDraft;
    @Column(name = "aft_draft")
    private String aftDraft;
    @Column(name = "mean_draft")
    private String meanDraft;
    @Column(name = "trim_value")
    private String trimValue;
    @Column(name = "heal")
    private String heal;
    @Column(name = "bending_moment")
    private String bendingMoment;
    @Column(name = "shearing_force")
    private String shearingForce;
    @Column(name = "is_active")
    private Boolean isActive;
    @JoinColumn(name = "loadable_pattern_xid", referencedColumnName = "id")
    @ManyToOne
    private LoadablePattern loadablePattern;


    
}
