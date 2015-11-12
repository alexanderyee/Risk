package tests;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.Test;

import model.Continent;
import model.Countries;
import model.Human;
import model.Map;
import model.Territory;

public class TerritoryTest
{

    @Test
    public void test()
    {
        Territory gland = new Territory(Continent.NAMERICA, Countries.GREENLAND);
        Territory na2 = new Territory(Continent.NAMERICA, Countries.NW_TERRITORY);
        Territory na5 = new Territory(Continent.NAMERICA, Countries.ONTARIO);
        Territory na6 = new Territory(Continent.NAMERICA, Countries.QUEBEC);
        Territory eu1 = new Territory(Continent.EUROPE, Countries.ICELAND);
        Territory[] na3Adj =
        { na2, na5, na6, eu1 };
        gland.setAdj(na3Adj);

        assertEquals(gland.getArmies(), 0);
        gland.addArmies(1);
        gland.changeOccupier(new Human(0, 0, new Map()));
        assertEquals(gland.toString(), "GREENLAND");
        assertEquals(gland.getContinent(), Continent.NAMERICA);
        assertEquals(gland.removeArmies(2), 1);
        gland.setArmies(3);
        assertEquals(gland.removeArmies(2), 1);
        gland.lose();

    }

}
