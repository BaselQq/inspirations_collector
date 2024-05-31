package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.InspirationsRecord;
import org.example.backend.model.Inspiration;
import org.example.backend.repository.InspirationsRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InspirationsService {

    private final InspirationsRepo repo;

    public Inspiration createNewInspiration(InspirationsRecord newInspiration) {
        Inspiration inspiration = new Inspiration(
                UUID.randomUUID().toString(),
                newInspiration.name(),
                newInspiration.description(),
                newInspiration.heroImage(),
                newInspiration.detailsImageUrls(),
                newInspiration.tags()
        );
        repo.save(inspiration);
        return inspiration;
    }

    public List<Inspiration> getAllInspirations() {
        return repo.findAll();
    }

    public Optional<Inspiration> getInspirationById(String id) {
        return repo.findById(id);
    }

    public Inspiration updateInspiration(String id, InspirationsRecord updatedInspiration) {
        return repo.findById(id).map(inspiration -> {
            inspiration.setName(updatedInspiration.name());
            inspiration.setDescription(updatedInspiration.description());
            inspiration.setHeroImage(updatedInspiration.heroImage());
            inspiration.setDetailImageUrls(updatedInspiration.detailsImageUrls());
            inspiration.setTags(updatedInspiration.tags());
            return repo.save(inspiration);
        }).orElseThrow(() -> new RuntimeException("Inspiration not found"));
    }

    public void deleteInspiration(String id) {
        repo.deleteById(id);
    }
}