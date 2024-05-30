package org.example.backend.repository;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.Inspiration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InspirationsRepo extends MongoRepository<Inspiration, String> {
}
