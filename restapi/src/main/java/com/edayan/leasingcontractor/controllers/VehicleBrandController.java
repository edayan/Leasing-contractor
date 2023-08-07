package com.edayan.leasingcontractor.controllers;

import com.edayan.leasingcontractor.models.VehicleBrandResource;
import com.edayan.leasingcontractor.models.VehicleModelResource;
import com.edayan.leasingcontractor.models.assemblers.VehicleBrandAssembler;
import com.edayan.leasingcontractor.models.assemblers.VehicleModelAssembler;
import com.edayan.leasingcontractor.repository.VehicleBrandRepository;
import com.edayan.leasingcontractor.repository.VehicleModelRepository;
import com.edayan.leasingcontractor.repository.entities.VehicleBrand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
//Ideally should support versioning
@RequestMapping("/api")
public class VehicleBrandController {

    private final VehicleBrandRepository vehicleBrandRepository;
    private final VehicleBrandAssembler vehicleBrandAssembler;
    private final VehicleModelRepository vehicleModelRepository;
    private final VehicleModelAssembler vehicleModelAssembler;

    @Autowired
    public VehicleBrandController(VehicleBrandRepository vehicleBrandRepository,
                                  VehicleBrandAssembler vehicleBrandAssembler,
                                  VehicleModelRepository vehicleModelRepository,
                                  VehicleModelAssembler vehicleModelAssembler) {
        this.vehicleBrandRepository = vehicleBrandRepository;
        this.vehicleBrandAssembler = vehicleBrandAssembler;
        this.vehicleModelRepository = vehicleModelRepository;
        this.vehicleModelAssembler = vehicleModelAssembler;
    }

    @GetMapping("/vehicle-brands")
    public ResponseEntity<CollectionModel<VehicleBrandResource>> getAllVehicleBrands() {
        List<VehicleBrandResource> vehicleBrands = vehicleBrandRepository.findAll().stream()
                .map(vehicleBrandAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(vehicleBrands));
    }

    @GetMapping("/vehicle-brands/{id}")
    public ResponseEntity<EntityModel<VehicleBrandResource>> getVehicleBrand(@PathVariable Long id) {
        VehicleBrand vehicleBrand = vehicleBrandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle brand with id " + id + " not found"));

        VehicleBrandResource vehicleBrandResource = vehicleBrandAssembler.toModel(vehicleBrand);

        return ResponseEntity.ok(EntityModel.of(vehicleBrandResource));
    }

    @GetMapping("/vehicle-brands/{id}/models")
    public ResponseEntity<CollectionModel<VehicleModelResource>> getModelByBrand(@PathVariable Long id) {
        VehicleBrand vehicleBrand = vehicleBrandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle brand with id " + id + " not found"));

        List<VehicleModelResource> vehicleModels = vehicleModelRepository.findByBrand(vehicleBrand).stream()
                .map(vehicleModelAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(vehicleModels));
    }
}
