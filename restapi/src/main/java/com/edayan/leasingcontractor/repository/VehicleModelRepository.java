package com.edayan.leasingcontractor.repository;

import com.edayan.leasingcontractor.repository.entities.VehicleBrand;
import com.edayan.leasingcontractor.repository.entities.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleModelRepository extends JpaRepository<VehicleModel, Long> {
    List<VehicleModel> findByBrand(VehicleBrand vehicleBrand);
}
