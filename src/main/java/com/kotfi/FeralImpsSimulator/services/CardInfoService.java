package com.kotfi.FeralImpsSimulator.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CardInfoService {

    private static final String API_ENDPOINT = "https://db.ygoprodeck.com/api/v7/cardinfo.php";
    private static final String ENCODING = "UTF-8";

    private static List<CardInfo> reptileCards;

    @Autowired
    ResourceLoader resourceLoader;

    @Value("${resources.carddata}")
    private String cardData;

    public List<CardInfo> getAllReptileCards() {
        String json = null;
        StringBuilder sb = new StringBuilder(API_ENDPOINT);
        sb.append("?race=reptile");
        if(reptileCards == null) {
            json = sendAPIRequest(sb.toString());
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
}
