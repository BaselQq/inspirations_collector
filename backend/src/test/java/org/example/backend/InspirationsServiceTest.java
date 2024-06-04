package org.example.backend.service;

import org.example.backend.dto.InspirationsRecord;
import org.example.backend.model.Inspiration;
import org.example.backend.repository.InspirationsRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InspirationsServiceTest {

    @Mock
    private InspirationsRepo repo;

    @InjectMocks
    private InspirationsService inspirationsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateNewInspiration() {
        InspirationsRecord record = new InspirationsRecord("Test Name", "Test Description", "", new String[]{}, new String[]{"tag1", "tag2"});
        Inspiration inspiration = new Inspiration(UUID.randomUUID().toString(), "Test Name", "Test Description", "", Arrays.asList(), Arrays.asList("tag1", "tag2"));

        when(repo.save(any(Inspiration.class))).thenReturn(inspiration);

        Inspiration result = inspirationsService.createNewInspiration(record);

        assertEquals("Test Name", result.getName());
        assertEquals("Test Description", result.getDescription());
        assertTrue(result.getDetailImageUrls().isEmpty());
        assertEquals(Arrays.asList("tag1", "tag2"), result.getTags());

        verify(repo, times(1)).save(any(Inspiration.class));
    }

    @Test
    public void testGetAllInspirations() {
        List<Inspiration> inspirations = Arrays.asList(
                new Inspiration(UUID.randomUUID().toString(), "Name1", "Description1", "", Arrays.asList(), Arrays.asList("tag1")),
                new Inspiration(UUID.randomUUID().toString(), "Name2", "Description2", "", Arrays.asList(), Arrays.asList("tag2"))
        );

        when(repo.findAll()).thenReturn(inspirations);

        List<Inspiration> result = inspirationsService.getAllInspirations();

        assertEquals(2, result.size());
        assertEquals("Name1", result.get(0).getName());
        assertEquals("Name2", result.get(1).getName());

        verify(repo, times(1)).findAll();
    }

    @Test
    public void testGetInspirationById() {
        String id = UUID.randomUUID().toString();
        Inspiration inspiration = new Inspiration(id, "Name", "Description", "", Arrays.asList(), Arrays.asList("tag1"));

        when(repo.findById(id)).thenReturn(Optional.of(inspiration));

        Optional<Inspiration> result = inspirationsService.getInspirationById(id);

        assertTrue(result.isPresent());
        assertEquals("Name", result.get().getName());

        verify(repo, times(1)).findById(id);
    }

    @Test
    public void testUpdateInspiration() {
        String id = UUID.randomUUID().toString();
        InspirationsRecord record = new InspirationsRecord("Updated Name", "Updated Description", "", new String[]{}, new String[]{"tag1", "tag2"});
        Inspiration inspiration = new Inspiration(id, "Original Name", "Original Description", "", Arrays.asList(), Arrays.asList("tag1"));

        when(repo.findById(id)).thenReturn(Optional.of(inspiration));
        when(repo.save(any(Inspiration.class))).thenReturn(inspiration);

        Inspiration result = inspirationsService.updateInspiration(id, record);

        assertEquals("Updated Name", result.getName());
        assertEquals("Updated Description", result.getDescription());

        verify(repo, times(1)).findById(id);
        verify(repo, times(1)).save(any(Inspiration.class));
    }

    @Test
    public void testDeleteInspiration() {
        String id = UUID.randomUUID().toString();
        doNothing().when(repo).deleteById(id);

        inspirationsService.deleteInspiration(id);

        verify(repo, times(1)).deleteById(id);
    }

    @Test
    public void testSearchInspirations() {
        List<Inspiration> inspirations = Arrays.asList(
                new Inspiration(UUID.randomUUID().toString(), "Name1", "Description1", "", Arrays.asList(), Arrays.asList("tag1")),
                new Inspiration(UUID.randomUUID().toString(), "Name2", "Description2", "", Arrays.asList(), Arrays.asList("tag2"))
        );

        when(repo.search("searchTerm")).thenReturn(inspirations);

        List<Inspiration> result = inspirationsService.searchInspirations("searchTerm");

        assertEquals(2, result.size());
        assertEquals("Name1", result.get(0).getName());
        assertEquals("Name2", result.get(1).getName());

        verify(repo, times(1)).search("searchTerm");
    }

    @Test
    public void testUpdateHeroImage() {
        String id = UUID.randomUUID().toString();
        Inspiration inspiration = new Inspiration(id, "Name", "Description", "", Arrays.asList(), Arrays.asList("tag1"));

        when(repo.findById(id)).thenReturn(Optional.of(inspiration));

        inspirationsService.updateHeroImage(id, "newHeroImage");

        assertEquals("newHeroImage", inspiration.getHeroImage());
        verify(repo, times(1)).findById(id);
        verify(repo, times(1)).save(inspiration);
    }

    @Test
    public void testAddDetailImage() {
        String id = UUID.randomUUID().toString();
        Inspiration inspiration = new Inspiration(id, "Name", "Description", "", Arrays.asList(), Arrays.asList("tag1"));

        when(repo.findById(id)).thenReturn(Optional.of(inspiration));

        inspirationsService.addDetailImage(id, "newDetailImage");

        assertTrue(inspiration.getDetailImageUrls().contains("newDetailImage"));
        verify(repo, times(1)).findById(id);
        verify(repo, times(1)).save(inspiration);
    }
}
