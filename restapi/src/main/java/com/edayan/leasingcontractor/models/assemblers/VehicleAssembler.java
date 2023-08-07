package com.edayan.leasingcontractor.models.assemblers;

import com.edayan.leasingcontractor.controllers.VehicleController;
import com.edayan.leasingcontractor.models.VehicleResource;
import com.edayan.leasingcontractor.repository.entities.Vehicle;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VehicleAssembler extends RepresentationModelAssemblerSupport<Vehicle, VehicleResource> {
    public VehicleAssembler() {
        super(VehicleController.class, VehicleResource.class);
    }

    @Override
    public VehicleResource toModel(Vehicle entity) {
        VehicleResource dto = convertToDto(entity);


        dto.add(linkTo(methodOn(VehicleController.class).updateVehicleDetails(entity.getId(), dto)).withRel("update"));

        return dto;
    }

    private VehicleResource convertToDto(Vehicle entity) {
        VehicleResource dto = new VehicleResource();
        dto.setVehicleModelId(entity.getVehicleModel().getId());
        dto.setYear(entity.getYear());
        dto.setVin(entity.getVin());
        dto.setPrice(entity.getPrice());
        return dto;
    }
}
