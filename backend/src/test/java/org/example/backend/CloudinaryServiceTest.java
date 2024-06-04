package org.example.backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import org.example.backend.model.ImageMetadata;
import org.example.backend.repository.ImageMetadataRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CloudinaryServiceTest {

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private Uploader uploader;

    @Mock
    private ImageMetadataRepo imageMetadataRepo;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private CloudinaryService cloudinaryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(cloudinary.uploader()).thenReturn(uploader);
    }

    @Test
    public void testUploadImageSuccess() throws IOException {
        byte[] fileBytes = new byte[]{1, 2, 3};
        when(file.getBytes()).thenReturn(fileBytes);
        when(file.getContentType()).thenReturn("image/png");
        when(file.getSize()).thenReturn(1024L);

        Map<String, Object> uploadResult = Map.of(
                "url", "http://res.cloudinary.com/test/image/upload/sample.png",
                "public_id", "sample",
                "format", "png"
        );

        when(uploader.upload(fileBytes, ObjectUtils.emptyMap())).thenReturn(uploadResult);

        ImageMetadata metadata = new ImageMetadata();
        metadata.setUrl("http://res.cloudinary.com/test/image/upload/sample.png");
        metadata.setPublicId("sample");
        metadata.setFormat("png");
        metadata.setSize(1024L);
        metadata.setType("image/png");

        when(imageMetadataRepo.save(any(ImageMetadata.class))).thenReturn(metadata);

        String url = cloudinaryService.uploadImage(file);

        assertEquals("http://res.cloudinary.com/test/image/upload/sample.png", url);
        verify(imageMetadataRepo, times(1)).save(any(ImageMetadata.class));
    }

    @Test
    public void testUploadImageInvalidFile() {
        when(file.getContentType()).thenReturn("text/plain");
        when(file.getSize()).thenReturn(1024L);

        assertThrows(IllegalArgumentException.class, () -> cloudinaryService.uploadImage(file));
    }

    @Test
    public void testUploadImageIOException() throws IOException {
        byte[] fileBytes = new byte[]{1, 2, 3};
        when(file.getBytes()).thenReturn(fileBytes);
        when(file.getContentType()).thenReturn("image/png");
        when(file.getSize()).thenReturn(1024L);

        when(uploader.upload(fileBytes, ObjectUtils.emptyMap())).thenThrow(new IOException("Test IOException"));

        IOException exception = assertThrows(IOException.class, () -> cloudinaryService.uploadImage(file));
        assertEquals("Test IOException", exception.getMessage());
    }
}
