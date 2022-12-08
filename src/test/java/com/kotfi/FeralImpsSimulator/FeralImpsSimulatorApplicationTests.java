package com.kotfi.FeralImpsSimulator;

import com.kotfi.FeralImpsSimulator.models.CardInfo;
import com.kotfi.FeralImpsSimulator.repository.CardRepository;
import com.kotfi.FeralImpsSimulator.services.CardInfoService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;

@SpringBootTest
class FeralImpsSimulatorApplicationTests {

	@Autowired
	private CardRepository repo;

	@Test
	void contextLoads() {
	}

	@Test
	void testInfoServiceGetByName() {
		CardInfoService infoService = new CardInfoService(repo);
		String cardName = "King of the Feral Imps";
		CardInfo info = infoService.getCardByName(cardName);
		System.out.println(info.toString());
		Assert.assertEquals(info.getName(), cardName);
		Assert.assertNotNull(info.getCardImages());
		Assert.assertTrue(info.getCardImages().size() > 0);
		Assert.assertNotNull(info.getCardPrices());
		Assert.assertTrue(info.getCardPrices().size() > 0);
		Assert.assertNotNull(info.getCardSets());
		Assert.assertTrue(info.getCardSets().size() > 0);
	}

	@Test
	void testInfoServiceGetAllReptiles() {
		CardInfoService infoService = new CardInfoService(repo);
		List<CardInfo> reptiles = infoService.getAllReptileCards();
		Assert.assertNotNull(reptiles);
		Assert.assertTrue(reptiles.size() > 0);
	}

	@Test
	void testInfoServiceGetMainDeckReptiles() {
		CardInfoService infoService = new CardInfoService(repo);
		List<CardInfo> mainDeckReptiles = infoService.getMainDeckReptileCards();
		Assert.assertNotNull(mainDeckReptiles);
		Assert.assertTrue(mainDeckReptiles.size() > 0);
		for(CardInfo reptile: mainDeckReptiles) {
			String type = reptile.getType();
			Assert.assertTrue(!(type.contains("Link")|type.contains("XYZ")|type.contains("Syncro")|type.contains("Fusion")));
		}
	}

	@Test
	void testInfoServiceGetLevelFourReptiles() {
		CardInfoService infoService = new CardInfoService(repo);
		List<CardInfo> levelFourReptiles = infoService.getLevelFourReptiles();
		Assert.assertNotNull(levelFourReptiles);
		Assert.assertTrue(levelFourReptiles.size() > 0);
		for(CardInfo reptile: levelFourReptiles) {
			String type = reptile.getType();
			Assert.assertTrue(reptile.getLevel() == 4);
			Assert.assertTrue(!(type.contains("Link")|type.contains("XYZ")|type.contains("Syncro")|type.contains("Fusion")));
		}
	}
}
