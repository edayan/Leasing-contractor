package com.edayan.leasingcontractor.repository.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "vehicle_details")
public class VehicleDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicle_model_id", nullable = false)
    private VehicleModel vehicleModel;

    private int year;
    private String vin;
    private double price;
}
