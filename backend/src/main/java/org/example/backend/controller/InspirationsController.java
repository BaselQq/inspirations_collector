package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.InspirationsRecord;
import org.example.backend.repository.InspirationsRepo;
import org.example.backend.service.InspirationsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InspirationsController {
    private final InspirationsRepo repo;
    private final InspirationsService inspirationsService;

    @PostMapping("/add/inspiration")
    public void createNewInspiration(@RequestBody InspirationsRecord inspiration) {
        inspirationsService.createNewInspiration(inspiration);
    }
}
