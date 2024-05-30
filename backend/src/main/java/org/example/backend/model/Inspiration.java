package org.example.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.With;

@Data
@With
@AllArgsConstructor
public class Inspiration {
    private String id;
    private String name;
    private String description;
    private String heroImage;
    private String[] detailImageUrls;
    private String[] tags;
}
