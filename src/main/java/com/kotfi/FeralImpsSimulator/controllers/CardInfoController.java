package com.kotfi.FeralImpsSimulator.controllers;

import com.kotfi.FeralImpsSimulator.models.CardImage;
import com.kotfi.FeralImpsSimulator.models.CardInfo;
import com.kotfi.FeralImpsSimulator.services.CardInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardInfoController {
    private final CardInfoService infoService;

    public CardInfoController(CardInfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping(path="/all")
    public List<CardInfo> getAllReptiles() {
        return infoService.getAllReptileCards();
    }

    @GetMapping(path="/lvl4")
    public List<CardInfo> getLevelFourReptiles() {
        return infoService.getLevelFourReptiles();
    }
}
