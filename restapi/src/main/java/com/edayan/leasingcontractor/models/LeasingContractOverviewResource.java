package com.edayan.leasingcontractor.models;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Data
public class LeasingContractOverviewResource extends RepresentationModel<LeasingContractOverviewResource> {

    private Long contractId;
    private CustomerResource customer;
    private VehicleDetailResource vehicle;
    private BigDecimal monthlyRate;
}
