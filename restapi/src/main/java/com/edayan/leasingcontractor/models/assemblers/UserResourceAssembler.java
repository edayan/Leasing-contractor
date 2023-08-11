package com.edayan.leasingcontractor.models.assemblers;

import com.edayan.leasingcontractor.controllers.UserController;
import com.edayan.leasingcontractor.models.UserResource;
import com.edayan.leasingcontractor.repository.entities.User;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UserResourceAssembler extends RepresentationModelAssemblerSupport<User, UserResource> {


    public UserResourceAssembler() {
        super(UserController.class, UserResource.class);
    }

    @Override
    public UserResource toModel(User entity) {
        UserResource resource = instantiateModel(entity);
        resource.setId(entity.getId());
        resource.setFirstName(entity.getFirstName());
        resource.setLastName(entity.getLastName());
        resource.setBirthDate(entity.getBirthDate());
        return resource;
    }
}
