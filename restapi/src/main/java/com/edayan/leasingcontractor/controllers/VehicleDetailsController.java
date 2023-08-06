package com.edayan.leasingcontractor.controllers;

import com.edayan.leasingcontractor.models.VehicleDetailsDTO;
import com.edayan.leasingcontractor.models.assemblers.VehicleDetailsAssembler;
import com.edayan.leasingcontractor.repository.VehicleDetailsRepository;
import com.edayan.leasingcontractor.repository.VehicleModelRepository;
import com.edayan.leasingcontractor.repository.entities.VehicleDetails;
import com.edayan.leasingcontractor.repository.entities.VehicleModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("api")
public class VehicleDetailsController {
    private final VehicleDetailsRepository vehicleDetailsRepository;
    private final VehicleModelRepository vehicleModelRepository;

    private final VehicleDetailsAssembler vehicleDetailsAssembler;

    public VehicleDetailsController(VehicleDetailsRepository vehicleDetailsRepository, VehicleModelRepository vehicleModelRepository, VehicleDetailsAssembler vehicleDetailsAssembler) {
        this.vehicleDetailsRepository = vehicleDetailsRepository;
        this.vehicleModelRepository = vehicleModelRepository;
        this.vehicleDetailsAssembler = vehicleDetailsAssembler;
    }

    @PostMapping("/vehicle-details")
    public ResponseEntity<VehicleDetailsDTO> createVehicleDetails(@RequestBody VehicleDetailsDTO vehicleDetailsDTO) {
        VehicleModel vehicleModel = vehicleModelRepository.findById(vehicleDetailsDTO.getVehicleModelId())
                .orElseThrow(() -> new EntityNotFoundException("Vehicle model with id " + vehicleDetailsDTO.getVehicleModelId() + " not found"));

        VehicleDetails vehicleDetails = new VehicleDetails();
        vehicleDetails.setVehicleModel(vehicleModel);
        vehicleDetails.setYear(vehicleDetailsDTO.getYear());
        vehicleDetails.setVin(vehicleDetailsDTO.getVin());
        vehicleDetails.setPrice(vehicleDetailsDTO.getPrice());

        VehicleDetails savedDetails = vehicleDetailsRepository.save(vehicleDetails);
        VehicleDetailsDTO responseDTO = vehicleDetailsAssembler.toModel(savedDetails);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/vehicle-details/{id}")
    public ResponseEntity<VehicleDetailsDTO> updateVehicleDetails(@PathVariable Long id, @RequestBody VehicleDetailsDTO updatedDetailsDTO) {
        VehicleDetails existingDetails = vehicleDetailsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle details with id " + id + " not found"));

        existingDetails.setYear(updatedDetailsDTO.getYear());
        existingDetails.setVin(updatedDetailsDTO.getVin());
        existingDetails.setPrice(updatedDetailsDTO.getPrice());

        VehicleDetails savedDetails = vehicleDetailsRepository.save(existingDetails);
        VehicleDetailsDTO responseDTO = vehicleDetailsAssembler.toModel(savedDetails);
        return ResponseEntity.ok(responseDTO);
    }
}
