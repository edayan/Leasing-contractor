package com.edayan.leasingcontractor.models;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class VehicleModelDTO extends RepresentationModel<VehicleModelDTO> {
    private String modelName;
    private String brand;
}
