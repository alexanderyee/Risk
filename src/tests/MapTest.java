package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Continent;
import model.Countries;
import model.Map;
import model.Territory;

public class MapTest {

	@Test
	public void test() {
		Map map = new Map();
		assertEquals(map.getCountry(Countries.AFGHANISTAN), new Territory(Countries.AFGHANISTAN));
	}

}
