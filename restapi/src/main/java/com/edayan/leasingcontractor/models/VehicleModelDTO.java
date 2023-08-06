package com.edayan.leasingcontractor.models;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class VehicleModelDTO extends RepresentationModel<VehicleModelDTO> {
    private Long id;
    private String modelName;
    private String brand;
}
