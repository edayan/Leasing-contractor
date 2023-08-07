package com.edayan.leasingcontractor.models.assemblers;

import com.edayan.leasingcontractor.controllers.VehicleController;
import com.edayan.leasingcontractor.models.VehicleDTO;
import com.edayan.leasingcontractor.repository.entities.Vehicle;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VehicleAssembler extends RepresentationModelAssemblerSupport<Vehicle, VehicleDTO> {
    public VehicleAssembler() {
        super(VehicleController.class, VehicleDTO.class);
    }

    @Override
    public VehicleDTO toModel(Vehicle entity) {
        VehicleDTO dto = convertToDto(entity);


        dto.add(linkTo(methodOn(VehicleController.class).updateVehicleDetails(entity.getId(), dto)).withRel("update"));

        return dto;
    }

    private VehicleDTO convertToDto(Vehicle entity) {
        VehicleDTO dto = new VehicleDTO();
        dto.setVehicleModelId(entity.getVehicleModel().getId());
        dto.setYear(entity.getYear());
        dto.setVin(entity.getVin());
        dto.setPrice(entity.getPrice());
        return dto;
    }
}
