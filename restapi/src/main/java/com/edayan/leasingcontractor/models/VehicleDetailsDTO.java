package com.edayan.leasingcontractor.models;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class VehicleDetailsDTO extends RepresentationModel<VehicleDetailsDTO> {
    private Long id;
    private Long vehicleModelId;
    private int year;
    private String vin;
    private double price;
}
