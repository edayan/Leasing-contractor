package com.edayan.leasingcontractor.repository;

import com.edayan.leasingcontractor.repository.entities.LeasingContract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeasingContractRepository extends JpaRepository<LeasingContract, Long> {

}
