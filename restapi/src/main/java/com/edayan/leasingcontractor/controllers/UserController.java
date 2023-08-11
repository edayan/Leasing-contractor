package com.edayan.leasingcontractor.controllers;


import com.edayan.leasingcontractor.models.UserResource;
import com.edayan.leasingcontractor.models.assemblers.UserResourceAssembler;
import com.edayan.leasingcontractor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    private final UserResourceAssembler userResourceAssembler;

    @Autowired
    public UserController(UserRepository userRepository,
                          UserResourceAssembler userResourceAssembler) {
        this.userRepository = userRepository;
        this.userResourceAssembler = userResourceAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<UserResource>> getAllUsers() {
        final List<UserResource> users = userRepository.findAll().stream()
                .map(userResourceAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(users));
    }
}
