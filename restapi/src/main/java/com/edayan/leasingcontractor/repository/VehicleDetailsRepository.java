package com.edayan.leasingcontractor.repository;

import com.edayan.leasingcontractor.repository.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleDetailsRepository extends JpaRepository<Vehicle, Long> {
}
