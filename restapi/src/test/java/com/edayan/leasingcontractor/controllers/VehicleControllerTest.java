package com.edayan.leasingcontractor.controllers;


import com.edayan.leasingcontractor.models.VehicleResource;
import com.edayan.leasingcontractor.models.assemblers.VehicleAssembler;
import com.edayan.leasingcontractor.repository.VehicleRepository;
import com.edayan.leasingcontractor.repository.entities.Vehicle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleRepository vehicleRepository;

    @MockBean
    private VehicleAssembler vehicleAssembler;

    @Test
    public void testGetVehicleDetails() throws Exception {
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId(1L);
        vehicle1.setYear(2022);
        vehicle1.setVin("VIN123");
        vehicle1.setPrice(25000.0);

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setId(2L);
        vehicle2.setYear(2021);
        vehicle2.setVin("VIN456");
        vehicle2.setPrice(30000.0);

        List<Vehicle> vehicleList = Arrays.asList(vehicle1, vehicle2);

        when(vehicleRepository.findAll()).thenReturn(vehicleList);

        VehicleResource resource1 = new VehicleResource();
        resource1.setYear(2022);
        resource1.setVin("VIN123");
        resource1.setPrice(25000.0);

        VehicleResource resource2 = new VehicleResource();
        resource2.setYear(2021);
        resource2.setVin("VIN456");
        resource2.setPrice(30000.0);

        when(vehicleAssembler.toModel(vehicle1)).thenReturn(resource1);
        when(vehicleAssembler.toModel(vehicle2)).thenReturn(resource2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.vehicleResourceList[0].year").value(2022))
                .andExpect(jsonPath("$._embedded.vehicleResourceList[1].year").value(2021))
                .andExpect(jsonPath("$._embedded.vehicleResourceList[0].vin").value("VIN123"))
                .andExpect(jsonPath("$._embedded.vehicleResourceList[1].vin").value("VIN456"))
                .andExpect(jsonPath("$._embedded.vehicleResourceList[0].price").value(25000.0))
                .andExpect(jsonPath("$._embedded.vehicleResourceList[1].price").value(30000.0))
                .andReturn();

        verify(vehicleRepository, times(1)).findAll();
        verifyNoMoreInteractions(vehicleRepository);
        verify(vehicleAssembler, times(1)).toModel(vehicle1);
        verify(vehicleAssembler, times(1)).toModel(vehicle2);
        verifyNoMoreInteractions(vehicleAssembler);
    }
}
