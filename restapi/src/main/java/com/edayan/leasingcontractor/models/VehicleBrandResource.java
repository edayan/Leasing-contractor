package com.edayan.leasingcontractor.models;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class VehicleBrandResource extends RepresentationModel<VehicleBrandResource> {
    private String brandName;
}
