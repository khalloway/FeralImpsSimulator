package com.kotfi.FeralImpsSimulator.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.kotfi.FeralImpsSimulator.models.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ResponseDataDeserializer implements JsonDeserializer {
    private static final String DATA = "data";
    private static final String CARD_SETS = "card_sets";
    private static final String CARD_IMAGES = "card_images";
    private static final String CARD_PRICES = "card_prices";

    @Override
    public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
//        System.out.println("In deserializer");
        // setup
        ResponseData responseData = new ResponseData();
        List<CardInfo> cards = new ArrayList<>();
        List<CardSet> sets = null;
        List<CardImage> images = null;
        List<CardPrice> prices = null;
        JsonObject json = jsonElement.getAsJsonObject();
        JsonElement dataElements = json.getAsJsonArray(DATA);
//        System.out.println("Parsing cards:");
        // parse card data
        for(JsonElement data : dataElements.getAsJsonArray()) {
            Gson gson = new Gson();
            CardInfo card = gson.fromJson(data, CardInfo.class);
            JsonObject dataObject = data.getAsJsonObject();
            // parse set info
            if(dataObject.has(CARD_SETS)) {
                JsonElement cardSets = dataObject.get(CARD_SETS);
                if(cardSets != null && !cardSets.isJsonNull()) {
                    sets = gson.fromJson(cardSets, new TypeToken<List<CardSet>>(){}.getType());
                }
                card.setCardSets(sets);
            }
            // parse image info
            if(dataObject.has(CARD_IMAGES)) {
                JsonElement cardImages = dataObject.get(CARD_IMAGES);
                if(cardImages != null && !cardImages.isJsonNull()) {
                    images = gson.fromJson(cardImages, new TypeToken<List<CardImage>>(){}.getType());
                }
                card.setCardImages(images);
            }
            // parse price info
            if(dataObject.has(CARD_PRICES)) {
                JsonElement cardPrices = dataObject.get(CARD_PRICES);
                if(cardPrices != null && !cardPrices.isJsonNull()) {
                    prices = gson.fromJson(cardPrices, new TypeToken<List<CardPrice>>(){}.getType());
                }
                card.setCardPrices(prices);
            }
//            System.out.println("Deserialized result:");
//            System.out.println(card.toString());
            // add to cards list
            cards.add(card);
        }
        // add cards to response data
        responseData.setData(cards);
        return responseData;
    }
}
