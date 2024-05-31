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
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class InspirationsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InspirationsRepo inspirationsRepo;

    @BeforeEach
    public void setup() {
        inspirationsRepo.deleteAll();
    }

    @Test
    public void testCreateNewInspiration() throws Exception {
        InspirationsRecord newInspiration = new InspirationsRecord(
                "Test Inspiration",
                "Test Description",
                "hero.jpg",
                new String[]{"detail1.jpg", "detail2.jpg"},
                new String[]{"tag1", "tag2"}
        );

        mockMvc.perform(post("/add/inspiration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Inspiration\", \"description\":\"Test Description\", \"heroImage\":\"hero.jpg\", \"detailsImageUrls\":[\"detail1.jpg\",\"detail2.jpg\"], \"tags\":[\"tag1\",\"tag2\"]}"))
                .andExpect(status().isOk());

        assertThat(inspirationsRepo.findAll()).hasSize(1);
    }

    @Test
    public void testGetAllInspirations() throws Exception {
        inspirationsRepo.save(new Inspiration(
                "1", "Test Inspiration 1", "Description 1", "hero1.jpg",
                new String[]{"detail1.jpg", "detail2.jpg"}, new String[]{"tag1", "tag2"}
        ));
        inspirationsRepo.save(new Inspiration(
                "2", "Test Inspiration 2", "Description 2", "hero2.jpg",
                new String[]{"detail3.jpg", "detail4.jpg"}, new String[]{"tag3", "tag4"}
        ));

        mockMvc.perform(get("/inspirations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Test Inspiration 1")))
                .andExpect(jsonPath("$[1].name", is("Test Inspiration 2")));
    }

    @Test
    public void testGetInspirationById() throws Exception {
        Inspiration inspiration = new Inspiration(
                "1", "Test Inspiration", "Description", "hero.jpg",
                new String[]{"detail1.jpg", "detail2.jpg"}, new String[]{"tag1", "tag2"}
        );
        inspirationsRepo.save(inspiration);

        mockMvc.perform(get("/inspiration/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Test Inspiration")));
    }

    @Test
    public void testUpdateInspiration() throws Exception {
        Inspiration inspiration = new Inspiration(
                "1", "Old Name", "Old Description", "oldHero.jpg",
                new String[]{"oldDetail1.jpg", "oldDetail2.jpg"}, new String[]{"oldTag1", "oldTag2"}
        );
        inspirationsRepo.save(inspiration);

        InspirationsRecord updatedInspiration = new InspirationsRecord(
                "New Name", "New Description", "newHero.jpg",
                new String[]{"newDetail1.jpg", "newDetail2.jpg"}, new String[]{"newTag1", "newTag2"}
        );

        mockMvc.perform(put("/inspiration/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Name\", \"description\":\"New Description\", \"heroImage\":\"newHero.jpg\", \"detailsImageUrls\":[\"newDetail1.jpg\",\"newDetail2.jpg\"], \"tags\":[\"newTag1\",\"newTag2\"]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("New Name")));

        Optional<Inspiration> updatedInsp = inspirationsRepo.findById("1");
        assertThat(updatedInsp).isPresent();
        assertThat(updatedInsp.get().getName()).isEqualTo("New Name");
    }

    @Test
    public void testDeleteInspiration() throws Exception {
        Inspiration inspiration = new Inspiration(
                "1", "Test Inspiration", "Description", "hero.jpg",
                new String[]{"detail1.jpg", "detail2.jpg"}, new String[]{"tag1", "tag2"}
        );
        inspirationsRepo.save(inspiration);

        mockMvc.perform(delete("/inspiration/{id}", "1"))
                .andExpect(status().isOk());

        assertThat(inspirationsRepo.findById("1")).isNotPresent();
    }
}
