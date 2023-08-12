package com.edayan.leasingcontractor.models.assemblers;

import com.edayan.leasingcontractor.controllers.LeasingContractController;
import com.edayan.leasingcontractor.models.LeasingContractResource;
import com.edayan.leasingcontractor.repository.entities.LeasingContract;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class LeasingContractResourceAssembler extends RepresentationModelAssemblerSupport<LeasingContract, LeasingContractResource> {

    public LeasingContractResourceAssembler() {
        super(LeasingContractController.class, LeasingContractResource.class);
    }

    @Override
    public LeasingContractResource toModel(LeasingContract entity) {
        LeasingContractResource leasingContractResource = instantiateModel(entity);
        leasingContractResource.setContractNumber(entity.getId());
        leasingContractResource.setMonthlyRate(entity.getMonthlyRate());
        leasingContractResource.setVehicleId(entity.getVehicle().getId());
        leasingContractResource.setCustomerId(entity.getCustomer().getId());

        leasingContractResource.add(linkTo(methodOn(LeasingContractController.class).getAllLeasingContracts()).withSelfRel());
        return leasingContractResource;
    }
}
