package com.edayan.leasingcontractor.controllers;


import com.edayan.leasingcontractor.models.UserResource;
import com.edayan.leasingcontractor.models.assemblers.UserResourceAssembler;
import com.edayan.leasingcontractor.repository.UserRepository;
import com.edayan.leasingcontractor.repository.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;

    private final UserResourceAssembler userResourceAssembler;

    @Autowired
    public UserController(UserRepository userRepository,
                          UserResourceAssembler userResourceAssembler) {
        this.userRepository = userRepository;
        this.userResourceAssembler = userResourceAssembler;
    }

    @GetMapping("/users")
    public ResponseEntity<CollectionModel<UserResource>> getAllUsers() {
        final List<UserResource> users = userRepository.findAll().stream()
                .map(userResourceAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(users));
    }

    @PostMapping("/users")
    public ResponseEntity<EntityModel<UserResource>> createUser(@RequestBody @Valid UserResource userResource) {
        User user = new User();
        user.setFirstName(userResource.getFirstName());
        user.setLastName(userResource.getLastName());
        user.setBirthDate(userResource.getBirthDate());

        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(EntityModel.of(userResourceAssembler.toModel(savedUser)));
    }
}
