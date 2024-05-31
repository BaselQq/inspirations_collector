package org.example.backend.repository;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.Inspiration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface InspirationsRepo extends MongoRepository<Inspiration, String> {

    @Query("{ '$or': [ " +
            "{ 'name': { $regex: ?0, $options: 'i' } }, " +
            "{ 'description': { $regex: ?0, $options: 'i' } }, " +
            "{ 'tags': { $regex: ?0, $options: 'i' } } " +
            "] }")
    List<Inspiration> search(String searchTerm);
}
