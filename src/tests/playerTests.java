<<<<<<< HEAD
package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.*;

public class playerTests
{
    EasyBot bot;
    @Test
    public void testPlayer()
    {
        
    }
    
    @Test
    public void testConstructorAndGettersAndSetters()
    {
        Map b = new Map();
        bot = new EasyBot(420, 100, b, 1);
        
        assertEquals(bot.getArmies(), 100);
        assertFalse(bot.getArmies() != 200);
        
        assertTrue(bot.getPID() == 420);
        assertFalse(bot.getPID() == 420);
        
        
        
    }
}
=======
package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.*;

public class playerTests
{
    EasyBot bot;
    @Test
    public void testPlayer()
    {
        
    }
    
    @Test
    public void testConstructorAndGettersAndSetters()
    {
        Map b = new Map();
        bot = new EasyBot(420, 100, b, 1);
        
        assertEquals(bot.getArmies(), 100);
        assertFalse(bot.getArmies() != 200);
        
        assertTrue(bot.getPID() == 420);
        assertFalse(bot.getPID() == 420);
        
        
        
    }
}
>>>>>>> 17eddd4f179a9ddd369f90590236b3d1821709fc
