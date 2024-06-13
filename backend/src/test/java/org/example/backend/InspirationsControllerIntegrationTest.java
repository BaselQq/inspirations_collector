package org.example.backend.controller;

import org.example.backend.dto.InspirationsRecord;
import org.example.backend.model.Inspiration;
import org.example.backend.repository.InspirationsRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class InspirationsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InspirationsRepo inspirationsRepo;

    @BeforeEach
    public void setUp() {
        inspirationsRepo.deleteAll();
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testCreateNewInspiration() throws Exception {
        String content = "{ \"name\": \"Test Name\", \"description\": \"Test Description\", \"heroImage\": \"\", \"detailsImageUrls\": [], \"tags\": [\"tag1\", \"tag2\"] }";

        mockMvc.perform(post("/add/inspiration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());

        assertThat(inspirationsRepo.findAll()).hasSize(1);
        assertThat(inspirationsRepo.findAll().get(0).getName()).isEqualTo("Test Name");
    }
    @Test
    @WithMockUser(username = "user")
    public void testUpdateInspiration() throws Exception {
        String id = UUID.randomUUID().toString();
        Inspiration inspiration = new Inspiration(id, "Original Name", "Original Description", "", Arrays.asList(), Arrays.asList("tag1"));
        inspirationsRepo.save(inspiration);

        mockMvc.perform(put("/inspiration/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Updated Name\", \"description\": \"Updated Description\", \"heroImage\": \"\", \"detailsImageUrls\": [], \"tags\": [\"tag1\", \"tag2\"] }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"));

        Optional<Inspiration> updatedInspiration = inspirationsRepo.findById(id);
        assertThat(updatedInspiration).isPresent();
        assertThat(updatedInspiration.get().getName()).isEqualTo("Updated Name");
    }

    @Test
    @WithMockUser(username = "user")
    public void testDeleteInspiration() throws Exception {
        String id = UUID.randomUUID().toString();
        Inspiration inspiration = new Inspiration(id, "Test Name", "Test Description", "", Arrays.asList(), Arrays.asList("tag1", "tag2"));
        inspirationsRepo.save(inspiration);

        mockMvc.perform(delete("/inspiration/" + id))
                .andExpect(status().isOk());

        assertThat(inspirationsRepo.findById(id)).isNotPresent();
    }

    @Test
    public void testGetAllInspirations() throws Exception {
        inspirationsRepo.saveAll(Arrays.asList(
                new Inspiration(UUID.randomUUID().toString(), "Name1", "Description1", "", Arrays.asList(), Arrays.asList("tag1")),
                new Inspiration(UUID.randomUUID().toString(), "Name2", "Description2", "", Arrays.asList(), Arrays.asList("tag2"))
        ));

        mockMvc.perform(get("/inspirations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Name1"))
                .andExpect(jsonPath("$[1].name").value("Name2"));
    }

    @Test
    public void testGetInspirationById() throws Exception {
        String id = UUID.randomUUID().toString();
        Inspiration inspiration = new Inspiration(id, "Name", "Description", "", Arrays.asList(), Arrays.asList("tag1"));
        inspirationsRepo.save(inspiration);

        mockMvc.perform(get("/inspiration/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Name"));
    }

    @Test
    public void testSearchInspirations() throws Exception {
        inspirationsRepo.saveAll(Arrays.asList(
                new Inspiration(UUID.randomUUID().toString(), "Name1", "Description1", "", Arrays.asList(), Arrays.asList("tag1")),
                new Inspiration(UUID.randomUUID().toString(), "Name2", "Description2", "", Arrays.asList(), Arrays.asList("tag2"))
        ));

        mockMvc.perform(get("/search/inspirations").param("searchTerm", "Name1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Name1"));
    }
}
