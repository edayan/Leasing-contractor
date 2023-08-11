package com.edayan.leasingcontractor.controllers;


import com.edayan.leasingcontractor.models.UserResource;
import com.edayan.leasingcontractor.models.assemblers.UserResourceAssembler;
import com.edayan.leasingcontractor.repository.UserRepository;
import com.edayan.leasingcontractor.repository.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserResourceAssembler userResourceAssembler;

    @Test
    public void testGetAllUsers() throws Exception {
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setBirthDate(LocalDate.of(1990, 1, 15));

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setBirthDate(LocalDate.of(1985, 5, 10));

        List<User> userList = Arrays.asList(user1, user2);

        UserResource userResource1 = new UserResource();
        userResource1.setFirstName(user1.getFirstName());
        userResource1.setLastName(user1.getLastName());
        userResource1.setBirthDate(user1.getBirthDate());

        UserResource userResource2 = new UserResource();
        userResource2.setFirstName(user2.getFirstName());
        userResource2.setLastName(user2.getLastName());
        userResource2.setBirthDate(user2.getBirthDate());

        when(userRepository.findAll()).thenReturn(userList);
        when(userResourceAssembler.toModel(user1)).thenReturn(userResource1);
        when(userResourceAssembler.toModel(user2)).thenReturn(userResource2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.userResourceList[0].firstName").value("John"))
                .andExpect(jsonPath("$._embedded.userResourceList[1].firstName").value("Jane"));

        verify(userRepository, times(1)).findAll();
        verify(userResourceAssembler, times(1)).toModel(user1);
        verify(userResourceAssembler, times(1)).toModel(user2);
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(userResourceAssembler);
    }
}
