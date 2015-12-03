package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

import model.Card;
import model.CardType;
import model.Continent;
import model.Countries;
import model.Dice;
import model.MediumBot;
import model.Map;
import model.Player;
import model.RiskResources;
import model.Territory;

public class MapTest
{

    @Test
    public void test()
    {
        Map map = new Map();
        assertEquals(map.getCountry(Countries.AFGHANISTAN).toString(),
                new Territory(Countries.AFGHANISTAN, 0, 0).toString());
        System.out.println(map.listUnclaimed());
        assertEquals(map.getUnclaimedTerritories().size(), 42);
        Player p = new MediumBot(0, 1, map);
        for (int i = 0; i < 69; i++)
            map.giveRandomTerritory(p);
        assertTrue(map.getUnclaimedTerritories().isEmpty());
        System.out.println(map.listUnclaimed());
        System.out.println(map.listPlayerTerritories(p));
        Card card = map.drawCard();
        map.returnCard(card);
        // map.exchangeCards(p);
    }

    @Test
    public void testMap()
    {

    }

    @Test
    public void testRiskResources()
    {
        RiskResources r = new RiskResources();
        assertEquals(42, r.getTerritories().size());
    }

    @Test
    public void testCard()
    {
        Card c = new Card(CardType.ARTILLERY, Countries.ALASKA);
        assertEquals(c.getCountry(), Countries.ALASKA);
        assertEquals(c.getCardType(), CardType.ARTILLERY);

        // BACK IN NAM
        assertEquals(Countries.ALASKA.getContinent(), Continent.NAMERICA);

        assertEquals(c.toString(), "ALASKAARTILLERY");
    }

    /*
     * TESTER FOR COUNTRIES.JAVA
     */
    @Test
    public void testContinent()
    {
        // NORTH AMERICA
        assertEquals(Countries.ALASKA.getContinent(), Continent.NAMERICA);
        assertEquals(Countries.NW_TERRITORY.getContinent(), Continent.NAMERICA);
        assertEquals(Countries.GREENLAND.getContinent(), Continent.NAMERICA);
        assertEquals(Countries.ONTARIO.getContinent(), Continent.NAMERICA);
        assertEquals(Countries.QUEBEC.getContinent(), Continent.NAMERICA);
        assertEquals(Countries.WESTERN_US.getContinent(), Continent.NAMERICA);
        assertEquals(Countries.EASTERN_US.getContinent(), Continent.NAMERICA);
        assertEquals(Countries.CENT_AMERICA.getContinent(), Continent.NAMERICA);
        assertEquals(Countries.ALBERTA.getContinent(), Continent.NAMERICA);

        // SOUTH AMERICA TESTS
        assertEquals(Countries.VENENZUELA.getContinent(), Continent.SAMERICA);
        assertEquals(Countries.PERU.getContinent(), Continent.SAMERICA);
        assertEquals(Countries.BRAZIL.getContinent(), Continent.SAMERICA);
        assertEquals(Countries.ARGENTINA.getContinent(), Continent.SAMERICA);

        assertEquals(Countries.ICELAND.getContinent(), Continent.EUROPE);
        assertEquals(Countries.SCANDINAVIA.getContinent(), Continent.EUROPE);
        assertEquals(Countries.UKRAINE.getContinent(), Continent.EUROPE);
        assertEquals(Countries.GREAT_BRITAIN.getContinent(), Continent.EUROPE);
        assertEquals(Countries.N_EUROPE.getContinent(), Continent.EUROPE);
        assertEquals(Countries.W_EUROPE.getContinent(), Continent.EUROPE);
        assertEquals(Countries.S_EUROPE.getContinent(), Continent.EUROPE);

        assertEquals(Countries.N_AFRICA.getContinent(), Continent.AFRICA);
        assertEquals(Countries.EGYPT.getContinent(), Continent.AFRICA);
        assertEquals(Countries.E_AFRICA.getContinent(), Continent.AFRICA);
        assertEquals(Countries.CONGO.getContinent(), Continent.AFRICA);
        assertEquals(Countries.S_AFRICA.getContinent(), Continent.AFRICA);
        assertEquals(Countries.MADAGASCAR.getContinent(), Continent.AFRICA);

        assertEquals(Countries.INDONESIA.getContinent(), Continent.AUSTRALIA);
        assertEquals(Countries.NEW_GUINEA.getContinent(), Continent.AUSTRALIA);
        assertEquals(Countries.W_AUSTRALIA.getContinent(), Continent.AUSTRALIA);
        assertEquals(Countries.E_AUSTRALIA.getContinent(), Continent.AUSTRALIA);

        assertEquals(Countries.CHINA.getContinent(), Continent.ASIA);

    }

    @Test
    public void testDice()
    {
        // Seed controlled tests for Dice.java,
        Random r = new Random(1);

        Dice d = new Dice(1);
        assertEquals(d.roll(), r.nextInt(6) + 1);

        Dice d1 = new Dice(1);
        int[] rollArray = new int[10];

        for (int i = 0; i < 10; i++)
        {
            rollArray[i] = d1.roll();
        }

        Dice d2 = new Dice(1);
        int[] testRollArray = d2.roll(10);

        for (int i = 0; i < 10; i++)
        {
            assertEquals(rollArray[i], testRollArray[i]);
        }

        ArrayList<Integer> roll2Tester = new ArrayList<Integer>();
        ArrayList<Integer> roll2Tester2 = new ArrayList<Integer>();

        Dice d3 = new Dice(1);
        Dice d4 = new Dice(1);

        roll2Tester = d3.roll2(10);

        for (int i = 0; i < 10; i++)
        {
            roll2Tester2.add(d4.roll());
        }

        for (int i = 0; i < 10; i++)
        {
            assertEquals(((Integer) roll2Tester2.get(i)), roll2Tester.get(i));
        }

    }
}
