package com.edayan.leasingcontractor.repository.entities;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data

public class LeasingContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "monthly_rate", nullable = false)
    private BigDecimal monthlyRate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
}
