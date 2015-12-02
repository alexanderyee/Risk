
package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/*
 * @author Ben Shields / Daniel Phillips (Mostly Ben)
 * @version 1.0
 * @since last updated:
 */
public abstract class Player
{

    // MEMBER VARIABLES
    private int playerID;
    private int totalArmies;
    private int totalTerritories;
    private String playerName;
    protected ArrayList<Territory> territories; // this had to be changed

    /*
     * Variables used to determine how many countries this player controls on
     * which continent
     */
    private int occupiedNAmerica;
    private int occupiedSAmerica;
    private int occupiedAsia;
    private int occupiedAutstralia;
    private int occupiedAfrica;
    private int occupiedEurope;

    protected ArrayList<Territory> terrNAmer = new ArrayList<Territory>();
    protected ArrayList<Territory> terrSAmer = new ArrayList<Territory>();
    protected ArrayList<Territory> terrAfrica = new ArrayList<Territory>();
    protected ArrayList<Territory> terrAustr = new ArrayList<Territory>();
    protected ArrayList<Territory> terrAsia = new ArrayList<Territory>();
    protected ArrayList<Territory> terrEuro = new ArrayList<Territory>();

    /*
     * Not sure about this - Danny
     */
    protected ArrayList<Card> cards;
    private Dice dice;
    protected Map map; // I don't know about this design choice but it's a
                       // placeholder - Ben

    /*
     * [1] Constructor: creates a player with a player ID, the inital amount of
     * armies and is passed the board state
     * @param int pid The player id, which is an int.
     * @param int initArmies The inital amount of armies to give to the player
     * when constructed
     * @param Board b A board object, this will represent the board that this
     * player will play on
     * @return void It's a constructor baka-chan!
     */
    public Player(int pid, int initArmies, Map b)
    {
        playerID = pid;
        totalArmies = initArmies;
        occupiedNAmerica = 0;
        occupiedSAmerica = 0;
        occupiedAsia = 0;
        occupiedAutstralia = 0;
        occupiedAfrica = 0;
        occupiedEurope = 0;
        dice = new Dice();
        map = b;
        territories = new ArrayList<Territory>();

    }

    public void setPlayerName(String name)
    {
        if (name == null)
        {
            if (getClass() != Human.class)
                this.playerName = "Bot" + String.valueOf(this.playerID);
            else
                this.playerName = "Human " + String.valueOf(this.playerID);
        }
        else
        {
            this.playerName = name;
        }
    }

    public String getPlayerName()
    {
        return this.playerName;
    }

    public void addTerritory(Territory t)
    {
        territories.add(t);
        if (getClass() != Human.class)
        {
            if (t.getContinent() == Continent.AFRICA)
                terrAfrica.add(t);
            else if (t.getContinent() == Continent.ASIA)
                terrAsia.add(t);
            else if (t.getContinent() == Continent.AUSTRALIA)
                terrAustr.add(t);
            else if (t.getContinent() == Continent.EUROPE)
                terrEuro.add(t);
            else if (t.getContinent() == Continent.NAMERICA)
                terrNAmer.add(t);
            else if (t.getContinent() == Continent.SAMERICA) terrSAmer.add(t);
        }
    }

    public void loseTerritory(Territory t)
    {
        territories.remove(t);
    }

    public String getTerroritories()
    {
        int count = 1;
        StringBuffer result = new StringBuffer();
        for (Territory t : territories)
        {
            result.append(count + ") " + t.toString() + "--" + t.getArmies()
                    + " armies");
            count++;
            result.append("\n");
        }
        result.append("Armies left " + this.totalArmies + "\n");
        return result.toString();
    }

    public void addArmy(int terrNumber)
    {
        if (totalArmies <= 0)
        {
            return;
        }
        territories.get(terrNumber - 1).addArmies(1);
        this.loseAnArmy();
    }

    // PUBLIC METHODS

    // getters

    /*
     * returns as an int the total armies this player object has
     * @return int An integer representing the total number of armies this
     * player object has
     */
    public int getArmies()
    {
        return totalArmies;
    }

    /*
     * returns an int of this Player object's player ID number
     * @return int A player Identification number to represent this player
     * object
     */
    public int getPID()
    {
        return getPlayerID();
    }

    /*
     * returns an int of the number of Territory objects this player controls
     * @return int representing the number of Territory objects this player
     * controls
     */
    public int getTotalTerritories()
    {
        return territories.size();
    }

    public ArrayList<Territory> getTerritories()
    {
        return territories;
    }

    /*
     * increments the total number of territories that this player has in
     * Continent c's area
     * @param Continent c A Continent object
     * @return void
     */
    public void territoryObtained(Territory t)
    {
        Continent c = t.getContinent();
        if (c == Continent.NAMERICA)
            occupiedNAmerica++;
        else if (c == Continent.SAMERICA)
            occupiedSAmerica++;
        else if (c == Continent.ASIA)
            occupiedAsia++;
        else if (c == Continent.AUSTRALIA)
            occupiedAutstralia++;
        else if (c == Continent.AFRICA)
            occupiedAfrica++;
        else if (c == Continent.EUROPE) occupiedEurope++;
        territories.add(t);
        totalTerritories++;

        if (this.getClass() == HardCpu.class)
        {
            if (c == Continent.NAMERICA)
                terrNAmer.add(t);
            else if (c == Continent.SAMERICA)
                terrSAmer.add(t);
            else if (c == Continent.ASIA)
                terrAsia.add(t);
            else if (c == Continent.AUSTRALIA)
                terrAustr.add(t);
            else if (c == Continent.AFRICA)
                terrAfrica.add(t);
            else if (c == Continent.EUROPE) terrEuro.add(t);
        }
    }

    /*
     * decrements this player object's current armies by 1
     * @return void
     */
    public void loseAnArmy()
    {
        totalArmies--;
    }

    /*
     * adds n armies to this player objects army count
     * @param int The amount of armies that this player will recieve
     */
    public void gainArmies(int n)
    {
        totalArmies += n;
    }

    // controller methods

    /*
     * rolls the dice for this player object
     * @param int The number of dice that this player object will roll
     * @return int[] returns an array of the dice rolls
     */
    public int[] rollDice(int numDiceRolled)
    {
        return dice.roll(numDiceRolled);
    }

    // ~~~~~~~~~~~~~~~~~~~CHECK THIS COMMENT BEN~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Checked, Ben
    /*
     * Gives the player the amount of armies it would receive from the
     * (end)->(beginning) of the turn. Accounts for both cards that this player
     * has, the amount of armies that this player would receive for the amount
     * of territories, and the bonus for a control over a continent.
     * @param int the bonus card set value that is currently being offered by
     * the deck
     * @return boolean
     */

    // This method should replace depoloy by moving the card-set turn-in logic
    // to the Map class
    public int deploy()
    {
        return fromTerritories() + fromContinents();
    }

    /*
     * Draws a card from the Game's object Board's object Deck and adds it to
     * the players hand
     * @return void
     */
    public void drawCard()
    {
        // takes a card from Game's Board's Deck to add to this Player's hand of
        // cards
        cards.add(map.drawCard());
    }

    public void setCards(ArrayList<Card> newHand)
    {
        cards = newHand;
    }

    public int getHandSize()
    {
        return cards.size();
    }

    abstract public boolean willTradeCards();

    abstract public ArrayList<Integer> cardSetChoices();

    public void playerOwnsTerritory(Card c)
    {
        for (Territory t : territories)
        {
            if (t.getCountry().equals(c.getCountry()))
            {
                t.addArmies(2);
            }
        }
    }

    abstract public void fortify();

    // PRIVATE METHODS

    // deploy methods
    /*
     * Used in Deploy, calculates then number of troops that you receive from
     * your territories minimum of three armies given to the player object
     * @return int the number of soldiers that this player object receives
     */
    private int fromTerritories()
    {
        return Math.max(totalTerritories % 3, 3); // 3 is the min armies you get
                                                  // to deploy regardless of the
                                                  // number of territories
                                                  // controlled
    }

    /*
     * Calculates the number of soldiers you receive from the continents you
     * control, only bonuses minimum recieved: 0
     * @return int The number of armies that this player will recieve
     */
    private int fromContinents()
    {
        int bonus = 0;
        if (occupiedNAmerica == 9) bonus += 5;
        if (occupiedSAmerica == 4) bonus += 2;
        if (occupiedAsia == 12) bonus += 7;
        if (occupiedAutstralia == 4) bonus += 2;
        if (occupiedAfrica == 6) bonus += 3;
        if (occupiedEurope == 7) bonus += 5;
        return bonus;
    }

    // attacking methods
    /*
     * @author Danny Note: Ben if this isn't supposed to be the functionality of
     * this method just text me and ill change it Lets the player choose to
     * continue attacking or not This should be attached to a GUI module or
     * button to let the player choose if he wants to continue attacking or not
     */
    public abstract Territory claimTerritory(List<Territory> list);
    
    public abstract boolean willAttack();

    public abstract int attackFrom();

    public abstract int attackAt(int attackingTerritoryNumber);

    public abstract boolean attackAgain();

    public abstract boolean willFortify();

    public abstract int attackDice(int armies);

    public abstract int defenseDice(int atkPID, String defStr, int atkDice,
            int armies);

    // deploy methods

    abstract public void placeDeployedArmies(int armies);

    public int getPlayerID()
    {
        return playerID;
    }

    public Color getColor()
    {
        if (this.playerID == 0) return Color.RED;
        if (this.playerID == 1) return Color.BLUE;
        if (this.playerID == 2) return Color.YELLOW;
        if (this.playerID == 3) return Color.GREEN;
        if (this.playerID == 4) return new Color(51, 25, 0);
        if (this.playerID == 5) return Color.MAGENTA;
        return Color.BLACK;
    }

}
