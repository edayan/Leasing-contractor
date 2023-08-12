package com.edayan.leasingcontractor.models;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class LeasingContractResource extends RepresentationModel<LeasingContractResource> {

    private Long contractNumber;

    @NotNull(message = "Monthly rate required")
    private BigDecimal monthlyRate;

    @NotNull(message = "Customer Id required")
    private Long customerId;

    @NotNull(message = "Vehicle Id number required")
    private Long vehicleId;
}
