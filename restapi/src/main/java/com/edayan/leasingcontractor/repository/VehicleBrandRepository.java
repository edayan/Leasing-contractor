package com.edayan.leasingcontractor.repository;

import com.edayan.leasingcontractor.repository.entities.VehicleBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleBrandRepository extends JpaRepository<VehicleBrand, Long> {

}
