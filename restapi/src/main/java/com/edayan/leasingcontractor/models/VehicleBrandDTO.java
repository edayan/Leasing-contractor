package com.edayan.leasingcontractor.models;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class VehicleBrandDTO extends RepresentationModel<VehicleBrandDTO> {
    private String brandName;
}
