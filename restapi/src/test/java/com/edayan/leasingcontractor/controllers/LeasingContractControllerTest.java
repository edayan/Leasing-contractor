package com.edayan.leasingcontractor.controllers;

import com.edayan.leasingcontractor.models.LeasingContractResource;
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
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

    @Test
    public void testCreateLeasingContract_ValidInput_ReturnsCreatedContract() throws Exception {
        Customer mockCustomer = new Customer();
        mockCustomer.setId(1L);
        mockCustomer.setFirstName("John");
        mockCustomer.setLastName("Doe");
        mockCustomer.setBirthDate(LocalDate.of(1990, 1, 1));

        Vehicle mockVehicle = new Vehicle();
        mockVehicle.setId(10L);


        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(mockCustomer));
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.of(mockVehicle));

        LeasingContract savedContract = new LeasingContract();
        savedContract.setId(1L);
        savedContract.setMonthlyRate(BigDecimal.valueOf(500));
        savedContract.setCustomer(mockCustomer);
        savedContract.setVehicle(mockVehicle);

        when(leasingContractRepository.save(any(LeasingContract.class))).thenReturn(savedContract);

        LeasingContractResource requestResource = new LeasingContractResource();
        requestResource.setMonthlyRate(BigDecimal.valueOf(500));
        requestResource.setCustomerId(1L);
        requestResource.setVehicleId(10L);

        String requestJson = objectMapper.writeValueAsString(requestResource);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/leasing-contracts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monthlyRate").value(500))
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.vehicleId").value(10));

        verify(customerRepository, times(1)).findById(anyLong());
        verify(vehicleRepository, times(1)).findById(anyLong());
        verify(leasingContractRepository, times(1)).save(any(LeasingContract.class));
    }
}
