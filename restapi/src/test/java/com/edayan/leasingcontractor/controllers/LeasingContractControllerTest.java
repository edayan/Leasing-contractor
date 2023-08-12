package com.edayan.leasingcontractor.controllers;

import com.edayan.leasingcontractor.models.LeasingContractResource;
import com.edayan.leasingcontractor.models.assemblers.LeasingContractResourceAssembler;
import com.edayan.leasingcontractor.repository.CustomerRepository;
import com.edayan.leasingcontractor.repository.LeasingContractRepository;
import com.edayan.leasingcontractor.repository.VehicleRepository;
import com.edayan.leasingcontractor.repository.entities.Customer;
import com.edayan.leasingcontractor.repository.entities.LeasingContract;
import com.edayan.leasingcontractor.repository.entities.Vehicle;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LeasingContractControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LeasingContractRepository leasingContractRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private VehicleRepository vehicleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LeasingContractResourceAssembler leasingContractResourceAssembler;

    @Test
    public void testCreateLeasingContract_Success() throws Exception {
        LeasingContractResource inputResource = new LeasingContractResource();
        inputResource.setMonthlyRate(new BigDecimal("500"));
        inputResource.setCustomerId(1L);
        inputResource.setVehicleId(2L);

        Customer customer = new Customer();
        customer.setId(1L);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(2L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(vehicleRepository.findById(2L)).thenReturn(Optional.of(vehicle));

        LeasingContract createdContract = new LeasingContract();
        createdContract.setId(1L);
        createdContract.setMonthlyRate(new BigDecimal("500"));
        createdContract.setCustomer(customer);
        createdContract.setVehicle(vehicle);

        when(leasingContractRepository.save(any(LeasingContract.class))).thenReturn(createdContract);

        LeasingContractResource responseResource = new LeasingContractResource();
        responseResource.setMonthlyRate(new BigDecimal("500"));
        responseResource.setCustomerId(1L);
        responseResource.setVehicleId(2L);

        when(leasingContractResourceAssembler.toModel(createdContract)).thenReturn(responseResource);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/leasing-contracts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputResource)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monthlyRate").value(500))
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.vehicleId").value(2))
                .andReturn();

        verify(customerRepository, times(1)).findById(1L);
        verify(vehicleRepository, times(1)).findById(2L);
        verify(leasingContractRepository, times(1)).save(any(LeasingContract.class));
        verify(leasingContractResourceAssembler, times(1)).toModel(createdContract);
    }


    @Test
    public void testGetAllLeasingContracts() throws Exception {
        LeasingContract contract1 = new LeasingContract();
        contract1.setId(1L);
        contract1.setMonthlyRate(new BigDecimal("500"));

        LeasingContract contract2 = new LeasingContract();
        contract2.setId(2L);
        contract2.setMonthlyRate(new BigDecimal("600"));

        List<LeasingContract> contractList = Arrays.asList(contract1, contract2);
        when(leasingContractRepository.findAll()).thenReturn(contractList);

        LeasingContractResource resource1 = new LeasingContractResource();
        resource1.setMonthlyRate(new BigDecimal("500"));

        LeasingContractResource resource2 = new LeasingContractResource();
        resource2.setMonthlyRate(new BigDecimal("600"));

        when(leasingContractResourceAssembler.toModel(contract1)).thenReturn(resource1);
        when(leasingContractResourceAssembler.toModel(contract2)).thenReturn(resource2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/leasing-contracts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.leasingContractResourceList[0].monthlyRate").value("500"))
                .andExpect(jsonPath("$._embedded.leasingContractResourceList[1].monthlyRate").value("600"))
                .andReturn();

        verify(leasingContractRepository, times(1)).findAll();
        verifyNoMoreInteractions(leasingContractRepository);
        verify(leasingContractResourceAssembler, times(1)).toModel(contract1);
        verify(leasingContractResourceAssembler, times(1)).toModel(contract2);
        verifyNoMoreInteractions(leasingContractResourceAssembler);
    }
}
