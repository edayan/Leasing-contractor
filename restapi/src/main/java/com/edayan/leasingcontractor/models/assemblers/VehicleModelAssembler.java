package com.edayan.leasingcontractor.models.assemblers;


import com.edayan.leasingcontractor.controllers.VehicleBrandController;
import com.edayan.leasingcontractor.models.VehicleModelResource;
import com.edayan.leasingcontractor.repository.entities.VehicleModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VehicleModelAssembler extends RepresentationModelAssemblerSupport<VehicleModel, VehicleModelResource> {
    public VehicleModelAssembler() {
        super(VehicleBrandController.class, VehicleModelResource.class);
    }

    @Override
    public VehicleModelResource toModel(VehicleModel entity) {
        VehicleModelResource dto = instantiateModel(entity);
        dto.setId(entity.getId());
        dto.setModelName(entity.getModelName());
        dto.setBrand(entity.getBrand().getBrandName());

        dto.add(linkTo(methodOn(VehicleBrandController.class).getModelByBrand(entity.getId())).withSelfRel());

        return dto;
    }
}
