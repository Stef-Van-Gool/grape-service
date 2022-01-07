package com.example.grapesservice;

import com.example.grapesservice.model.Grape;
import com.example.grapesservice.repository.GrapeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class grapeControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GrapeRepository grapeRepository;

    private Grape grape1 = new Grape("Fernao Pires", "spanje", "catalonië");
    private Grape grape2 = new Grape("semillon", "italie", "sangio");
    private Grape grape3 = new Grape("Merlot", "frankrijk", "champagne");
    private Grape grape4 = new Grape("Sangiovese", "frankrijk", "sangio");

    @BeforeEach
    public void beforAllTests() {
        grapeRepository.deleteAll();
        grapeRepository.save(grape1);
        grapeRepository.save(grape2);
        grapeRepository.save(grape3);
        grapeRepository.save(grape4);
    }

    @AfterEach
    public void afterAllTests(){
        grapeRepository.deleteAll();
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenGrapeName_whenGetGrapeList_thenReturnJsonGrapeList() throws Exception {
        mockMvc.perform(get("/grapes/grapename/{grapeName}", "se"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].grapeName", is("semillon")))
            .andExpect(jsonPath("$[0].region", is("italie")))
            .andExpect(jsonPath("$[0].country", is("sangio")))
            .andExpect(jsonPath("$[1].grapeName", is("Sangiovese")))
            .andExpect(jsonPath("$[1].region", is("frankrijk")))
            .andExpect(jsonPath("$[1].country", is("sangio")));
    }

    @Test
    public void givenRegion_whenGetGrapeList_thenReturnJsonGrapeList() throws Exception {
        mockMvc.perform(get("/grapes/region/{region}", "frankrijk"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].grapeName", is("Merlot")))
            .andExpect(jsonPath("$[0].region", is("frankrijk")))
            .andExpect(jsonPath("$[0].country", is("champagne")))
            .andExpect(jsonPath("$[1].grapeName", is("Sangiovese")))
            .andExpect(jsonPath("$[1].region", is("frankrijk")))
            .andExpect(jsonPath("$[1].country", is("sangio")));
    }

    @Test
    public void givenCountry_whenGetGrapeList_thenReturnJsonGrapeList() throws Exception {
        mockMvc.perform(get("/grapes/country/{country}", "sangio"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].grapeName", is("semillon")))
            .andExpect(jsonPath("$[0].region", is("italie")))
            .andExpect(jsonPath("$[0].country", is("sangio")))
            .andExpect(jsonPath("$[1].grapeName", is("Sangiovese")))
            .andExpect(jsonPath("$[1].region", is("frankrijk")))
            .andExpect(jsonPath("$[1].country", is("sangio")));
    }

    @Test
    public void whenPostGrape_thenReturnJsonGrape() throws Exception {
        Grape newGrape= new Grape("newGrapeName", "newRegion", "newCountry");

        mockMvc.perform(post("/grapes")
            .content(mapper.writeValueAsString(newGrape))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.grapeName", is("newGrapeName")))
            .andExpect(jsonPath("$.region", is("newRegion")))
            .andExpect(jsonPath("$.country", is("newCountry")));
    }

    @Test
    public void givenGrape_whenPutGrape_thenReturnJsonGrape() throws Exception {
        Grape updatedGrape = new Grape("Merlot", "updatedRegion", "updatedCountry");

        mockMvc.perform(put("/grapes")
            .content(mapper.writeValueAsString(updatedGrape))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.grapeName", is("Merlot")))
            .andExpect(jsonPath("$.region", is("updatedRegion")))
            .andExpect(jsonPath("$.country", is("updatedCountry")));
    }

    @Test
    public void givenGrape_whenDeleteGrape_thenStatusOk() throws Exception {
        Grape newGrape= new Grape("newGrapeName", "newRegion", "newCountry");
        grapeRepository.save(newGrape);

        mockMvc.perform(delete("/grapes/grapename/{grapeName}", "newGrapeName")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenGrape_whenDeleteGrape_thenStatusNotFound() throws Exception {

        mockMvc.perform(delete("/grapes/grapename/{grapeName}", "newGrapeName")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}


