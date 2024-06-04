package org.example.backend.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConf {

    @Value("${CLOUDINARY_CLOUD_NAME:default_cloud_name}")
    private String cloudName;

    @Value("${CLOUDINARY_API_KEY:default_api_key}")
    private String apiKey;

    @Value("${CLOUDINARY_API_SECRET:default_api_secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        );
        return new Cloudinary(config);
    }
}
