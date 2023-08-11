package com.edayan.leasingcontractor.controllers;

import com.edayan.leasingcontractor.models.VehicleBrandResource;
import com.edayan.leasingcontractor.models.VehicleModelResource;
import com.edayan.leasingcontractor.models.assemblers.VehicleBrandAssembler;
import com.edayan.leasingcontractor.models.assemblers.VehicleModelAssembler;
import com.edayan.leasingcontractor.repository.VehicleBrandRepository;
import com.edayan.leasingcontractor.repository.VehicleModelRepository;
import com.edayan.leasingcontractor.repository.entities.VehicleBrand;
import com.edayan.leasingcontractor.repository.entities.VehicleModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class VehicleBrandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleBrandRepository vehicleBrandRepository;

    @MockBean
    private VehicleBrandAssembler vehicleBrandAssembler;

    @MockBean
    private VehicleModelRepository vehicleModelRepository;

    @MockBean
    private VehicleModelAssembler vehicleModelAssembler;

    @Test
    public void testGetAllVehicleBrands() throws Exception {
        VehicleBrand brand1 = new VehicleBrand();
        brand1.setId(1L);
        brand1.setBrandName("Brand 1");

        VehicleBrand brand2 = new VehicleBrand();
        brand2.setId(2L);
        brand2.setBrandName("Brand 2");

        List<VehicleBrand> brandList = Arrays.asList(brand1, brand2);
        when(vehicleBrandRepository.findAll()).thenReturn(brandList);

        VehicleBrandResource resource1 = new VehicleBrandResource();
        resource1.setBrandName("Brand 1");

        VehicleBrandResource resource2 = new VehicleBrandResource();
        resource2.setBrandName("Brand 2");

        when(vehicleBrandAssembler.toModel(brand1)).thenReturn(resource1);
        when(vehicleBrandAssembler.toModel(brand2)).thenReturn(resource2);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicle-brands")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.vehicleBrandResourceList[0].brandName").value("Brand 1"))
                .andExpect(jsonPath("$._embedded.vehicleBrandResourceList[1].brandName").value("Brand 2"))
                .andReturn();

        verify(vehicleBrandRepository, times(1)).findAll();
        verify(vehicleBrandAssembler, times(1)).toModel(brand1);
        verify(vehicleBrandAssembler, times(1)).toModel(brand2);
    }

    @Test
    public void testGetVehicleBrand() throws Exception {
        VehicleBrand vehicleBrand = new VehicleBrand();
        vehicleBrand.setId(1L);
        vehicleBrand.setBrandName("Toyota");

        when(vehicleBrandRepository.findById(1L)).thenReturn(Optional.of(vehicleBrand));

        VehicleBrandResource resource = new VehicleBrandResource();
        resource.setBrandName("Toyota");

        when(vehicleBrandAssembler.toModel(vehicleBrand)).thenReturn(resource);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicle-brands/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brandName").value("Toyota"))
                .andReturn();

        verify(vehicleBrandRepository, times(1)).findById(1L);
        verify(vehicleBrandAssembler, times(1)).toModel(vehicleBrand);
    }

    @Test
    public void testGetModelByBrand() throws Exception {
        VehicleBrand vehicleBrand = new VehicleBrand();
        vehicleBrand.setId(1L);
        vehicleBrand.setBrandName("Toyota");

        VehicleModel vehicleModel1 = new VehicleModel();
        vehicleModel1.setId(101L);
        vehicleModel1.setModelName("Camry");
        vehicleModel1.setBrand(vehicleBrand);

        VehicleModel vehicleModel2 = new VehicleModel();
        vehicleModel2.setId(102L);
        vehicleModel2.setModelName("Corolla");
        vehicleModel2.setBrand(vehicleBrand);

        List<VehicleModel> vehicleModelList = Arrays.asList(vehicleModel1, vehicleModel2);

        when(vehicleBrandRepository.findById(1L)).thenReturn(Optional.of(vehicleBrand));
        when(vehicleModelRepository.findByBrand(vehicleBrand)).thenReturn(vehicleModelList);

        VehicleModelResource modelResource1 = new VehicleModelResource();
        modelResource1.setModelName("Camry");

        VehicleModelResource modelResource2 = new VehicleModelResource();
        modelResource2.setModelName("Corolla");

        when(vehicleModelAssembler.toModel(vehicleModel1)).thenReturn(modelResource1);
        when(vehicleModelAssembler.toModel(vehicleModel2)).thenReturn(modelResource2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicle-brands/1/models")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.vehicleModelResourceList[*].modelName", containsInAnyOrder("Camry", "Corolla")))
                .andReturn();

    }
}
