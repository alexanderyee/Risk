package tests;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Test;

import model.Human;
import model.Map;
import model.Player;

public class HumanTests
{

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
        fail("We haven't written this method really, only when Ben was high.");
    }

    @Test
    public void placeDeployedArmiesTest()
    {
        Map earth = new Map();
        Player reaper = new Human(0, 666, earth);
        reaper.placeDeployedArmies(3);
        Scanner s = new Scanner(System.in);
        /*
         * I don't really know how to test this, all it does is: (1) it asks you
         * for a territory name (2) subtracts an army from this player (3) adds
         * an army to the territory (4) repeats until you place all bonus armies
         * that were passed into this method
         */
    }

}
