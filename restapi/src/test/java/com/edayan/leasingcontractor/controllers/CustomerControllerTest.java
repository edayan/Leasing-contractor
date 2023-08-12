package com.edayan.leasingcontractor.controllers;


import com.edayan.leasingcontractor.models.CustomerResource;
import com.edayan.leasingcontractor.models.assemblers.CustomerResourceAssembler;
import com.edayan.leasingcontractor.repository.CustomerRepository;
import com.edayan.leasingcontractor.repository.entities.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private CustomerResourceAssembler customerResourceAssembler;

    @Test
    public void testGetAllUsers() throws Exception {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("John");
        customer1.setLastName("Doe");
        customer1.setBirthDate(LocalDate.of(1990, 1, 15));

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("Jane");
        customer2.setLastName("Smith");
        customer2.setBirthDate(LocalDate.of(1985, 5, 10));

        List<Customer> customerList = Arrays.asList(customer1, customer2);

        CustomerResource customerResource1 = new CustomerResource();
        customerResource1.setFirstName(customer1.getFirstName());
        customerResource1.setLastName(customer1.getLastName());
        customerResource1.setBirthDate(customer1.getBirthDate());

        CustomerResource customerResource2 = new CustomerResource();
        customerResource2.setFirstName(customer2.getFirstName());
        customerResource2.setLastName(customer2.getLastName());
        customerResource2.setBirthDate(customer2.getBirthDate());

        when(customerRepository.findAll()).thenReturn(customerList);
        when(customerResourceAssembler.toModel(customer1)).thenReturn(customerResource1);
        when(customerResourceAssembler.toModel(customer2)).thenReturn(customerResource2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.customerResourceList[0].firstName").value("John"))
                .andExpect(jsonPath("$._embedded.customerResourceList[1].firstName").value("Jane"));

        verify(customerRepository, times(1)).findAll();
        verify(customerResourceAssembler, times(1)).toModel(customer1);
        verify(customerResourceAssembler, times(1)).toModel(customer2);
        verifyNoMoreInteractions(customerRepository);
        verifyNoMoreInteractions(customerResourceAssembler);
    }

    @Test
    public void testCreateCustomer() throws Exception {
        CustomerResource customerResource = new CustomerResource();
        customerResource.setFirstName("Saju");
        customerResource.setLastName("Edayan");
        customerResource.setBirthDate(LocalDate.of(1990, 1, 15));

        when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(new Customer());
        when(customerResourceAssembler.toModel(Mockito.any(Customer.class))).thenReturn(customerResource);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerResource)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Saju"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Edayan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthDate").value("1990-01-15"));
    }

    @Test
    public void testGetCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setBirthDate(LocalDate.of(1990, 1, 1));

        CustomerResource customerResource = new CustomerResource();
        customerResource.setId(customer.getId());
        customerResource.setFirstName(customer.getFirstName());
        customerResource.setLastName(customer.getLastName());
        customerResource.setBirthDate(customer.getBirthDate());

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(customerResourceAssembler.toModel(customer)).thenReturn(customerResource);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.birthDate").value("1990-01-01"))
                .andReturn();
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        Customer existingCustomer = new Customer();
        existingCustomer.setId(1L);
        existingCustomer.setFirstName("John");
        existingCustomer.setLastName("Doe");
        existingCustomer.setBirthDate(LocalDate.of(1990, 1, 1));

        CustomerResource updatedCustomerResource = new CustomerResource();
        updatedCustomerResource.setId(existingCustomer.getId());
        updatedCustomerResource.setFirstName("UpdatedFirstName");
        updatedCustomerResource.setLastName("UpdatedLastName");
        updatedCustomerResource.setBirthDate(LocalDate.of(1995, 5, 5));

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(existingCustomer)).thenReturn(existingCustomer);
        when(customerResourceAssembler.toModel(existingCustomer)).thenReturn(updatedCustomerResource);

        String jsonPayload = objectMapper.writeValueAsString(updatedCustomerResource);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/customers/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("UpdatedFirstName"))
                .andExpect(jsonPath("$.lastName").value("UpdatedLastName"))
                .andExpect(jsonPath("$.birthDate").value("1995-05-05"))
                .andReturn();
    }
}
