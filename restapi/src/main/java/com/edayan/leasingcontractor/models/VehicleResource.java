package com.edayan.leasingcontractor.models;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;

@Data
public class VehicleResource extends RepresentationModel<VehicleResource> {

    @NotNull(message = "Vehicle model ID is required")
    private Long vehicleModelId;

    @NotNull(message = "year is required")
    private int year;

    private String vin;

    @NotNull(message = "price is required")
    private double price;
}
