package org.example.backend.config;

import com.cloudinary.Cloudinary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "CLOUDINARY_CLOUD_NAME=test_cloud",
        "CLOUDINARY_API_KEY=test_key",
        "CLOUDINARY_API_SEC=test_secret"
})
public class CloudinaryConfTest {

    @Autowired
    private Cloudinary cloudinary;

    @Value("${CLOUDINARY_CLOUD_NAME}")
    private String cloudName;

    @Value("${CLOUDINARY_API_KEY}")
    private String apiKey;

    @Value("${CLOUDINARY_API_SEC}")
    private String apiSec;

    @Test
    public void testCloudinaryBeanConfiguration() {
        assertNotNull(cloudinary, "Cloudinary bean should not be null");

        // Accessing the Cloudinary configuration
        assertEquals(cloudName, cloudinary.config.cloudName);
        assertEquals(apiKey, cloudinary.config.apiKey);
        assertEquals(apiSec, cloudinary.config.apiSecret);
    }
}
