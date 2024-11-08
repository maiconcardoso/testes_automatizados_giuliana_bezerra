package br.com.maicon.project.web;

import static br.com.maicon.project.common.PlanetConstants.PLANET;
import static br.com.maicon.project.common.PlanetConstants.PLANETS;
import static br.com.maicon.project.common.PlanetConstants.TATOOINE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.maicon.project.domain.Planet;
import br.com.maicon.project.services.PlanetService;

@WebMvcTest(PlanetController.class)
public class PlanetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PlanetService service;

    @Test
    public void createPlanet_WithValidData_ReturnsCreated() throws Exception {

        when(service.create(PLANET)).thenReturn(PLANET);

        mockMvc.perform(post("/planets").content(objectMapper.writeValueAsString(PLANET))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        // .andExpect(jsonPath("$").value(PLANET));
    }

    @Test
    public void createPlanet_WithInvalidData_ReturnsBadRequest() throws Exception {
        Planet emptyPlanet = new Planet();
        Planet invalidPlanet = new Planet("", "", "");

        mockMvc.perform(post("/planets").content(objectMapper.writeValueAsString(emptyPlanet))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

        mockMvc.perform(post("/planets").content(objectMapper.writeValueAsString(invalidPlanet))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void createPlanet_WithExistingName_ReturnsConflict() throws Exception {
        when(service.create(any())).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post("/planets").content(objectMapper.writeValueAsString(PLANET))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet() throws Exception {
        when(service.findById(1l)).thenReturn(Optional.of(PLANET));

        mockMvc.perform(get("/planets/1")).andExpect(status().isOk());
    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/planets/1")).andExpect(status().isNotFound());
    }

    @Test
    public void getPlanet_ByExistingName_ReturnsPlanet() throws Exception {
        when(service.findByName("name")).thenReturn(Optional.of(PLANET));

        mockMvc.perform(get("/planets/name/name")).andExpect(status().isOk());
    }

    @Test
    public void getPlanet_ByUnexistingName_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/planets/name/name")).andExpect(status().isNotFound());
    }

    @Test
    public void listPlanets_ReturnsFilteredPlanets() throws Exception {
        when(service.findAll("name", "terrain")).thenReturn(PLANETS);
        when(service.findAll(TATOOINE.getClimate(), TATOOINE.getTerrain())).thenReturn(List.of(TATOOINE));

        mockMvc.perform(get("/planets")).andExpect(status().isOk());

        mockMvc.perform(
                get("/planets?" + String.format("climate=%sterrain=%s", TATOOINE.getClimate(), TATOOINE.getTerrain())))
                .andExpect(status().isOk());
    }

    @Test
    public void listPlanets_ReturnsNoPlanets() throws Exception {
        when(service.findAll(null, null)).thenReturn(Collections.emptyList());
    }

    @Test
    public void removePlanet_WithExisting_RemovesPlanetFromDataBase() throws Exception {
        mockMvc.perform(delete("/planets/1")).andExpect(status().isNoContent());
    }

    @Test
    public void removePlanet_WithUnexistingId_ThrowsException() throws Exception {
        /* final Long planetId = 1L;
        doThrow(new EmptyResultDataAccessException(1)).when(service).deleteById(planetId);

        mockMvc.perform(delete("/planets/" + planetId)).andExpect(status().isNotFound()); */
    }

}
