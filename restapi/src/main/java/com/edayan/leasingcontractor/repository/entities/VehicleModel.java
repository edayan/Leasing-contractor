package com.edayan.leasingcontractor.repository.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "vehicle_models")
public class VehicleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String modelName;

    @ManyToOne
    private VehicleBrand brand;
}
