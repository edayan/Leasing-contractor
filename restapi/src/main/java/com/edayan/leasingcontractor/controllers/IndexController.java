package com.edayan.leasingcontractor.controllers;

import com.edayan.leasingcontractor.models.RootResource;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class IndexController {
    @GetMapping
    public ResponseEntity<EntityModel<RootResource>> getRoot() {
        RootResource rootResource = new RootResource();
        return ResponseEntity.ok(EntityModel.of(rootResource));
    }
}
