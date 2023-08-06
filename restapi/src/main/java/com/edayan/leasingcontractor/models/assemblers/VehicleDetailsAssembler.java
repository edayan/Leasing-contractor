package com.edayan.leasingcontractor.models.assemblers;

import com.edayan.leasingcontractor.controllers.VehicleDetailsController;
import com.edayan.leasingcontractor.models.VehicleDetailsDTO;
import com.edayan.leasingcontractor.repository.entities.VehicleDetails;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VehicleDetailsAssembler extends RepresentationModelAssemblerSupport<VehicleDetails, VehicleDetailsDTO> {
    public VehicleDetailsAssembler() {
        super(VehicleDetailsController.class, VehicleDetailsDTO.class);
    }

    @Override
    public VehicleDetailsDTO toModel(VehicleDetails entity) {
        VehicleDetailsDTO dto = instantiateModel(entity);

        dto.setId(entity.getId());
        dto.setVehicleModelId(entity.getVehicleModel().getId());
        dto.setYear(entity.getYear());
        dto.setVin(entity.getVin());
        dto.setPrice(entity.getPrice());

//        dto.add(linkTo(methodOn(VehicleDetailsController.class).getVehicleDetails(entity.getId())).withSelfRel());
        dto.add(linkTo(methodOn(VehicleDetailsController.class).updateVehicleDetails(entity.getId(), dto)).withRel("update"));
        return dto;
    }
}
