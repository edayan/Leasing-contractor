package com.edayan.leasingcontractor.models;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class VehicleDetailResource extends RepresentationModel<VehicleDetailResource> {
    private Long id;

    private long modelId;
    private String modelName;
    private String brandName;

    private int year;

    private String vin;

    private double price;

}
