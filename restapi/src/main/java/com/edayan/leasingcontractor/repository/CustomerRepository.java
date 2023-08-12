package com.edayan.leasingcontractor.repository;

import com.edayan.leasingcontractor.repository.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
