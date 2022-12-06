package com.kotfi.FeralImpsSimulator.config;

import com.kotfi.FeralImpsSimulator.FeralImpsSimulatorApplication;
import com.kotfi.FeralImpsSimulator.services.CardInfoService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class CardImageConfig implements WebMvcConfigurer {
    private File imageFolder;

    public CardImageConfig() {
        imageFolder = CardInfoService.getResourceRoot();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations(imageFolder.getAbsolutePath());
    }
}
