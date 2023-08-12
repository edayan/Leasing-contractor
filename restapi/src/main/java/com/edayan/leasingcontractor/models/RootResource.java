package com.edayan.leasingcontractor.models;

import com.edayan.leasingcontractor.controllers.*;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class RootResource extends RepresentationModel<RootResource> {
    public RootResource() {
        Link selfLink = linkTo(methodOn(IndexController.class).getRoot()).withSelfRel();
        Link vehicleBrandsLink = linkTo(methodOn(VehicleBrandController.class).getAllVehicleBrands()).withRel("vehicleBrands");

        Link vehiclesLink = linkTo(methodOn(VehicleController.class).getVehicleDetails()).withRel("vehicles");
        Link customersLink = linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("customers");
//        Link leasgingContractsLink = linkTo(methodOn(LeasingContractController.class).createLeasingContract()).withRel("leasingContracts");

        add(selfLink, vehicleBrandsLink, vehiclesLink, customersLink);
    }
}
