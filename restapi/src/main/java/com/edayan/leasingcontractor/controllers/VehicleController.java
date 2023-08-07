package com.edayan.leasingcontractor.controllers;

import com.edayan.leasingcontractor.models.VehicleResource;
import com.edayan.leasingcontractor.models.assemblers.VehicleAssembler;
import com.edayan.leasingcontractor.repository.VehicleDetailsRepository;
import com.edayan.leasingcontractor.repository.VehicleModelRepository;
import com.edayan.leasingcontractor.repository.entities.Vehicle;
import com.edayan.leasingcontractor.repository.entities.VehicleModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class VehicleController {
    private final VehicleDetailsRepository vehicleDetailsRepository;
    private final VehicleModelRepository vehicleModelRepository;

    private final VehicleAssembler vehicleAssembler;

    public VehicleController(VehicleDetailsRepository vehicleDetailsRepository, VehicleModelRepository vehicleModelRepository, VehicleAssembler vehicleAssembler) {
        this.vehicleDetailsRepository = vehicleDetailsRepository;
        this.vehicleModelRepository = vehicleModelRepository;
        this.vehicleAssembler = vehicleAssembler;
    }

    @GetMapping("/vehicles")
    public ResponseEntity<CollectionModel<VehicleResource>> getVehicleDetails() {
        List<VehicleResource> vehicles = vehicleDetailsRepository.findAll().stream()
                .map(vehicleAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(vehicles));
    }

    @PostMapping("/vehicles")
    public ResponseEntity<VehicleResource> createVehicleDetails(@RequestBody @Valid VehicleResource vehicleResource) {
        VehicleModel vehicleModel = vehicleModelRepository.findById(vehicleResource.getVehicleModelId())
                .orElseThrow(() -> new EntityNotFoundException("Vehicle model with id " + vehicleResource.getVehicleModelId() + " not found"));

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleModel(vehicleModel);
        vehicle.setYear(vehicleResource.getYear());
        vehicle.setVin(vehicleResource.getVin());
        vehicle.setPrice(vehicleResource.getPrice());

        Vehicle savedDetails = vehicleDetailsRepository.save(vehicle);
        VehicleResource responseDTO = vehicleAssembler.toModel(savedDetails);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/vehicles/{id}")
    public ResponseEntity<VehicleResource> updateVehicleDetails(@PathVariable Long id, @RequestBody @Valid VehicleResource updatedDetailsDTO) {
        Vehicle existingDetails = vehicleDetailsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle details with id " + id + " not found"));

        VehicleModel vehicleModel = vehicleModelRepository.findById(updatedDetailsDTO.getVehicleModelId())
                .orElseThrow(() -> new EntityNotFoundException("Vehicle model with id " + updatedDetailsDTO.getVehicleModelId() + " not found"));

        existingDetails.setVehicleModel(vehicleModel);
        existingDetails.setYear(updatedDetailsDTO.getYear());
        existingDetails.setVin(updatedDetailsDTO.getVin());
        existingDetails.setPrice(updatedDetailsDTO.getPrice());

        Vehicle savedDetails = vehicleDetailsRepository.save(existingDetails);
        VehicleResource responseDTO = vehicleAssembler.toModel(savedDetails);
        return ResponseEntity.ok(responseDTO);
    }
}
