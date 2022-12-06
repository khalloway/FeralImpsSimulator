package com.kotfi.FeralImpsSimulator.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kotfi.FeralImpsSimulator.FeralImpsSimulatorApplication;
import com.kotfi.FeralImpsSimulator.models.CardInfo;
import com.kotfi.FeralImpsSimulator.models.ResponseData;
import com.kotfi.FeralImpsSimulator.utils.ResponseDataDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CardInfoService {

    private static final String API_ENDPOINT = "https://db.ygoprodeck.com/api/v7/cardinfo.php";

    private static List<CardInfo> reptileCards;

    private static final String DATA_FILE = "/card_info/data.json";
    private static final String IMAGE_FOLDER = "/card_info/images/";

    private static File dataFile;
    private static File imageFolder;

    public static File resourceRoot;

    public CardInfoService() {
        try {
            resourceRoot = new File(FeralImpsSimulatorApplication.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            // create files/folders
            dataFile = new File(resourceRoot + "/" + DATA_FILE);
            if (!dataFile.exists()) {
                dataFile.getParentFile().mkdirs();
                dataFile.createNewFile();
            }
            imageFolder = new File(resourceRoot + "/" + IMAGE_FOLDER);
            if (!imageFolder.exists()) {
                imageFolder.mkdirs();
                imageFolder.createNewFile();
            }
            updateCardInfo();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            dataFile = null;
            imageFolder = null;
        }
    }

    private void updateCardInfo() throws IOException {
        // write json to file
        StringBuilder sb = new StringBuilder(API_ENDPOINT);
        sb.append("?race=reptile");
        String json = sendAPIRequest(sb.toString());
        try (FileWriter w = new FileWriter(dataFile)) {
            w.write(json);
        }
        // download images
        ResponseData response = deserialize(json);
        for(CardInfo info : response.getData()) {
            info.getCardImages().get(0).downloadImage();
        }
    }

    public List<CardInfo> getAllReptileCards() {
        String json = null;
        try {
            json = Files.readString(dataFile.toPath());
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder(API_ENDPOINT);
            sb.append("?race=reptile");
            json = sendAPIRequest(sb.toString());
        }

        if(reptileCards == null) {
            ResponseData response = deserialize(json);
            reptileCards = response.getData();
        }
        return reptileCards;
    }

    /**
     * Get the list of searchable main deck reptiles via King of the Feral Imp's effect.
     * @return List of CardInfo excluding XYZ, Link, Synchro, and Fusion monsters.
     */
    public List<CardInfo> getMainDeckReptileCards() {
        if(reptileCards == null) {
            getAllReptileCards();
        }

        return reptileCards.stream()
                .filter(card -> {
                    String cardType = card.getType();
                    return !(cardType.contains("XYZ") || cardType.contains("Link") || cardType.contains("Synchro") || cardType.contains("Fusion"));
                })
                .toList();
    }

    /**
     * Gets the list of all main deck level 4 reptiles.
     * @return filtered list of reptile cards for level 4 monsters only.
     */
    public List<CardInfo> getLevelFourReptiles() {
        List<CardInfo> levelFours = getMainDeckReptileCards().stream()
                .filter(card -> card.getLevel() == 4)
                .toList();
//        System.out.println(levelFours.size());
        return levelFours;
    }

    public CardInfo getCardByName(String name) {
        if(reptileCards == null) {
            getAllReptileCards();
        }

        return reptileCards.stream()
                .filter(cardInfo -> cardInfo.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    private ResponseData deserialize(String json) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ResponseData.class, new ResponseDataDeserializer())
                .create();
        return gson.fromJson(json, ResponseData.class);
    }

    private String sendAPIRequest(String queryString) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(queryString))
                    .GET()
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static File getDataFile() {
        return dataFile;
    }

    public static File getImageFolder() {
        return imageFolder;
    }

    public static File getResourceRoot() {
        return resourceRoot;
    }
}
