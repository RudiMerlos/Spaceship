package com.project.spaceship.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.spaceship.dto.SpaceshipDto;
import com.project.spaceship.service.SpaceshipService;

@SpringBootTest
@AutoConfigureMockMvc
public class SpaceshipControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SpaceshipService spaceshipService;

    @BeforeEach
    public void setUp() {
        this.spaceshipService.deleteAll();
        this.spaceshipService.save(new SpaceshipDto("Halcón Milenario", "Star Wars"));
        this.spaceshipService.save(new SpaceshipDto("USS Enterprise", "Star Trek"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetAll() throws Exception {
        // @formatter:off
        this.mockMvc.perform(get("/api/spaceships")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));
        // @formatter:on
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testFindByIdFound() throws Exception {
        SpaceshipDto spaceship = this.spaceshipService.save(new SpaceshipDto("Nave Nodriza", "District 9"));
        // @formatter:off
        this.mockMvc.perform(get("/api/spaceships/{id}", spaceship.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.spaceshipName").value("Nave Nodriza"));
        // @formatter:on
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testFindByIdNotFound() throws Exception {
        // @formatter:off
        this.mockMvc.perform(get("/api/spaceships/{id}", 99L))
                .andExpect(status().isNotFound());
        // @formatter:on
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testSearchByName() throws Exception {
        // @formatter:off
        this.mockMvc.perform(get("/api/spaceships/search")
                .param("name", "prise"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].spaceshipName").value("USS Enterprise"));
        // @formatter:on
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testCreateUnauthorized() throws Exception {
        SpaceshipDto spaceship = this.spaceshipService.save(new SpaceshipDto("Discovery One", "2001: Una Odisea del espacio"));
        // @formatter:off
        this.mockMvc.perform(post("/api/spaceships/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(spaceship)))
                .andExpect(status().isForbidden());
        // @formatter:on
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCreate() throws Exception {
        SpaceshipDto spaceship = this.spaceshipService.save(new SpaceshipDto("Discovery One", "2001: Una Odisea del espacio"));
        // @formatter:off
        this.mockMvc.perform(post("/api/spaceships/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(spaceship)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.spaceshipName").value("Discovery One"));
        // @formatter:on
    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testEdit() throws Exception {
        SpaceshipDto newSpaceship = this.spaceshipService.save(new SpaceshipDto("Nave Original", "Película Original"));
        SpaceshipDto updatedSpaceship = new SpaceshipDto("Nave Actualizada", "Película Actualizada");
        // @formatter:off
        this.mockMvc.perform(put("/api/spaceships/{id}", newSpaceship.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(updatedSpaceship)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.spaceshipName").value("Nave Actualizada"))
                .andExpect(jsonPath("$.movieName").value("Película Actualizada"));
        // @formatter:on
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDelete() throws Exception {
        SpaceshipDto spaceship = this.spaceshipService.save(new SpaceshipDto("Nave Eliminar", "Película Eliminar"));
        // @formatter:off
        this.mockMvc.perform(delete("/api/spaceships/{id}", spaceship.getId()))
                .andExpect(status().isNoContent());

        this.mockMvc.perform(get("/api/spaceships/{id}", spaceship.getId()))
                .andExpect(status().isNotFound());
        // @formatter:on
    }

}
