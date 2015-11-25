package tests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.junit.Test;

import model.Card;
import model.CardType;
import model.Continent;
import model.Countries;
import model.Deck;
import model.Dice;
import model.EasyBot;
import model.Game;
import model.Human;
import model.Map;
import model.Player;
import model.RiskResources;
import model.Territory;

public class MasterTest
{

    @Test
    public void test()
    {
        Deck d = new Deck();

        assertEquals(42, d.size());

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

    @Test
    public void testMap()
    {
        Map map = new Map();

        assertEquals(map.getCountry(Countries.AFGHANISTAN).toString(),
                new Territory(Countries.AFGHANISTAN,0,0).toString());

        assertEquals(map.getUnclaimedTerritories().size(), 42);

        Player p = new EasyBot(0, 1, map);

        for (int i = 0; i < 69; i++)
            map.giveRandomTerritory(p);

        assertTrue(map.getUnclaimedTerritories().isEmpty());

        Card card = map.drawCard();

        map.returnCard(card);

        // Test getTerritory
        assertEquals(map.getTerritory("ALASKA").getCountry(), Countries.ALASKA);
        assertEquals(map.getTerritory("420 131aZe 1t 1aNd"), null);

        // test unclaimed
        ArrayList<Territory> unclaimed = (ArrayList<Territory>) map
                .getUnclaimedTerritories();
        String unclaimedString = map.listUnclaimed();

        String testString = "";
        for (Territory i : unclaimed)
        {
            testString += i.toString() + "\n";
        }

        assertEquals(unclaimedString, testString);

        EasyBot bot = new EasyBot(1, 10, map);

        assertTrue(map.listPlayerTerritories(bot).equals(""));

        bot.addTerritory(new Territory(Countries.ALASKA,0,0));
        ;
        System.out.println(bot.getTerroritories());

        // assertFalse(map.listPlayerTerritories(bot).equals(""));
        // Test exchangeCards
        /*
         * System.out.println(bot.getHandSize()); int botPreExchangeArmies =
         * bot.getArmies(); assertEquals(bot.getHandSize(), 0);
         * map.exchangeCards(bot); assertEquals(bot.getArmies(),
         * bot.getArmies()); bot.drawCard(); bot.drawCard(); bot.drawCard();
         * bot.drawCard(); bot.drawCard(); needs to be fixed!
         */
        // map.exchangeCards(p);
    }

    @Test
    public void testRiskResources()
    {
        RiskResources r = new RiskResources();
        assertEquals(42, r.getTerritories().size());
    }

    @Test
    public void testCard2()
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
    public void testContinent2()
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
    public void testDice2()
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

    @Test
    public void testPlayerAndBots()
    {
        Map b = new Map();
        EasyBot bot = new EasyBot(420, 100, b);

        // Armies tests
        assertEquals(bot.getArmies(), 100);
        assertTrue(bot.getArmies() != 200);

        // bot.placeDeployedArmies(armies);

        // Player ID tests
        assertTrue(bot.getPID() == 420);
        assertFalse(bot.getPID() != 420);

        // Get Territories tests
        assertEquals(0, bot.getTerritories().size());

        bot.addTerritory(new Territory(Countries.ALASKA,0,0));

        assertEquals(1, bot.getTerritories().size());
        assertEquals("1) ALASKA--0 armies\nArmies left 100\n",
                bot.getTerroritories());

        // Test Add armies
        // bot.addTerritory(new Territory(Countries.ALASKA));
        assertTrue(bot.getArmies() == 100);
        bot.addArmy(1);
        assertTrue(bot.getArmies() < 100);

        System.out.println(bot.getTotalTerritories());
        assertEquals(1, bot.getTotalTerritories());

        ArrayList<Territory> testList = new ArrayList<Territory>();
        testList.add(new Territory(Countries.ALASKA,0,0));
        ArrayList<Territory> getList = bot.getTerritories();
        for (int i = 0; i < getList.size(); i++)
        {
            assertTrue(getList.get(i).getCountry()
                    .equals(testList.get(i).getCountry()));
        }

        bot.playerOwnsTerritory(new Card(CardType.ARTILLERY, Countries.ALASKA));

        // Test redeem cards
        ArrayList<Card> cardTestList = new ArrayList<Card>();
        cardTestList.add(new Card(CardType.ARTILLERY, Countries.ALASKA));
        cardTestList.add(new Card(CardType.ARTILLERY, Countries.ALASKA));
        cardTestList.add(new Card(CardType.ARTILLERY, Countries.ALASKA));

        bot.setCards(cardTestList);
        assertTrue(b.exchangeCards(bot) > 0);
        cardTestList.removeAll(cardTestList);

        cardTestList.add(new Card(CardType.CALVARY, Countries.ALASKA));
        cardTestList.add(new Card(CardType.CALVARY, Countries.ALASKA));
        cardTestList.add(new Card(CardType.CALVARY, Countries.ALASKA));

        bot.setCards(cardTestList);
        assertTrue(b.exchangeCards(bot) > 0);
        cardTestList.removeAll(cardTestList);

        cardTestList.add(new Card(CardType.INFANTRY, Countries.ALASKA));
        cardTestList.add(new Card(CardType.INFANTRY, Countries.ALASKA));
        cardTestList.add(new Card(CardType.INFANTRY, Countries.ALASKA));

        bot.setCards(cardTestList);
        assertTrue(b.exchangeCards(bot) > 0);
        cardTestList.removeAll(cardTestList);

        cardTestList.add(new Card(CardType.CALVARY, Countries.ALASKA));
        cardTestList.add(new Card(CardType.ARTILLERY, Countries.ALASKA));
        cardTestList.add(new Card(CardType.INFANTRY, Countries.ALASKA));

        bot.setCards(cardTestList);
        assertTrue(b.exchangeCards(bot) > 0);
        cardTestList.removeAll(cardTestList);

        bot.setCards(cardTestList);
        assertTrue(b.exchangeCards(bot) == 0);

    }

    @Test
    public void testTerritory()
    {
        Territory gland = new Territory(Countries.GREENLAND,0,0);
        Territory na2 = new Territory(Countries.NW_TERRITORY,0,0);
        Territory na5 = new Territory(Countries.ONTARIO,0,0);
        Territory na6 = new Territory(Countries.QUEBEC,0,0);
        Territory eu1 = new Territory(Countries.ICELAND,0,0);
        Territory[] na3Adj = { na2, na5, na6, eu1 };

        gland.setAdj(na3Adj);

        assertEquals(gland.getArmies(), 0);

        assertFalse(gland.isOccupied());
        gland.addArmies(1);
        gland.changeOccupier(new Human(0, 0, new Map()));

        assertEquals(gland.toString(), "GREENLAND");
        assertEquals(gland.getContinent(), Continent.NAMERICA);
        assertEquals(gland.removeArmies(2), 1);
        gland.setArmies(3);

        assertEquals(gland.removeArmies(2), 1);
        gland.lose();

        assertEquals(Countries.GREENLAND, gland.getCountry());

        ArrayList<Territory> testAdjList = (ArrayList<Territory>) gland
                .getAdjacentTerritories();

        for (int i = 0; i < na3Adj.length; i++)
        {
            assertEquals(testAdjList.get(i), na3Adj[i]);
        }

        Human testPlayer = new Human(420, 1, new Map());
        gland.changeOccupier(testPlayer);
        assertEquals(testPlayer, gland.getOccupier());
        assertTrue(gland.isOccupied());

        assertTrue(gland.getColor().toString().equals("YELLOW"));

    }

    @Test
    public void testGame()
    {
        // Game game = new Game(1, 1);

        // Testing attack and resolve Attack

        // ByteArrayOutputStream out = new ByteArrayOutputStream();
        // ByteArrayOutputStream err = new ByteArrayOutputStream();

        // System.setOut(new PrintStream(out));
        // System.setErr(new PrintStream(err));

    }

    @Test
    public void constructorTest()
    {
        Map theWorld = new Map();
        Human danny = new Human(12, 0, theWorld);
        assertEquals(12, danny.getPID());
        assertEquals(0, danny.getArmies());
    }

    @Test
    public void fortifyTest()
    {
        // fail("We haven't written this method really, only when Ben was
        // high.");
    }

    // @Test
    // public void placeDeployedArmiesTest()
    // {
    // Map earth = new Map();
    // Player reaper = new Human(0, 666, earth);
    // reaper.placeDeployedArmies(3);
    // Scanner s = new Scanner(System.in);
    // /*
    // * I don't really know how to test this, all it does is: (1) it asks you
    // * for a territory name (2) subtracts an army from this player (3) adds
    // * an army to the territory (4) repeats until you place all bonus armies
    // * that were passed into this method
    // */
    // }

    @Test
    public void test2()
    {
        Map map = new Map();
        assertEquals(map.getCountry(Countries.AFGHANISTAN).toString(),
                new Territory(Countries.AFGHANISTAN,0,0).toString());
        System.out.println(map.listUnclaimed());
        assertEquals(map.getUnclaimedTerritories().size(), 42);
        Player p = new EasyBot(0, 1, map);
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
    public void testRiskResources1()
    {
        RiskResources r = new RiskResources();
        assertEquals(42, r.getTerritories().size());
    }

    @Test
    public void testCard1()
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
    public void testContinent1()
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
    public void testDice1()
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

    EasyBot bot;

    @Test
    public void testPlayer()
    {

    }

    @Test
    public void testConstructorAndGettersAndSetters()
    {
        Map b = new Map();
        bot = new EasyBot(420, 100, b);

        assertEquals(bot.getArmies(), 100);
        assertTrue(bot.getArmies() != 200);

        assertTrue(bot.getPID() == 420);

    }

    @Test
    public void test1()
    {
        Territory gland = new Territory(Countries.GREENLAND,0,0);
        Territory na2 = new Territory(Countries.NW_TERRITORY,0,0);
        Territory na5 = new Territory(Countries.ONTARIO,0,0);
        Territory na6 = new Territory(Countries.QUEBEC,0,0);
        Territory eu1 = new Territory(Countries.ICELAND,0,0);
        Territory[] na3Adj = { na2, na5, na6, eu1 };
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
