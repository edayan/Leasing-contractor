package com.edayan.leasingcontractor.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CustomerResource extends RepresentationModel<CustomerResource> {
    private Long id;

    @NotNull(message = "firstName is required")
    private String firstName;

    @NotNull(message = "lastName is required")
    private String lastName;

    @NotNull(message = "birthDate is required")
    private LocalDate birthDate;
}
