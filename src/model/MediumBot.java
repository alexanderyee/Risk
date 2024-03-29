package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MediumBot extends Player
{

    private Random r;
    private int pid;
    private int atkFrom;
    private int atkTo;
    private ArrayList<String> realThoughts = new ArrayList<String>();
    private ArrayList<Territory> validChoices = new ArrayList<Territory>();
    private int numTerrsAtBeginningOfTurn;
    private int attacksThisTurn;

    /*
     * Represents the Intermediate AI bot in Risk, will choose a single
     * advantageous attack per turn
     */
    public MediumBot(int pid, int initArmies, Map b)
    {
        super(pid, initArmies, b);
        r = new Random(2);
        this.pid = pid;
        addThoughts();
        
    }

    public MediumBot(int pid, int initArmies, Map b, Random s)
    {
        super(pid, initArmies, b);
        this.r = s;
        this.pid = pid;
        addThoughts();
    }

    private void addThoughts()
    {
        realThoughts.add("I know how to do arithmetic");
        realThoughts.add(
                "I am even programmed to actually consider all of my territories");
        realThoughts.add("Are you bad at this game? or am I...");
        realThoughts.add("just pullin");
        realThoughts.add("I was written in assembly language");
    }

    @Override
    public void fortify()
    {
        boolean thinking = true;
        while (thinking)
        {

            Territory fortifyTo = territories
                    .get(r.nextInt(territories.size()));
            int times = 0;
            while (fortifyTo.getArmies() < 1 && times < territories.size())
            {
                fortifyTo = territories.get(r.nextInt(territories.size()));
                times++;
            }
            if (times == territories.size())
            {

                thinking = false;
            }
            else
            {
                List<Territory> possible = fortifyTo.getAdjacentTerritories();

                Territory t = possible.get(r.nextInt(possible.size()));
                int counter = 0;
                while (!(t.getOccupier().equals(this))
                        && counter < possible.size())
                {
                    t = possible.get(r.nextInt(possible.size()));
                    counter++;
                }
                if (counter == possible.size())
                {
                    thinking = false;
                }
                Territory fortifyFrom = t;
                int move = r.nextInt(fortifyFrom.getArmies() - 1);
                fortifyFrom.addArmies(move * -1);
            }
        }
    }

    @Override
    public void placeDeployedArmiesRand(int armies)
    {
        for (int i = 0; i < armies; i++)
        {
            Territory least = territories.get(r.nextInt(territories.size()));
            least.addArmies(1);
            loseAnArmy();
        }
    }

    @Override
    public boolean willAttack()
    {
        if (territories.size() == 42) // I won!
            return false;
        ArrayList<Territory> newValidChoices = new ArrayList<Territory>();
        ArrayList<Territory> adjList = null;
        for (Territory terr : territories) // go through our territories
        {
            if (terr.getArmies() >= 2) // if one has at least 2 territories it
                                       // can attack as long as...
            {
                adjList = (ArrayList<Territory>) terr.getAdjacentTerritories();
                for (Territory enemy : adjList) // make sure you have a valid
                                                // enemy target from that
                                                // location
                { // if(not attacking yourself
                    if (enemy.getOccupier().getPlayerID() != pid)
                    {
                        newValidChoices.add(terr);
                        break; // we only need to add it once
                    }
                }
            }
        }
        validChoices = newValidChoices;
        boolean canAttack = !validChoices.isEmpty();
        numTerrsAtBeginningOfTurn = territories.size();
        return canAttack;
    }

    @Override
    public int attackFrom()
    {
        atkFrom = 0;
        atkTo = 0;
        int maxDif = 0;
        int from;
        int at;
        Territory t;
        Territory e;
        for (int i = 0; i < validChoices.size(); i++) // go through my terr's
        {
            t = validChoices.get(i);
            if (t.getArmies() > 1) // if I can attack from it
            {
                from = t.getArmies(); // check armies here
                for (int j = 0; j < t.getAdjacentTerritories().size(); j++) // go
                                                                            // through
                                                                            // adjacents
                {
                    e = t.getAdjacentTerritories().get(j);
                    if (e.getOccupier() != this) // if it's an enemy terr
                    {
                        at = e.getArmies(); // get its armies
                        if ((from - at) > maxDif)
                        {
                            maxDif = from - at;
                            atkFrom = i; // choose to attack where I have
                            // greatest advantage
                            atkTo = j;
                        }
                    }
                }
            }
        }
        Territory tFrom = validChoices.get(atkFrom);
        int indexOfActualTerrFrom = territories.indexOf(tFrom);
        Territory tTo = tFrom.getAdjacentTerritories().get(atkTo);

        return indexOfActualTerrFrom;
    }

    @Override
    public int attackAt(int atkTerrNum)
    {
        Territory atkAt = territories.get(atkTerrNum).getAdjacentTerritories()
                .get(atkTo);
        return atkTo;
    }

    @Override
    public boolean attackAgain()
    {
        attacksThisTurn++;
        if(attacksThisTurn >= 2 && this.limitedAttacks) //for GUI
        {
            attacksThisTurn = 0;
            return false;
        }
        if(attacksThisTurn >= 10) //for run6bots
        {
            attacksThisTurn = 0;
            return false;
        }
        if (territories.size() == 42) // If you won, don't attack again
            return false;
        ArrayList<Territory> newValidChoices = new ArrayList<Territory>(); 
        // otherwise check if CAN attack
        ArrayList<Territory> adjList = null;
        for (Territory terr : territories) // go through our territories
        {
            if (terr.getArmies() >= 2) // if one has at least 2 territories it
                                       // can attack as long as...
            {
                adjList = (ArrayList<Territory>) terr.getAdjacentTerritories();
                for (Territory enemy : adjList) // make sure you have a valid
                                                // enemy target from that
                                                // location
                { // if(not attacking yourself
                    if (enemy.getOccupier().getPlayerID() != pid)
                    {
                        newValidChoices.add(terr);
                        break; // we only need to add it once
                    }
                }
            }
        }
                                                                             
        validChoices = newValidChoices;
        boolean canAttack = !validChoices.isEmpty();
        // if CAN and you're winning, keep going
        if (canAttack && territories.size() >= numTerrsAtBeginningOfTurn)
            return true;
        else // otherwise quit
            return false;
    }

    @Override
    public boolean willFortify()
    {
        return false;
    }

    @Override
    public int attackDice(int armies)
    {
        if (armies > 3)
            return 3;
        else if (armies > 2)
            return 2;
        else
            return 1;
    }

    @Override
    public int defenseDice(int atkPID, String defStr, int atkDice, int armies)
    {
        if (armies > 1)
            return 2;
        else
            return 1;
    }

    @Override
    public boolean willTradeCards()
    {
        if (cards.size() == 5)
            return true;
        else
            return false;
    }

    public ArrayList<Integer> cardSetChoices()
    {
        ArrayList<Integer> choices = new ArrayList<Integer>(3);
        int matches = 0;
        for (int i = 0; i < cards.size(); i++) // for each card in hand
        {
            choices.add(i);
            for (int j = 0; j < cards.size(); j++) // compare the other cards to
                                                   // i
            {
                if (i != j && cards.get(i).getCardType() == cards.get(j)
                        .getCardType())
                { // find 3 matches
                    matches++;
                    choices.add(j);
                }
            }
            if (matches == 3)
                return choices;
            else if (matches == 0) // or find 3 cards of 3 kinds
            {
                for (int j = 0; j < cards.size(); j++)
                {
                    if (i != j && cards.get(i).getCardType() != cards.get(j)
                            .getCardType())
                        choices.add(j);
                }
                return choices;
            }
        }
        return choices;
    }

    @Override
    public Territory claimTerritory(List<Territory> list)
    {
        for (Territory i : territories)
        {
            if (i.getOccupier() == null)
            {
                return i;
            }
        }
        return null;
    }

    @Override
    public int attackInvade(int armies)
    {
        return armies - 1; // invade with max num of troops possible?
    }

}
