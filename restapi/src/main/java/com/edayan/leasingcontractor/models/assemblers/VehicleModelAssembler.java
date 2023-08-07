package com.edayan.leasingcontractor.models.assemblers;


import com.edayan.leasingcontractor.controllers.VehicleBrandController;
import com.edayan.leasingcontractor.models.VehicleModelDTO;
import com.edayan.leasingcontractor.repository.entities.VehicleModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VehicleModelAssembler extends RepresentationModelAssemblerSupport<VehicleModel, VehicleModelDTO> {
    public VehicleModelAssembler() {
        super(VehicleBrandController.class, VehicleModelDTO.class);
    }

    @Override
    public VehicleModelDTO toModel(VehicleModel entity) {
        VehicleModelDTO dto = instantiateModel(entity);
        dto.setModelName(entity.getModelName());
        dto.setBrand(entity.getBrand().getBrandName());

        dto.add(linkTo(methodOn(VehicleBrandController.class).getModelByBrand(entity.getId())).withSelfRel());

        return dto;
    }
}
