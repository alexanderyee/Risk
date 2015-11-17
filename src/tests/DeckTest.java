package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Card;
import model.Deck;

public class DeckTest
{

    @Test
    public void test()
    {
        Deck d = new Deck();

        assertEquals(42, d.size());
        
    }

}
