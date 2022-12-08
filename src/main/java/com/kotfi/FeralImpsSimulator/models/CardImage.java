package com.kotfi.FeralImpsSimulator.models;

import com.kotfi.FeralImpsSimulator.FeralImpsSimulatorApplication;
import com.kotfi.FeralImpsSimulator.services.CardInfoService;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class CardImage {
    private int id;
    private String image_url;
    private String image_url_small;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getImage_url_small() {
        return image_url_small;
    }

    public void setImage_url_small(String image_url_small) {
        this.image_url_small = image_url_small;
    }
    public String getImageName() {
        int start = this.image_url.lastIndexOf("/");
        return this.image_url.substring(start + 1);
    }

    public void downloadImage() throws IOException {
//        URL url = new URL(image_url);
//        String filePath = CardInfoService.getResourceRoot() + "/" + getImageName();
//        if(!Files.exists(Path.of(filePath))) {
//            try (InputStream in = new BufferedInputStream(url.openStream());
//                 FileOutputStream out = new FileOutputStream(filePath)) {
//                out.write(in.readAllBytes());
//            }
//        }
    }

    @Override
    public String toString() {
        return "CardImage{" +
                "id=" + id +
                ", image_url='" + image_url + '\'' +
                ", image_url_small='" + image_url_small + '\'' +
                '}';
    }
}
