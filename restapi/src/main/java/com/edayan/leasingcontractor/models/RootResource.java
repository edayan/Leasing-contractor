package com.edayan.leasingcontractor.models;

import com.edayan.leasingcontractor.controllers.IndexController;
import com.edayan.leasingcontractor.controllers.VehicleBrandController;
import com.edayan.leasingcontractor.controllers.VehicleDetailsController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class RootResource extends RepresentationModel<RootResource> {
    public RootResource() {
        Link selfLink = linkTo(methodOn(IndexController.class).getRoot()).withSelfRel();
        Link vehicleBrandsLink = linkTo(methodOn(VehicleBrandController.class).getAllVehicleBrands()).withRel("vehicleBrands");
//        Link vehicleModelsLink = linkTo(methodOn(VehicleController.class).getAllVehicleModels()).withRel("vehicleModels");

        Link createVehicleDetailsLink = linkTo(methodOn(VehicleDetailsController.class).createVehicleDetails(null)).withRel("createVehicleDetails");

        add(selfLink, vehicleBrandsLink, createVehicleDetailsLink);
    }
}
