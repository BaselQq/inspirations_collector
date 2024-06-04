package org.example.backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "image_metadata")
public class ImageMetadata {
    @Id
    private String id;
    private String url;
    private String publicId;
    private String format;
    private long size;
    private String type;
}
