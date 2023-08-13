package com.edayan.leasingcontractor.models.assemblers;

import com.edayan.leasingcontractor.controllers.LeasingContractController;
import com.edayan.leasingcontractor.models.LeasingContractOverviewResource;
import com.edayan.leasingcontractor.repository.entities.LeasingContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LeasingContractOverviewResourceAssembler extends RepresentationModelAssemblerSupport<LeasingContract, LeasingContractOverviewResource> {

    private final VehicleDetailAssembler vehicleDetailAssembler;

    private final CustomerResourceAssembler customerResourceAssembler;

    @Autowired
    public LeasingContractOverviewResourceAssembler(VehicleAssembler vehicleAssembler,
                                                    VehicleDetailAssembler vehicleDetailAssembler,
                                                    CustomerResourceAssembler customerResourceAssembler) {
        super(LeasingContractController.class, LeasingContractOverviewResource.class);
        this.vehicleDetailAssembler = vehicleDetailAssembler;
        this.customerResourceAssembler = customerResourceAssembler;
    }


    @Override
    public LeasingContractOverviewResource toModel(LeasingContract entity) {
        LeasingContractOverviewResource overviewResource = instantiateModel(entity);

        overviewResource.setContractId(entity.getId());
        overviewResource.setMonthlyRate(entity.getMonthlyRate());

        overviewResource.setVehicle(vehicleDetailAssembler.toModel(entity.getVehicle()));
        overviewResource.setCustomer(customerResourceAssembler.toModel(entity.getCustomer()));

        overviewResource.add(linkTo(methodOn(LeasingContractController.class).getLeasingContract(entity.getId())).withSelfRel());

        return overviewResource;
    }
}
