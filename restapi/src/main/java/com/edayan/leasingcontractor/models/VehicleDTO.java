package com.edayan.leasingcontractor.models;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;

@Data
public class VehicleDTO extends RepresentationModel<VehicleDTO> {

    @NotNull(message = "Vehicle model ID is required")
    private Long vehicleModelId;

    @NotNull(message = "year is required")
    private int year;

    private String vin;

    @NotNull(message = "price is required")
    private double price;
}
