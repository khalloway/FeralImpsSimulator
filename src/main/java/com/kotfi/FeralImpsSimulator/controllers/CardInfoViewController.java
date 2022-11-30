package com.kotfi.FeralImpsSimulator.controllers;

import com.kotfi.FeralImpsSimulator.services.CardInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CardInfoViewController {
    private final CardInfoService cardInfoService;

    public CardInfoViewController(CardInfoService cardInfoService) {
        this.cardInfoService = cardInfoService;
    }

    @GetMapping("/")
    public String getHome(Model model) {
        model.addAttribute("materials", cardInfoService.getLevelFourReptiles());
        model.addAttribute("reptiles", cardInfoService.getMainDeckReptileCards());
        model.addAttribute("kotfi", cardInfoService.getCardByName("King of the Feral Imps"));
        return "home.html";
    }
}
