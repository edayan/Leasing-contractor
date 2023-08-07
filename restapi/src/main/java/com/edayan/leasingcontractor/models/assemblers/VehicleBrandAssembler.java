package com.edayan.leasingcontractor.models.assemblers;

import com.edayan.leasingcontractor.controllers.VehicleBrandController;
import com.edayan.leasingcontractor.models.VehicleBrandResource;
import com.edayan.leasingcontractor.repository.entities.VehicleBrand;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VehicleBrandAssembler extends RepresentationModelAssemblerSupport<VehicleBrand, VehicleBrandResource> {
    public VehicleBrandAssembler() {
        super(VehicleBrandController.class, VehicleBrandResource.class);
    }

    @Override
    public VehicleBrandResource toModel(VehicleBrand entity) {
        VehicleBrandResource dto = instantiateModel(entity);

        dto.setBrandName(entity.getBrandName());

        dto.add(linkTo(methodOn(VehicleBrandController.class).getVehicleBrand(entity.getId())).withRel("brandDetails"));
        dto.add(linkTo(methodOn(VehicleBrandController.class).getModelByBrand(entity.getId())).withRel("models"));

        return dto;
    }
}
