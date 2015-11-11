package tests;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import model.Continent;
import model.Countries;
import model.Territory;

public class TerritoryTest {

	@Test
	public void test() {
		Territory gland = new Territory(Continent.NAMERICA, Countries.GREENLAND);
		Territory na2 = new Territory(Continent.NAMERICA, Countries.NW_TERRITORY);
		Territory na5 = new Territory(Continent.NAMERICA, Countries.ONTARIO);
		Territory na6 = new Territory(Continent.NAMERICA, Countries.QUEBEC);
		Territory eu1 = new Territory(Continent.EUROPE,Countries. ICELAND);
		Territory[] na3Adj = {na2, na5, na6, eu1};
		gland.setAdj(na3Adj);
		assertEquals(gland.getColor(), Color.YELLOW);
	}
	
}
