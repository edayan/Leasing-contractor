package com.edayan.leasingcontractor.repository.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "vehicle_brands")
public class VehicleBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brandName;


}