package com.kotfi.FeralImpsSimulator;

import com.kotfi.FeralImpsSimulator.models.CardInfo;
import com.kotfi.FeralImpsSimulator.services.CardInfoService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FeralImpsSimulatorApplicationTests {
	private static final CardInfoService infoService = new CardInfoService();

	@Test
	void contextLoads() {
	}

	@Test
	void testSerializationFromAPI() {
		String cardName = "King of the Feral Imps";
		CardInfo info = infoService.getCardByName(cardName);
		Assert.assertEquals(info.getName(), cardName);
		Assert.assertNotNull(info.getCardImages());
		Assert.assertTrue(info.getCardImages().size() > 0);
		Assert.assertNotNull(info.getCardPrices());
		Assert.assertTrue(info.getCardPrices().size() > 0);
		Assert.assertNotNull(info.getCardSets());
		Assert.assertTrue(info.getCardSets().size() > 0);
	}
}
