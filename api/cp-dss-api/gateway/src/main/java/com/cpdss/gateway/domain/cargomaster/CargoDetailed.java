package com.cpdss.gateway.domain.cargomaster;

import com.cpdss.common.generated.PortInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CargoDetailed {
    private Long id;
    private String name;
    private String abbreviation;
    private String api;
    private String type;
    private String assayDate;
    private String temp;
    private String reidVapourPressure;
    private String gas;
    private String totalWax;
    private String pourPoint;
    private String cloudPoint;
    private String viscosity;
    private String cowCodes;
    private String hydrogenSulfideOil;
    private String hydrogenSulfideVapour;
    private String benzene;
    private String specialInstrictionsRemark;
    private List<CargoPortMapping> loadingInformation;
}
