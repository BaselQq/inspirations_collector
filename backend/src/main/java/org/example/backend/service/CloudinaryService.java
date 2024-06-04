package org.example.backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.example.backend.model.ImageMetadata;
import org.example.backend.repository.ImageMetadataRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;
    private final ImageMetadataRepo imageMetadataRepo;

    public String uploadImage(MultipartFile file) throws IOException {
        if (!isValidFile(file)) {
            throw new IllegalArgumentException("Invalid file type or size");
        }

        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        String url = uploadResult.get("url").toString();
        String publicId = uploadResult.get("public_id").toString();
        String format = uploadResult.get("format").toString();
        long size = file.getSize();
        String type = file.getContentType();

        ImageMetadata metadata = new ImageMetadata();
        metadata.setUrl(url);
        metadata.setPublicId(publicId);
        metadata.setFormat(format);
        metadata.setSize(size);
        metadata.setType(type);

        imageMetadataRepo.save(metadata);

        return url;
    }

    private boolean isValidFile(MultipartFile file) {
        String contentType = file.getContentType();
        long fileSize = file.getSize();

        return (contentType != null && contentType.startsWith("image/")) && fileSize <= 10 * 1024 * 1024;
    }
}
