package com.edayan.leasingcontractor.controllers;


import com.edayan.leasingcontractor.models.LeasingContractResource;
import com.edayan.leasingcontractor.models.assemblers.LeasingContractResourceAssembler;
import com.edayan.leasingcontractor.repository.CustomerRepository;
import com.edayan.leasingcontractor.repository.LeasingContractRepository;
import com.edayan.leasingcontractor.repository.VehicleRepository;
import com.edayan.leasingcontractor.repository.entities.Customer;
import com.edayan.leasingcontractor.repository.entities.LeasingContract;
import com.edayan.leasingcontractor.repository.entities.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LeasingContractController {

    private final LeasingContractResourceAssembler leasingContractResourceAssembler;
    private final CustomerRepository customerRepository;

    private final VehicleRepository vehicleRepository;

    private final LeasingContractRepository leasingContractRepository;

    @Autowired
    public LeasingContractController(LeasingContractResourceAssembler leasingContractResourceAssembler,
                                     CustomerRepository customerRepository,
                                     VehicleRepository vehicleRepository, LeasingContractRepository leasingContractRepository) {
        this.leasingContractResourceAssembler = leasingContractResourceAssembler;
        this.customerRepository = customerRepository;
        this.vehicleRepository = vehicleRepository;
        this.leasingContractRepository = leasingContractRepository;
    }

    @PostMapping("/leasing-contracts")
    public ResponseEntity<EntityModel<LeasingContractResource>> createLeasingContract(
            @RequestBody @Valid LeasingContractResource leasingContractResource) {

        Optional<Customer> customerOptional = customerRepository.findById(leasingContractResource.getCustomerId());
        if (customerOptional.isEmpty()) {
            throw new EntityNotFoundException("Customer with id " + leasingContractResource.getCustomerId() + " not found");
        }


        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(leasingContractResource.getVehicleId());
        if (vehicleOptional.isEmpty()) {
            throw new EntityNotFoundException("Vehicle with id " + leasingContractResource.getVehicleId() + " not found");
        }


        LeasingContract contract = mapResourceToEntity(leasingContractResource, vehicleOptional.get(), customerOptional.get());

        LeasingContract savedContract = leasingContractRepository.save(contract);
        LeasingContractResource responseResource = leasingContractResourceAssembler.toModel(savedContract);

        return ResponseEntity.ok(EntityModel.of(responseResource));
    }

    @GetMapping("/leasing-contracts")
    public ResponseEntity<CollectionModel<LeasingContractResource>> getAllLeasingContracts() {
        List<LeasingContractResource> contracts = leasingContractRepository.findAll().stream()
                .map(leasingContractResourceAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(contracts));
    }

    @PutMapping("/leasing-contracts/{id}")
    public ResponseEntity<EntityModel<LeasingContractResource>> updateLeasingContract(
            @PathVariable Long id,
            @RequestBody @Valid LeasingContractResource updatedLeasingContractResource) {

        LeasingContract existingContract = leasingContractRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Leasing contract with id " + id + " not found"));

        Optional<Customer> customerOptional = customerRepository.findById(updatedLeasingContractResource.getCustomerId());
        if (customerOptional.isEmpty()) {
            throw new EntityNotFoundException("Customer with id " + updatedLeasingContractResource.getCustomerId() + " not found");
        }

        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(updatedLeasingContractResource.getVehicleId());
        if (vehicleOptional.isEmpty()) {
            throw new EntityNotFoundException("Vehicle with id " + updatedLeasingContractResource.getVehicleId() + " not found");
        }

        LeasingContract updatedContract = mapResourceToEntity(updatedLeasingContractResource, vehicleOptional.get(), customerOptional.get());
        updatedContract.setId(existingContract.getId());

        LeasingContract savedContract = leasingContractRepository.save(updatedContract);
        LeasingContractResource responseResource = leasingContractResourceAssembler.toModel(savedContract);

        return ResponseEntity.ok(EntityModel.of(responseResource));
    }


    private LeasingContract mapResourceToEntity(LeasingContractResource leasingContractResource, Vehicle vehicle, Customer customer) {
        LeasingContract contract = new LeasingContract();
        contract.setId(leasingContractResource.getContractNumber());
        contract.setMonthlyRate(leasingContractResource.getMonthlyRate());
        contract.setCustomer(customer);
        contract.setVehicle(vehicle);
        return contract;
    }
}