package com.edayan.leasingcontractor.models;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Data
public class UserResource extends RepresentationModel<UserResource> {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
}
