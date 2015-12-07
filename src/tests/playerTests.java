package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import model.*;

public class playerTests
{
    MediumBot mbot;
    EasyBot ebot;
    HardCpu hbot;
    Human human;
    @Test
    public void testPlayer()
    {
        
    }

    @Test
    public void testConstructorAndGettersAndSetters()
    {
        Map b = new Map();
      
        assertEquals(mbot.getArmies(), 100);
        assertTrue(mbot.getArmies() != 200);

        assertTrue(mbot.getPID() == 420);

    }
}
