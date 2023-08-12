package com.edayan.leasingcontractor.controllers;


import com.edayan.leasingcontractor.models.CustomerResource;
import com.edayan.leasingcontractor.models.assemblers.CustomerResourceAssembler;
import com.edayan.leasingcontractor.repository.CustomerRepository;
import com.edayan.leasingcontractor.repository.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private final CustomerRepository customerRepository;

    private final CustomerResourceAssembler customerResourceAssembler;

    @Autowired
    public CustomerController(CustomerRepository customerRepository,
                              CustomerResourceAssembler customerResourceAssembler) {
        this.customerRepository = customerRepository;
        this.customerResourceAssembler = customerResourceAssembler;
    }

    @GetMapping("/customers")
    public ResponseEntity<CollectionModel<CustomerResource>> getAllCustomers() {
        final List<CustomerResource> users = customerRepository.findAll().stream()
                .map(customerResourceAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(users));
    }

    @PostMapping("/customers")
    public ResponseEntity<EntityModel<CustomerResource>> createCustomer(@RequestBody @Valid CustomerResource customerResource) {
        Customer customer = new Customer();
        customer.setFirstName(customerResource.getFirstName());
        customer.setLastName(customerResource.getLastName());
        customer.setBirthDate(customerResource.getBirthDate());

        Customer savedCustomer = customerRepository.save(customer);
        return ResponseEntity.ok(EntityModel.of(customerResourceAssembler.toModel(savedCustomer)));
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<EntityModel<CustomerResource>> getCustomer(@PathVariable Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer with id " + id + " not found"));

        return ResponseEntity.ok(EntityModel.of(customerResourceAssembler.toModel(customer)));
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<EntityModel<CustomerResource>> updateCustomer(
            @PathVariable Long id,
            @RequestBody @Valid CustomerResource updatedCustomerResource) {

        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer with id " + id + " not found"));

        existingCustomer.setFirstName(updatedCustomerResource.getFirstName());
        existingCustomer.setLastName(updatedCustomerResource.getLastName());
        existingCustomer.setBirthDate(updatedCustomerResource.getBirthDate());

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return ResponseEntity.ok(EntityModel.of(customerResourceAssembler.toModel(updatedCustomer)));
    }
}
