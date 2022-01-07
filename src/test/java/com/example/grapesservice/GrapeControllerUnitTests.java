package com.example.grapesservice;

import com.example.grapesservice.model.Grape;
import com.example.grapesservice.repository.GrapeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GrapeControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GrapeRepository grapeRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenGrapeName_whenGetGrapesByGrapeName_thenReturnGrapes() throws Exception {
        Grape grape1 = new Grape("semillon", "italie", "sangio");
        Grape grape2 = new Grape("Sangiovese", "frankrijk", "sangio");

        List<Grape> grapeList = new ArrayList<>();
        grapeList.add(grape1);
        grapeList.add(grape2);

        given(grapeRepository.findGrapeByGrapeNameContaining("se")).willReturn(grapeList);

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
    public void givenRegion_whenGetGrapesByRegion_thenReturnGrapes() throws Exception {
        Grape grape1 = new Grape("semillon", "frankrijk", "sangio");
        Grape grape2 = new Grape("Sangiovese", "frankrijk", "sangio");

        List<Grape> grapeList = new ArrayList<>();
        grapeList.add(grape1);
        grapeList.add(grape2);

        given(grapeRepository.findGrapeByRegionContaining("frankrijk")).willReturn(grapeList);

        mockMvc.perform(get("/grapes/region/{region}", "frankrijk"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].grapeName", is("semillon")))
            .andExpect(jsonPath("$[0].region", is("frankrijk")))
            .andExpect(jsonPath("$[0].country", is("sangio")))

            .andExpect(jsonPath("$[1].grapeName", is("Sangiovese")))
            .andExpect(jsonPath("$[1].region", is("frankrijk")))
            .andExpect(jsonPath("$[1].country", is("sangio")));
    }

    @Test
    public void givenCountry_whenGetGrapesByCountry_thenReturnGrapes() throws Exception {
        Grape grape1 = new Grape("semillon", "italie", "sangio");
        Grape grape2 = new Grape("Sangiovese", "frankrijk", "sangio");

        List<Grape> grapeList = new ArrayList<>();
        grapeList.add(grape1);
        grapeList.add(grape2);

        given(grapeRepository.findGrapeByCountryContaining("sangio")).willReturn(grapeList);

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
    public void whenPostGrape_thenReturnJsonGrape() throws Exception{
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
        Grape grape = new Grape("Merlot", "region", "country");

        given(grapeRepository.findGrapeByGrapeName("Merlot")).willReturn(grape);

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
        Grape newGrape = new Grape("newGrapeName", "newRegion", "newCountry");
        grapeRepository.save(newGrape);

        given(grapeRepository.findGrapeByGrapeName("newGrapeName")).willReturn(newGrape);

        mockMvc.perform(delete("/grapes/grapename/{grapeName}", "newGrapeName")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenGrape_whenDeleteGrape_thenStatusNotFound() throws Exception {
        given(grapeRepository.findGrapeByGrapeName("newGrapeName")).willReturn(null);

        mockMvc.perform(delete("/grapes/grapename/{grapeName}", "newGrapeName")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
