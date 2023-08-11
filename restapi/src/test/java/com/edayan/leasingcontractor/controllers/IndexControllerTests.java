package com.edayan.leasingcontractor.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IndexController.class)
public class IndexControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetRoot() throws Exception {
        // Perform the GET request to /api and expect JSON content
        mockMvc.perform(get("/api").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/api"))
                .andExpect(jsonPath("$._links.vehicleBrands.href").value("http://localhost/api/vehicle-brands"))
                .andExpect(jsonPath("$._links.vehicles.href").value("http://localhost/api/vehicles"));
    }
}
