package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Map
{

    // MEMBER VARIABLES
    private ArrayList<Territory> territories;
    private List<Territory> unclaimedTerritories;
    private Deck deck;
    private int cardSetValue; // this was added here by Ben, moved from Game
    // class

    // CONSTRUCTOR
    public Map()
    {
        RiskResources r = new RiskResources();
        territories = r.getTerritories();
        deck = new Deck();
        unclaimedTerritories = getUnclaimedTerritories();
    }

    // PUBLIC METHODS
    // getters
    public Card drawCard()
    {
        return deck.dealCard();
    }

    public Territory getTerritory(String name)
    {
        for (Territory t : territories)
        {
            if (t.toString().equals(name))
            {
                return t;
            }
        }
        System.out.println("Territory not found by Map's method getTerritory.");
        return null;
    }

    // Returning null if the country can't be found is smart, but I don't think
    // it needs to be there
    // I can't figure out a way to test it -Danny
    public Territory getCountry(Countries c)
    {
        for (Territory t : territories)
        {
            if (c.toString().equals(t.toString())) return t;
        }
        System.out.println(
                "Error, territory not found by Map's getCountry method.");
        return null;
    }

    public String listUnclaimed()
    {
        String list = "";
        for (Territory t : territories)
        {
            if (t.getOccupier() == null)
            {
                list += t.toString() + "\n";
            }
        }
        return list;
    }

    public List<Territory> getUnclaimedTerritories()
    {
        List<Territory> result = new ArrayList<Territory>();
        for (Territory t : territories)
        {
            if (t.getOccupier() == null) result.add(t);
        }
        return result;
    }

    public String listPlayerTerritories(Player p)
    {
        String list = "";
        for (Territory t : territories)
        {
            if (t.getOccupier() == p)
            {
                list += t.toString() + "\n";
            }
        }
        return list;
    }

    // other
    public void returnCard(Card c)
    {
        deck.returnCardToDeck(c);
    }

    public void giveRandomTerritory(Player p)
    { // used when assigning territories at the start, NOTE: territories is
      // shuffled.
        if (unclaimedTerritories.size() == 0)
        {
            return;
        }

        Collections.shuffle(unclaimedTerritories);
        Territory temp = unclaimedTerritories.get(0); //p.claimTerritoryChoice(unclaimedTerritories);
        p.territoryObtained(temp);
        p.loseAnArmy();
        temp.changeOccupier(p);
        temp.setArmies(1);
        unclaimedTerritories.remove(0);

    }

    /*
     * Gives selected Territory to player,
     * @Param Player, player to give the territory to
     * @param Territory, Territory to give to the player
     */
    public void giveTerritory(Player playerToGiveTo, Territory territoryToGive)
    {

        playerToGiveTo.territoryObtained(territoryToGive);
        playerToGiveTo.loseAnArmy();

        territoryToGive.changeOccupier(playerToGiveTo);
        territoryToGive.setArmies(1);

        unclaimedTerritories.remove(territoryToGive);
    }

    // new method here
    public int exchangeCards(Player p)
    {
        ArrayList<Card> playersHand = p.cards; // get the players cards create
        // getter
        int bonus = 0;
        boolean willExchange = false;
        if (playersHand.size() >= 5)
        { // check if they HAVE TO exchange cards
            System.out.println(
                    "Because you have 5 or move cards, you must trade in this turn.");
            willExchange = true;
        }
        else if (playersHand.size() >= 3)
        { // check if they would like to
            willExchange = p.willTradeCards();
        }
        if (willExchange)
        { // do the actual exchange
          // Still needs logic to check if they are trading in a valid set
            System.out.println("You must trade in cards this turn.");
            System.out.println(
                    "Enter the int of the cards to trade in, one at a time.");
            ArrayList<Integer> choices = p.cardSetChoices();
            int first = choices.get(0);
            Card curr = playersHand.remove(first);
            Territory onCard1 = getCountry(curr.getCountry());
            if (onCard1 != null)
            {
                onCard1.addArmies(2);
                p.gainArmies(2);
            }
            deck.returnCardToDeck(curr);
            int second = choices.get(1) - 1; // -1 because we've already removed
                                             // a card
            curr = playersHand.remove(second);
            Territory onCard2 = getCountry(curr.getCountry());
            if (onCard2 != null)
            {
                onCard2.addArmies(2);
                p.gainArmies(2);
            }
            deck.returnCardToDeck(curr);
            int third = choices.get(2) - 2; // -2 because we've already removed
                                            // 2 cards
            curr = playersHand.remove(third);
            Territory onCard3 = getCountry(curr.getCountry());
            if (onCard3 != null)
            {
                onCard3.addArmies(2);
                p.gainArmies(2);
            }
            deck.returnCardToDeck(curr);
            bonus = cardSetValue;
            raiseCardSetValue();
        }
        return bonus;
    }

    private void raiseCardSetValue()
    {
        if (cardSetValue <= 10)
            cardSetValue += 2;
        else if (cardSetValue == 12)
            cardSetValue += 3;
        else
            cardSetValue += 5;
    }

    public ArrayList<Territory> getTerritories()
    {
        return territories;

    }

}
