package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.InspirationsRecord;
import org.example.backend.model.Inspiration;
import org.example.backend.repository.InspirationsRepo;
import org.example.backend.service.InspirationsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class InspirationsController {
    private final InspirationsRepo repo;
    private final InspirationsService inspirationsService;

    @PostMapping("/add/inspiration")
    public ResponseEntity<Inspiration> createNewInspiration(@RequestBody InspirationsRecord inspiration) {
        Inspiration createdInspiration = inspirationsService.createNewInspiration(inspiration);
        return ResponseEntity.ok(createdInspiration);
    }
    @PutMapping("/inspiration/{id}")
    public ResponseEntity<Inspiration> updateInspiration(@PathVariable String id, @RequestBody InspirationsRecord updatedInspiration) {
        Inspiration updated = inspirationsService.updateInspiration(id, updatedInspiration);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/inspiration/{id}")
    public ResponseEntity<Void> deleteInspiration(@PathVariable String id) {
        inspirationsService.deleteInspiration(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/inspirations")
    public List<Inspiration> getAllInspirations() {
        return inspirationsService.getAllInspirations();
    }

    @GetMapping("/inspiration/{id}")
    public Optional<Inspiration> getInspirationById(@PathVariable String id) {
        return inspirationsService.getInspirationById(id);
    }

    @GetMapping("/search/inspirations")
    public List<Inspiration> searchInspirations(@RequestParam String searchTerm) {
        return inspirationsService.searchInspirations(searchTerm);
    }
}
