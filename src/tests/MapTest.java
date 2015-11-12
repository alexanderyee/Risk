package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import model.Bot;
import model.Countries;
import model.Map;
import model.Territory;

public class MapTest {

	@Test
	public void test() {
		Map map = new Map();
		assertEquals(map.getCountry(Countries.AFGHANISTAN).toString(), new Territory(Countries.AFGHANISTAN).toString());
		System.out.println(map.listUnclaimed());
		assertEquals(map.getUnclaimedTerritories().size(), 42);
		for (int i = 0; i < 42; i++)
			map.giveRandomTerritory(new Bot(0, 1, map));
		assertTrue(map.getUnclaimedTerritories().isEmpty());
	}
}
