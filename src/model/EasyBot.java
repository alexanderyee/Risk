
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EasyBot extends Player
{

    private Random r;
    private int pid;
    private ArrayList<String> realThoughts = new ArrayList<String>();
    private ArrayList<Territory> validChoices = new ArrayList<Territory>();

    /*
     * Represents the Easy AI bot in Risk, will choose pseudo random decisions
     */
    public EasyBot(int pid, int initArmies, Map b)
    {
        super(pid, initArmies, b);
        r = new Random(2); //THIS IS JUST FOR TESTING Run6Bots
        this.pid = pid;
        addThoughts();
        
    }

    public EasyBot(int pid, int initArmies, Map b, Random s)
    {
        super(pid, initArmies, b);
        this.r = s;
        this.pid = pid;
        addThoughts();
    }

    private void addThoughts()
    {
        realThoughts.add("When faced with a questi0n I pray to rngesus :^)");
        realThoughts.add(
                "Just kissin the dice and rollin snake eyes for sure dude");
        realThoughts.add("let me think about this... or not");
        realThoughts.add("wHEN FACED WITH A QUEST1ON i PRAY TO RNGESUS ;60");
        realThoughts.add("ihavenomoreprogrammedsayings");
    }

    @Override
    public Territory claimTerritory(List<Territory> territories)
    {
        for(Territory i : territories)
        {
            if(i.getOccupier() == null)
            {
                return i;
            }
        }
        return null;
    }
    
    @Override
    public void fortify()
    {
        boolean thinking = true;
        while (thinking)
        {
            System.out.println(this.getTerroritories());

//            System.out
//                    .println(realThoughts.get(r.nextInt(realThoughts.size()))); // says
//                                                                                // a
//                                                                                // thing
            System.out.println("Player "+this.pid+", is foritfying.");
            Territory fortifyTo = getTerritories()
                    .get(r.nextInt(getTerritories().size()));
            int times = 0;
            while (fortifyTo.getArmies() < 1 && times < getTerritories().size())
            {
                fortifyTo = getTerritories()
                        .get(r.nextInt(getTerritories().size()));
                times++;
            }
            if (times == getTerritories().size())
            {
                System.out.println(
                        "i give up, this one is beyond me, I choose to not fortify");
                thinking = false;
            }
            else
            {
                List<Territory> possible = fortifyTo.getAdjacentTerritories();
//                System.out.println(
//                        realThoughts.get(r.nextInt(realThoughts.size()))); // says
//                                                                           // a
//                                                                           // thing
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
                    System.out.println(
                            "i give up, this one is beyond me, I choose to not fortify");
                    thinking = false;
                }
                Territory fortifyFrom = t;
                System.out.println("Hmm how many armies should I move from "
                        + fortifyFrom.toString() + " to " + fortifyTo.toString()
                        + ", less than " + (fortifyFrom.getArmies() - 1));
//                System.out.println(
//                        realThoughts.get(r.nextInt(realThoughts.size()))); // says
//                                                                           // a
//                                                                           // thing
                int move = r.nextInt(fortifyFrom.getArmies() - 1);
                fortifyFrom.addArmies(move * -1);
                System.out.println("Player "+this.pid+", is done foritfying.");
            }
        }
    }

    @Override
    public void placeDeployedArmiesRand(int armies)
    {
        Territory terr;
        for (int i = 0; i < armies; i++)
        {
            terr = territories.get(r.nextInt(2));
            terr.addArmies(1);
            loseAnArmy();
        }
        System.out.println("EasyBot " + this.pid + " has deployed armies");
    }

    @Override
    public boolean willAttack()
    {
        validChoices.removeAll(validChoices);
        ArrayList<Territory> adjList = null;
        for(Territory terr : this.territories) //go through our territories
        {
            if(terr.getArmies() >= 2) //if one has at least 2 territories it can attack as long as...
            {
                adjList = (ArrayList<Territory>) terr.getAdjacentTerritories();
                for (int j = 0; j < adjList.size(); j++) //make sure you have a valid enemy target from that location
                { // if(not attacking yourself
                    if (!(adjList.get(j).getOccupier()
                            .getPlayerID() == getPlayerID()))
                    {
                        validChoices.add(terr);
                    }
                }
            }
        }
        System.out.println("ValidChoices: \n" + validChoices);
        boolean canAttack = !validChoices.isEmpty();
        return canAttack;
    }

    @Override
    public int attackFrom()
    {
        int choice = r.nextInt(validChoices.size()); //bound must be positive
        Territory from = validChoices.get(choice);
//        System.out.println(realThoughts.get(r.nextInt(realThoughts.size()))); // says
//                                                                              // a
//                                                                              // thing
        System.out.println("Player "+this.pid+" is attacking from "+from+".");
        return choice;
    }

    @Override
    public int attackAt(int atkTerrNum)
    {

        Territory from = this.getTerritories().get(atkTerrNum);
        int choice = -1;
        boolean keepGoing = true;
        while (keepGoing)
        {
            choice = r.nextInt(from.getAdjacentTerritories().size());
            if (from.getAdjacentTerritories().get(choice).getOccupier() != this)
                keepGoing = false;
        }
        Territory at = from.getAdjacentTerritories().get(choice);
        System.out.println("Player "+this.pid+" is attacking at "+at+".");
        return choice;
    }

    @Override
    public boolean attackAgain() // always attacks again until it can't
    {
        System.out.println("Player "+this.pid+" is an EasyBot and will attack until it can't.");
        for (Territory t : getTerritories())
        {
            if (t.getArmies() > 1) return true;
        }
                                                                             
        System.out.println("Player "+this.pid+" can no longer attack and is therefore done attacking.");
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
//        System.out.println(realThoughts.get(r.nextInt(realThoughts.size()))); // says
//                                                                              // a
//                                                                              // thing
        return 1;
    }

    @Override
    public int defenseDice(int atkPID, String defStr, int atkDice, int armies)
    {
//        System.out.println(realThoughts.get(r.nextInt(realThoughts.size()))); // says
//                                                                              // a
//                                                                              // thing
        return 1;
    }

    @Override
    public int attackInvade()
    {
        // TODO Auto-generated method stub
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
        System.out.println("Player "+this.pid+" is thinking about card sets.");
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

}
