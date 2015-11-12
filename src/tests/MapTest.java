package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import model.Card;
import model.Countries;
import model.EasyBot;
import model.Map;
import model.Player;
import model.Territory;

public class MapTest
{

    @Test
    public void test()
    {
        Map map = new Map();
        assertEquals(map.getCountry(Countries.AFGHANISTAN).toString(),
                new Territory(Countries.AFGHANISTAN).toString());
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
}
