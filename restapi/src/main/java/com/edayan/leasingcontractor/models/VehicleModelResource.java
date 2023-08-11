package com.edayan.leasingcontractor.models;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class VehicleModelResource extends RepresentationModel<VehicleModelResource> {
    private long id;
    private String modelName;
    private String brand;
}
