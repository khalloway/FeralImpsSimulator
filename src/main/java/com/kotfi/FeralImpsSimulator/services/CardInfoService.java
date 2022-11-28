package com.kotfi.FeralImpsSimulator.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kotfi.FeralImpsSimulator.models.CardInfo;
import com.kotfi.FeralImpsSimulator.models.ResponseData;
import com.kotfi.FeralImpsSimulator.utils.ResponseDataDeserializer;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class CardInfoService {

    private static final String API_ENDPOINT = "https://db.ygoprodeck.com/api/v7/cardinfo.php";
    private static final String ENCODING = "UTF-8";

    private static List<CardInfo> reptileCards;

    public List<CardInfo> getAllCards() {
        return null;
    }

    public List<CardInfo> getAllReptileCards() {
        if(reptileCards == null) {
//            System.out.println("Pull into cache");
            StringBuilder sb = new StringBuilder(API_ENDPOINT);
            sb.append("?race=reptile");
            String json = sendAPIRequest(sb.toString());
//            System.out.println(json);
            ResponseData response = deserialize(json);
            reptileCards = response.getData();
        }
        System.out.println(reptileCards.size());
        return reptileCards;
    }

    public List<CardInfo> getLevelFourReptiles() {
        List<CardInfo> levelFours = getAllReptileCards().stream()
                .filter(card -> card.getLevel() == 4)
                .toList();
        System.out.println(levelFours.size());
        return levelFours;
    }

    public CardInfo getCardByName(String name) {
        StringBuilder queryString = new StringBuilder(API_ENDPOINT);
        queryString.append("?name=");
        try {
            queryString.append(URLEncoder.encode(name, ENCODING));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        String json = sendAPIRequest(queryString.toString());
//        System.out.println(json);
        return deserialize(json).getData().get(0);
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
