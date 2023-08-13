package com.edayan.leasingcontractor.controllers;


import com.edayan.leasingcontractor.models.VehicleDetailResource;
import com.edayan.leasingcontractor.models.VehicleResource;
import com.edayan.leasingcontractor.models.assemblers.VehicleAssembler;
import com.edayan.leasingcontractor.models.assemblers.VehicleDetailAssembler;
import com.edayan.leasingcontractor.repository.VehicleRepository;
import com.edayan.leasingcontractor.repository.entities.Vehicle;
import com.edayan.leasingcontractor.repository.entities.VehicleBrand;
import com.edayan.leasingcontractor.repository.entities.VehicleModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleRepository vehicleRepository;

    @MockBean
    private VehicleDetailAssembler vehicleDetailAssembler;

    @MockBean
    private VehicleAssembler vehicleAssembler;

    @Test
    public void testGetVehicleDetails() throws Exception {
        VehicleModel vehicleModel = new VehicleModel();
        vehicleModel.setId(1L);
        vehicleModel.setModelName("Camry");
        VehicleBrand vehicleBrand = new VehicleBrand();
        vehicleBrand.setBrandName("Toyota");
        vehicleModel.setBrand(vehicleBrand);

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId(1L);
        vehicle1.setVehicleModel(vehicleModel);
        vehicle1.setYear(2022);
        vehicle1.setVin("123456789");
        vehicle1.setPrice(25000.0);

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setId(2L);
        vehicle2.setVehicleModel(vehicleModel);
        vehicle2.setYear(2023);
        vehicle2.setVin("987654321");
        vehicle2.setPrice(28000.0);

        List<Vehicle> vehicleList = Arrays.asList(vehicle1, vehicle2);
        when(vehicleRepository.findAll()).thenReturn(vehicleList);

        VehicleDetailResource detailResource1 = new VehicleDetailResource();
        detailResource1.setModelName("Camry");
        detailResource1.setBrandName("Toyota");
        detailResource1.setYear(2022);
        detailResource1.setVin("123456789");
        detailResource1.setPrice(25000.0);

        VehicleDetailResource detailResource2 = new VehicleDetailResource();
        detailResource2.setModelName("Camry");
        detailResource2.setBrandName("Toyota");
        detailResource2.setYear(2023);
        detailResource2.setVin("987654321");
        detailResource2.setPrice(28000.0);

        when(vehicleDetailAssembler.toModel(vehicle1)).thenReturn(detailResource1);
        when(vehicleDetailAssembler.toModel(vehicle2)).thenReturn(detailResource2);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.vehicleDetailResourceList[0].modelName").value("Camry"))
                .andExpect(jsonPath("$._embedded.vehicleDetailResourceList[0].brandName").value("Toyota"))
                .andExpect(jsonPath("$._embedded.vehicleDetailResourceList[1].modelName").value("Camry"))
                .andExpect(jsonPath("$._embedded.vehicleDetailResourceList[1].brandName").value("Toyota"))
                .andExpect(jsonPath("$._embedded.vehicleDetailResourceList[0].year").value(2022))
                .andExpect(jsonPath("$._embedded.vehicleDetailResourceList[0].vin").value("123456789"))
                .andExpect(jsonPath("$._embedded.vehicleDetailResourceList[0].price").value(25000.0))
                .andExpect(jsonPath("$._embedded.vehicleDetailResourceList[1].year").value(2023))
                .andExpect(jsonPath("$._embedded.vehicleDetailResourceList[1].vin").value("987654321"))
                .andExpect(jsonPath("$._embedded.vehicleDetailResourceList[1].price").value(28000.0))
                .andReturn();

        verify(vehicleRepository, times(1)).findAll();
        verifyNoMoreInteractions(vehicleRepository);

        verify(vehicleDetailAssembler, times(1)).toModel(vehicle1);
        verify(vehicleDetailAssembler, times(1)).toModel(vehicle2);
        verifyNoMoreInteractions(vehicleDetailAssembler);
    }


    @Test
    public void testGetSingleVehicle() throws Exception {
        Long vehicleId = 1L;
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleId);
        vehicle.setYear(2023);
        vehicle.setVin("VIN123");
        vehicle.setPrice(25000);

        VehicleResource vehicleResource = new VehicleResource();
        vehicleResource.setId(vehicleId);
        vehicleResource.setYear(2023);
        vehicleResource.setVin("VIN123");
        vehicleResource.setPrice(25000);

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        when(vehicleAssembler.toModel(vehicle)).thenReturn(vehicleResource);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicles/{id}", vehicleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(vehicleId))
                .andExpect(jsonPath("$.year").value(2023))
                .andExpect(jsonPath("$.vin").value("VIN123"))
                .andExpect(jsonPath("$.price").value(25000))
                .andReturn();

        verify(vehicleRepository, times(1)).findById(vehicleId);
        verifyNoMoreInteractions(vehicleRepository);
        verify(vehicleAssembler, times(1)).toModel(vehicle);
        verifyNoMoreInteractions(vehicleAssembler);
    }
}
