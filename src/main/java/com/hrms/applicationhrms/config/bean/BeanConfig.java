package com.hrms.applicationhrms.config.bean;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hrms.applicationhrms.core.adapters.CloudinaryImageAdapter;
import com.hrms.applicationhrms.core.adapters.ImageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public Cloudinary cloudinaryService(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name","djopj1d1m",
                "api_key","579217186544211",
                "api_secret","W84B4M-MrjljuyyCMLXbpLHPeaE"
        ));
    }

    @Bean
    public ImageService imageService(){
        return new CloudinaryImageAdapter(cloudinaryService());
    }

}
