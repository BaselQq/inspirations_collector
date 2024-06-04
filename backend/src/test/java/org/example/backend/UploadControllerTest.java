package org.example.backend.controller;

import org.example.backend.service.CloudinaryService;
import org.example.backend.service.InspirationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class UploadControllerTest {

    @Mock
    private CloudinaryService cloudinaryService;

    @Mock
    private InspirationsService inspirationsService;

    @InjectMocks
    private UploadController uploadController;

    @Mock
    private MultipartFile file;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void uploadImage_SuccessHeroImage() throws Exception {
        String url = "http://example.com/image.jpg";
        String inspirationId = "123";
        String type = "hero";

        when(cloudinaryService.uploadImage(file)).thenReturn(url);

        ResponseEntity<String> response = uploadController.uploadImage(file, inspirationId, type);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(url, response.getBody());
    }

    @Test
    void uploadImage_SuccessDetailImage() throws Exception {
        String url = "http://example.com/image.jpg";
        String inspirationId = "123";
        String type = "detail";

        when(cloudinaryService.uploadImage(file)).thenReturn(url);

        ResponseEntity<String> response = uploadController.uploadImage(file, inspirationId, type);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(url, response.getBody());
    }

    @Test
    void uploadImage_FileValidationError() throws Exception {
        String inspirationId = "123";
        String type = "hero";

        doThrow(new IllegalArgumentException("Invalid file")).when(cloudinaryService).uploadImage(any(MultipartFile.class));

        ResponseEntity<String> response = uploadController.uploadImage(file, inspirationId, type);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid file", response.getBody());
    }

    @Test
    void uploadImage_GeneralError() throws Exception {
        String inspirationId = "123";
        String type = "hero";

        doThrow(new RuntimeException("Some error")).when(cloudinaryService).uploadImage(any(MultipartFile.class));

        ResponseEntity<String> response = uploadController.uploadImage(file, inspirationId, type);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Error uploading image: Some error", response.getBody());
    }
}
