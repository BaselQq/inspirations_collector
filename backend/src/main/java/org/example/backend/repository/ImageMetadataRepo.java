package org.example.backend.repository;

import org.example.backend.model.ImageMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageMetadataRepo extends MongoRepository<ImageMetadata, String> {
}
