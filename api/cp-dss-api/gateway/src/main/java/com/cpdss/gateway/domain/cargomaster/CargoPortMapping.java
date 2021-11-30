package com.cpdss.gateway.domain.cargomaster;

import lombok.Data;

@Data
public class CargoPortMapping {
    private long id;
    private Country country;
    private CargoPort port;
}