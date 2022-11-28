package com.kotfi.FeralImpsSimulator.models;

import java.util.List;

public class CardInfo {
    private int id;
    private String name;
    private String type;
    private String desc;
    private int atk;
    private int def;
    private int level;
    private String race;
    private String attribute;
    private List<CardSet> cardSets;
    private List<CardImage> cardImages;
    private List<CardPrice> cardPrices;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public List<CardSet> getCardSets() {
        return cardSets;
    }

    public void setCardSets(List<CardSet> cardSets) {
        this.cardSets = cardSets;
    }

    public List<CardImage> getCardImages() {
        return cardImages;
    }

    public void setCardImages(List<CardImage> cardImages) {
        this.cardImages = cardImages;
    }

    public List<CardPrice> getCardPrices() {
        return cardPrices;
    }

    public void setCardPrices(List<CardPrice> cardPrices) {
        this.cardPrices = cardPrices;
    }

    @Override
    public String toString() {
        return "CardInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", desc='" + desc + '\'' +
                ", atk=" + atk +
                ", def=" + def +
                ", level=" + level +
                ", race='" + race + '\'' +
                ", attribute='" + attribute + '\'' +
                ", cardSets=" + cardSets +
                ", cardImages=" + cardImages +
                ", cardPrices=" + cardPrices +
                '}';
    }
}
