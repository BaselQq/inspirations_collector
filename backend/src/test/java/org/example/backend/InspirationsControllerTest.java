package org.example.backend.controller;

import org.example.backend.dto.InspirationsRecord;
import org.example.backend.model.Inspiration;
import org.example.backend.service.InspirationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class InspirationsControllerTest {

    @Mock
    private InspirationsService inspirationsService;

    @InjectMocks
    private InspirationsController inspirationsController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(inspirationsController).build();
    }

    @Test
    public void testCreateNewInspiration() throws Exception {
        InspirationsRecord record = new InspirationsRecord("Test Name", "Test Description", "", new String[]{}, new String[]{"tag1", "tag2"});

        mockMvc.perform(post("/add/inspiration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Test Name\", \"description\": \"Test Description\", \"heroImage\": \"\", \"detailsImageUrls\": [], \"tags\": [\"tag1\", \"tag2\"] }"))
                .andExpect(status().isOk());

        verify(inspirationsService, times(1)).createNewInspiration(any(InspirationsRecord.class));
    }

    @Test
    public void testGetAllInspirations() throws Exception {
        List<Inspiration> inspirations = Arrays.asList(
                new Inspiration(UUID.randomUUID().toString(), "Name1", "Description1", "", Arrays.asList(), Arrays.asList("tag1")),
                new Inspiration(UUID.randomUUID().toString(), "Name2", "Description2", "", Arrays.asList(), Arrays.asList("tag2"))
        );

        when(inspirationsService.getAllInspirations()).thenReturn(inspirations);

        mockMvc.perform(get("/inspirations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Name1"))
                .andExpect(jsonPath("$[1].name").value("Name2"));

        verify(inspirationsService, times(1)).getAllInspirations();
    }

    @Test
    public void testGetInspirationById() throws Exception {
        String id = UUID.randomUUID().toString();
        Inspiration inspiration = new Inspiration(id, "Name", "Description", "", Arrays.asList(), Arrays.asList("tag1"));

        when(inspirationsService.getInspirationById(id)).thenReturn(Optional.of(inspiration));

        mockMvc.perform(get("/inspiration/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Name"));

        verify(inspirationsService, times(1)).getInspirationById(id);
    }

    @Test
    public void testUpdateInspiration() throws Exception {
        String id = UUID.randomUUID().toString();
        InspirationsRecord record = new InspirationsRecord("Updated Name", "Updated Description", "", new String[]{}, new String[]{"tag1", "tag2"});
        Inspiration inspiration = new Inspiration(id, "Original Name", "Original Description", "", Arrays.asList(), Arrays.asList("tag1"));

        when(inspirationsService.updateInspiration(eq(id), any(InspirationsRecord.class))).thenReturn(inspiration);

        mockMvc.perform(put("/inspiration/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Updated Name\", \"description\": \"Updated Description\", \"heroImage\": \"\", \"detailsImageUrls\": [], \"tags\": [\"tag1\", \"tag2\"] }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Original Name")); // This should be updated but matches mock return

        verify(inspirationsService, times(1)).updateInspiration(eq(id), any(InspirationsRecord.class));
    }

    @Test
    public void testDeleteInspiration() throws Exception {
        String id = UUID.randomUUID().toString();

        doNothing().when(inspirationsService).deleteInspiration(id);

        mockMvc.perform(delete("/inspiration/" + id))
                .andExpect(status().isOk());

        verify(inspirationsService, times(1)).deleteInspiration(id);
    }

    @Test
    public void testSearchInspirations() throws Exception {
        List<Inspiration> inspirations = Arrays.asList(
                new Inspiration(UUID.randomUUID().toString(), "Name1", "Description1", "", Arrays.asList(), Arrays.asList("tag1")),
                new Inspiration(UUID.randomUUID().toString(), "Name2", "Description2", "", Arrays.asList(), Arrays.asList("tag2"))
        );

        when(inspirationsService.searchInspirations("searchTerm")).thenReturn(inspirations);

        mockMvc.perform(get("/search/inspirations").param("searchTerm", "searchTerm"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Name1"))
                .andExpect(jsonPath("$[1].name").value("Name2"));

        verify(inspirationsService, times(1)).searchInspirations("searchTerm");
    }
}
