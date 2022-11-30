package com.kotfi.FeralImpsSimulator.controllers;

import com.kotfi.FeralImpsSimulator.models.CardInfo;
import com.kotfi.FeralImpsSimulator.services.CardInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class CardInfoAPIController {
    private final CardInfoService infoService;

    public CardInfoAPIController(CardInfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping(path="/all")
    public List<CardInfo> getAllReptiles() {
        return infoService.getMainDeckReptileCards();
    }

    @GetMapping(path="/lvl4")
    public List<CardInfo> getLevelFourReptiles() {
        return infoService.getLevelFourReptiles();
    }
}
