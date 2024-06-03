package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.service.CloudinaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class UploadController {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
    private final CloudinaryService cloudinaryService;

    @PostMapping("/image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String url = cloudinaryService.uploadImage(file);
            return ResponseEntity.ok(url);
        } catch (IllegalArgumentException e) {
            logger.error("File validation error: ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error uploading image: ", e);
            return ResponseEntity.status(500).body("Error uploading image: " + e.getMessage());
        }
    }
}
