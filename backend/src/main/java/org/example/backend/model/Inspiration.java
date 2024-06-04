package org.example.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@With
@AllArgsConstructor
@Document(collection = "inspiration")
public class Inspiration {
    @Id
    private String id;
    private String name;
    private String description;
    private String heroImage;
    private List<String> detailImageUrls;
    private List<String> tags;
}
