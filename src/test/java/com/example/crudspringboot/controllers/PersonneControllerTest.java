package com.example.crudspringboot.controllers;

import com.example.crudspringboot.configuration.JwtUtils;
import com.example.crudspringboot.entities.Personne;
import com.example.crudspringboot.services.CustomUserDetailsService;
import com.example.crudspringboot.services.PersonneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@WebMvcTest(value = PersonneController.class, excludeAutoConfiguration = {
        SecurityAutoConfiguration.class,
        SecurityFilterAutoConfiguration.class
})class PersonneControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonneService personneService;
    @MockitoBean
    private CustomUserDetailsService customUserDetailsService; // ← JwtFilter en a besoin

    @MockitoBean
    private JwtUtils jwtUtils;

    @Test
    void shouldReturnAllPersons() throws Exception {
        Personne p1 = new Personne("John Doe", "New York", "123456789");
        Personne p2 = new Personne("Jane Doe", "Los Angeles", "987654321");

        when(personneService.getAllPersonnes()).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/api/personnes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nom").value("John Doe"))

        ;


    }
    @Test
    void shouldReturnPersonById() throws Exception {
        Personne p = new Personne("John Doe", "New York", "123456789");
        when(personneService.getPersonneById(1L)).thenReturn(p);

        mockMvc.perform(get("/api/personnes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("New York"));
    }
    @Test
    void shouldReturnCreatedPerson() throws Exception {
        String json = """
                {
                    "id": 1,
                    "nom": "John Doe",
                    "city": "New York",
                    "phoneNumber": "123456789"
                }
                """;
        Personne p = new Personne("John Doe", "New York", "123456789");
        when(personneService.createPersonne(p)).thenReturn(p);

        mockMvc.perform(post("/api/personnes")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated())
                //.andExpect(jsonPath("$.nom").value("John Doe"))
                ;
    }
    @Test
    void shouldReturnUpdatedPerson() throws Exception {
        String json = """
            {
                "id": 1,
                "nom": "John Doe",
                "city": "New York",
                "phoneNumber": "123456789"
            }
            """;
        Personne p = new Personne("John Doe", "New York", "123456789");

        // ← any(Personne.class) au lieu de p
        when(personneService.updatePersonne(eq(1L), any(Personne.class))).thenReturn(p);

        mockMvc.perform(put("/api/personnes/1")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("John Doe"));
    }
    @Test
    void shouldDeletePerson() throws Exception {
           Personne p = new Personne("John Doe", "New York", "123456789");
           when(personneService.getPersonneById(1L)).thenReturn(p);

            mockMvc.perform(delete("/api/personnes/1"))
                    .andExpect(status().isNoContent());

    }
}