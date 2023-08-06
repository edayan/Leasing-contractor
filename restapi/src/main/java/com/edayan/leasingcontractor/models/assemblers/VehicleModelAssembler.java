package com.edayan.leasingcontractor.models.assemblers;


import com.edayan.leasingcontractor.controllers.VehicleController;
import com.edayan.leasingcontractor.models.VehicleModelDTO;
import com.edayan.leasingcontractor.repository.entities.VehicleModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VehicleModelAssembler extends RepresentationModelAssemblerSupport<VehicleModel, VehicleModelDTO> {
    public VehicleModelAssembler() {
        super(VehicleController.class, VehicleModelDTO.class);
    }

    @Override
    public VehicleModelDTO toModel(VehicleModel entity) {
        VehicleModelDTO dto = instantiateModel(entity);
        dto.setId(entity.getId());
        dto.setModelName(entity.getModelName());
        dto.setBrand(entity.getBrand().getBrandName());

        dto.add(linkTo(methodOn(VehicleController.class).getModelByBrand(entity.getId())).withSelfRel());

        return dto;
    }
}
