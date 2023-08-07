package com.edayan.leasingcontractor.controllers;

import com.edayan.leasingcontractor.models.VehicleDTO;
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
    public ResponseEntity<CollectionModel<VehicleDTO>> getVehicleDetails() {
        List<VehicleDTO> vehicles = vehicleDetailsRepository.findAll().stream()
                .map(vehicleAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(vehicles));
    }

    @PostMapping("/vehicles")
    public ResponseEntity<VehicleDTO> createVehicleDetails(@RequestBody @Valid VehicleDTO vehicleDTO) {
        VehicleModel vehicleModel = vehicleModelRepository.findById(vehicleDTO.getVehicleModelId())
                .orElseThrow(() -> new EntityNotFoundException("Vehicle model with id " + vehicleDTO.getVehicleModelId() + " not found"));

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleModel(vehicleModel);
        vehicle.setYear(vehicleDTO.getYear());
        vehicle.setVin(vehicleDTO.getVin());
        vehicle.setPrice(vehicleDTO.getPrice());

        Vehicle savedDetails = vehicleDetailsRepository.save(vehicle);
        VehicleDTO responseDTO = vehicleAssembler.toModel(savedDetails);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/vehicles/{id}")
    public ResponseEntity<VehicleDTO> updateVehicleDetails(@PathVariable Long id, @RequestBody VehicleDTO updatedDetailsDTO) {
        Vehicle existingDetails = vehicleDetailsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle details with id " + id + " not found"));

        VehicleModel vehicleModel = vehicleModelRepository.findById(updatedDetailsDTO.getVehicleModelId())
                .orElseThrow(() -> new EntityNotFoundException("Vehicle model with id " + updatedDetailsDTO.getVehicleModelId() + " not found"));

        existingDetails.setVehicleModel(vehicleModel);
        existingDetails.setYear(updatedDetailsDTO.getYear());
        existingDetails.setVin(updatedDetailsDTO.getVin());
        existingDetails.setPrice(updatedDetailsDTO.getPrice());

        Vehicle savedDetails = vehicleDetailsRepository.save(existingDetails);
        VehicleDTO responseDTO = vehicleAssembler.toModel(savedDetails);
        return ResponseEntity.ok(responseDTO);
    }
}
