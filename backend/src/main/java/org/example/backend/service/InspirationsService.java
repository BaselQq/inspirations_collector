package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.InspirationsRecord;
import org.example.backend.model.Inspiration;
import org.example.backend.repository.InspirationsRepo;
import org.springframework.stereotype.Service;

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
}
