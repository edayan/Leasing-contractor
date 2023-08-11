package com.edayan.leasingcontractor.models;

import com.edayan.leasingcontractor.controllers.IndexController;
import com.edayan.leasingcontractor.controllers.UserController;
import com.edayan.leasingcontractor.controllers.VehicleBrandController;
import com.edayan.leasingcontractor.controllers.VehicleController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class RootResource extends RepresentationModel<RootResource> {
    public RootResource() {
        Link selfLink = linkTo(methodOn(IndexController.class).getRoot()).withSelfRel();
        Link vehicleBrandsLink = linkTo(methodOn(VehicleBrandController.class).getAllVehicleBrands()).withRel("vehicleBrands");

        Link vehiclesLink = linkTo(methodOn(VehicleController.class).getVehicleDetails()).withRel("vehicles");
        Link usersLink = linkTo(methodOn(UserController.class).getAllUsers()).withRel("users");

        add(selfLink, vehicleBrandsLink, vehiclesLink, usersLink);
    }
}
