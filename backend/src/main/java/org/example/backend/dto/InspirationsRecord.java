package org.example.backend.dto;

public record InspirationsRecord(
        String name,
        String description,
        String heroImage,
        String[] detailsImageUrls,
        String[] tags

) {
}
