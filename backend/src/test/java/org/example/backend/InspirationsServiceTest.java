package org.example.backend;

import org.example.backend.dto.InspirationsRecord;
import org.example.backend.model.Inspiration;
import org.example.backend.repository.InspirationsRepo;
import org.example.backend.service.InspirationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InspirationsServiceTest {

    @Mock
    private InspirationsRepo repo;

    @InjectMocks
    private InspirationsService service;

    private Inspiration inspiration;
    private InspirationsRecord record;

    @BeforeEach
    void setUp() {
        record = new InspirationsRecord(
                "Test Name",
                "Test Description",
                "Test Hero Image",
                new String[]{"Detail Image 1", "Detail Image 2"},
                new String[]{"Tag1", "Tag2"}
        );

        inspiration = new Inspiration(
                UUID.randomUUID().toString(),
                record.name(),
                record.description(),
                record.heroImage(),
                record.detailsImageUrls(),
                record.tags()
        );
    }

    @Test
    void createNewInspiration() {
        InspirationsRecord record = new InspirationsRecord(
                "Test Name",
                "Test Description",
                "Test Hero Image",
                new String[]{"Detail Image 1", "Detail Image 2"},
                new String[]{"Tag1", "Tag2"}
        );

        Inspiration inspiration = new Inspiration(
                UUID.randomUUID().toString(),
                record.name(),
                record.description(),
                record.heroImage(),
                record.detailsImageUrls(),
                record.tags()
        );

        when(repo.save(any(Inspiration.class))).thenReturn(inspiration);

        service.createNewInspiration(record);

        verify(repo, times(1)).save(any(Inspiration.class));
    }

    @Test
    void updateInspiration() {
        InspirationsRecord updatedRecord = new InspirationsRecord(
                "Updated Name",
                "Updated Description",
                "Updated Hero Image",
                new String[]{"Updated Detail Image 1", "Updated Detail Image 2"},
                new String[]{"Updated Tag1", "Updated Tag2"}
        );

        when(repo.findById(inspiration.getId())).thenReturn(Optional.of(inspiration));
        when(repo.save(any(Inspiration.class))).thenReturn(inspiration);

        Inspiration updatedInspiration = service.updateInspiration(inspiration.getId(), updatedRecord);

        verify(repo, times(1)).findById(inspiration.getId());
        verify(repo, times(1)).save(any(Inspiration.class));
        assertEquals(updatedRecord.name(), updatedInspiration.getName());
        assertEquals(updatedRecord.description(), updatedInspiration.getDescription());
        assertEquals(updatedRecord.heroImage(), updatedInspiration.getHeroImage());
        assertArrayEquals(updatedRecord.detailsImageUrls(), updatedInspiration.getDetailImageUrls());
        assertArrayEquals(updatedRecord.tags(), updatedInspiration.getTags());
    }

    @Test
    void getAllInspirations() {
        when(repo.findAll()).thenReturn(Arrays.asList(inspiration));

        List<Inspiration> inspirations = service.getAllInspirations();

        verify(repo, times(1)).findAll();
        assertEquals(1, inspirations.size());
        assertEquals(inspiration.getId(), inspirations.get(0).getId());
    }

    @Test
    void getInspirationById() {
        when(repo.findById(inspiration.getId())).thenReturn(Optional.of(inspiration));

        Optional<Inspiration> foundInspiration = service.getInspirationById(inspiration.getId());

        verify(repo, times(1)).findById(inspiration.getId());
        assertTrue(foundInspiration.isPresent());
        assertEquals(inspiration.getId(), foundInspiration.get().getId());
    }

    @Test
    void deleteInspiration() {
        doNothing().when(repo).deleteById(inspiration.getId());

        service.deleteInspiration(inspiration.getId());

        verify(repo, times(1)).deleteById(inspiration.getId());
    }
}
