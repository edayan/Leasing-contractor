package com.edayan.leasingcontractor.models.assemblers;

import com.edayan.leasingcontractor.controllers.CustomerController;
import com.edayan.leasingcontractor.models.CustomerResource;
import com.edayan.leasingcontractor.repository.entities.Customer;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerResourceAssembler extends RepresentationModelAssemblerSupport<Customer, CustomerResource> {


    public CustomerResourceAssembler() {
        super(CustomerController.class, CustomerResource.class);
    }

    @Override
    public CustomerResource toModel(Customer entity) {
        CustomerResource resource = instantiateModel(entity);
        resource.setId(entity.getId());
        resource.setFirstName(entity.getFirstName());
        resource.setLastName(entity.getLastName());
        resource.setBirthDate(entity.getBirthDate());

        Link selfLink = linkTo(methodOn(CustomerController.class).getCustomer(entity.getId())).withSelfRel();
        Link updateLink = linkTo(methodOn(CustomerController.class).updateCustomer(entity.getId(), resource)).withRel("update");

        resource.add(selfLink, updateLink);

        return resource;
    }
}
