package com.edayan.leasingcontractor.controllers;

import com.edayan.leasingcontractor.models.VehicleDetailResource;
import com.edayan.leasingcontractor.models.VehicleResource;
import com.edayan.leasingcontractor.models.assemblers.VehicleAssembler;
import com.edayan.leasingcontractor.models.assemblers.VehicleDetailAssembler;
import com.edayan.leasingcontractor.repository.VehicleModelRepository;
import com.edayan.leasingcontractor.repository.VehicleRepository;
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
    private final VehicleRepository vehicleRepository;
    private final VehicleModelRepository vehicleModelRepository;

    private final VehicleDetailAssembler vehicleDetailAssembler;

    private final VehicleAssembler vehicleAssembler;

    public VehicleController(VehicleRepository vehicleRepository,
                             VehicleModelRepository vehicleModelRepository,
                             VehicleDetailAssembler vehicleDetailAssembler,
                             VehicleAssembler vehicleAssembler) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleModelRepository = vehicleModelRepository;
        this.vehicleDetailAssembler = vehicleDetailAssembler;
        this.vehicleAssembler = vehicleAssembler;
    }

    @GetMapping("/vehicles")
    public ResponseEntity<CollectionModel<VehicleDetailResource>> getVehicleDetails() {
        List<VehicleDetailResource> vehicles = vehicleRepository.findAll().stream()
                .map(vehicleDetailAssembler::toModel)
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

        Vehicle savedDetails = vehicleRepository.save(vehicle);
        VehicleResource responseDTO = vehicleAssembler.toModel(savedDetails);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/vehicles/{id}")
    public ResponseEntity<VehicleResource> updateVehicleDetails(@PathVariable Long id, @RequestBody @Valid VehicleResource updatedDetailsDTO) {
        Vehicle existingDetails = vehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle details with id " + id + " not found"));

        VehicleModel vehicleModel = vehicleModelRepository.findById(updatedDetailsDTO.getVehicleModelId())
                .orElseThrow(() -> new EntityNotFoundException("Vehicle model with id " + updatedDetailsDTO.getVehicleModelId() + " not found"));

        existingDetails.setVehicleModel(vehicleModel);
        existingDetails.setYear(updatedDetailsDTO.getYear());
        existingDetails.setVin(updatedDetailsDTO.getVin());
        existingDetails.setPrice(updatedDetailsDTO.getPrice());

        Vehicle savedDetails = vehicleRepository.save(existingDetails);
        VehicleResource responseDTO = vehicleAssembler.toModel(savedDetails);
        return ResponseEntity.ok(responseDTO);
    }
}
