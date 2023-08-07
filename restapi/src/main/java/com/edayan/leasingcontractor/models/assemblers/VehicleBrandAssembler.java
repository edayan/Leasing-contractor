package com.edayan.leasingcontractor.models.assemblers;

import com.edayan.leasingcontractor.controllers.VehicleBrandController;
import com.edayan.leasingcontractor.models.VehicleBrandDTO;
import com.edayan.leasingcontractor.repository.entities.VehicleBrand;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VehicleBrandAssembler extends RepresentationModelAssemblerSupport<VehicleBrand, VehicleBrandDTO> {
    public VehicleBrandAssembler() {
        super(VehicleBrandController.class, VehicleBrandDTO.class);
    }

    @Override
    public VehicleBrandDTO toModel(VehicleBrand entity) {
        VehicleBrandDTO dto = instantiateModel(entity);

        dto.setBrandName(entity.getBrandName());

        dto.add(linkTo(methodOn(VehicleBrandController.class).getVehicleBrand(entity.getId())).withRel("brandDetails"));
        dto.add(linkTo(methodOn(VehicleBrandController.class).getModelByBrand(entity.getId())).withRel("models"));

        return dto;
    }
}
