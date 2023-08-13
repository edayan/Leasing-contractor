package com.edayan.leasingcontractor.models.assemblers;

import com.edayan.leasingcontractor.controllers.VehicleController;
import com.edayan.leasingcontractor.models.VehicleDetailResource;
import com.edayan.leasingcontractor.repository.entities.Vehicle;
import com.edayan.leasingcontractor.repository.entities.VehicleModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VehicleDetailAssembler extends RepresentationModelAssemblerSupport<Vehicle, VehicleDetailResource> {
    public VehicleDetailAssembler() {
        super(VehicleController.class, VehicleDetailResource.class);
    }

    @Override
    public VehicleDetailResource toModel(Vehicle entity) {
        VehicleDetailResource dto = mapToVehicleDetail(entity);


        dto.add(linkTo(methodOn(VehicleController.class).updateVehicleDetails(entity.getId(), null)).withRel("update"));

        return dto;
    }

    private VehicleDetailResource mapToVehicleDetail(Vehicle entity) {
        VehicleDetailResource dto = new VehicleDetailResource();
        dto.setId(entity.getId());

        VehicleModel vModel = entity.getVehicleModel();
        dto.setModelName(vModel.getModelName());
        dto.setBrandName(vModel.getBrand().getBrandName());

        dto.setYear(entity.getYear());
        dto.setVin(entity.getVin());
        dto.setPrice(entity.getPrice());
        return dto;
    }

}
