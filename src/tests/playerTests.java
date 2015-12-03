package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import model.*;

public class playerTests
{
    MediumBot bot;

    @Test
    public void testPlayer()
    {

    }

    @Test
    public void testConstructorAndGettersAndSetters()
    {
        Map b = new Map();
        bot = new MediumBot(420, 100, b);

        assertEquals(bot.getArmies(), 100);
        assertTrue(bot.getArmies() != 200);

        assertTrue(bot.getPID() == 420);

    }
}
