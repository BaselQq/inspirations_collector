package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.InspirationsRecord;
import org.example.backend.model.Inspiration;
import org.example.backend.repository.InspirationsRepo;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
                newInspiration.heroImage(),  // This will be set later if needed
                Arrays.asList(newInspiration.detailsImageUrls()),  // Convert array to list
                Arrays.asList(newInspiration.tags())  // Convert array to list
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
            // heroImage and detailImageUrls will be updated separately
            inspiration.setTags(Arrays.asList(updatedInspiration.tags()));  // Convert array to list
            return repo.save(inspiration);
        }).orElseThrow(() -> new RuntimeException("Inspiration not found"));
    }

    public void deleteInspiration(String id) {
        repo.deleteById(id);
    }

    public List<Inspiration> searchInspirations(String searchTerm) {
        return repo.search(searchTerm);
    }

    public void updateHeroImage(String inspirationId, String heroImageUrl) {
        repo.findById(inspirationId).ifPresent(inspiration -> {
            inspiration.setHeroImage(heroImageUrl);
            repo.save(inspiration);
        });
    }

    public void addDetailImage(String inspirationId, String imageMetadataId) {
        repo.findById(inspirationId).ifPresent(inspiration -> {
            inspiration.getDetailImageUrls().add(imageMetadataId);
            repo.save(inspiration);
        });
    }
}
