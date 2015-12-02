
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class EasyBot extends Player
{

    private Random r;
    private int pid;
    private ArrayList<String> realThoughts = new ArrayList<String>();

    /*
     * Represents the Easy AI bot in Risk, will choose pseudo random decisions
     */
    public EasyBot(int pid, int initArmies, Map b)
    {
        super(pid, initArmies, b);
        r = new Random();
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
    public void fortify()
    {
        boolean thinking = true;
        while (thinking)
        {
            System.out.println(this.getTerroritories());

            System.out
                    .println(realThoughts.get(r.nextInt(realThoughts.size()))); // says
                                                                                // a
                                                                                // thing
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
                System.out.println(
                        realThoughts.get(r.nextInt(realThoughts.size()))); // says
                                                                           // a
                                                                           // thing
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
                System.out.println(
                        realThoughts.get(r.nextInt(realThoughts.size()))); // says
                                                                           // a
                                                                           // thing
                int move = r.nextInt(fortifyFrom.getArmies() - 1);
                fortifyFrom.addArmies(move * -1);
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
        System.out.println("EasyBot " + this.pid + "has deployed armies");
    }

    @Override
    public boolean willAttack()
    {
        return true;
    }

    @Override
    public int attackFrom()
    {
        ArrayList<Territory> adjList = null;
        ArrayList<Integer> validChoices = new ArrayList<Integer>();
        for (int i = 0; i < getTotalTerritories(); i++)
        {
            if (territories.get(i).getArmies() > 1)
            {
                adjList = (ArrayList<Territory>) territories.get(i)
                        .getAdjacentTerritories();
                for (int j = 0; j < adjList.size(); j++)
                { // if(not attacking yourself
                    if (!(adjList.get(j).getOccupier()
                            .getPlayerID() == getPlayerID()))
                    {
                        validChoices.add(i);
                        /*
                         * we have to make sure that only territories that are
                         * adj to enemy territories are selected as options
                         */
                    }
                }
            }
        }
        int choice = r.nextInt(validChoices.size());
        while (!validChoices.contains(choice)
                || territories.get(choice).getArmies() < 2)
        {
            choice = r.nextInt(validChoices.size());
        }
        System.out.println(realThoughts.get(r.nextInt(realThoughts.size()))); // says
                                                                              // a
                                                                              // thing
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
        return choice;
    }

    @Override
    public boolean attackAgain() // always attacks again until it can't
    {
        for (Territory t : getTerritories())
        {
            System.out
                    .println(realThoughts.get(r.nextInt(realThoughts.size()))); // says
                                                                                // a
                                                                                // thing
            if (t.getArmies() > 1) return true;
        }
        System.out.println(realThoughts.get(r.nextInt(realThoughts.size()))); // says
                                                                              // a
                                                                              // thing
        return false;
    }

    @Override
    public boolean willFortify()
    {
        return true;
    }

    @Override
    public int attackDice(int armies)
    {
        System.out.println(realThoughts.get(r.nextInt(realThoughts.size()))); // says
                                                                              // a
                                                                              // thing
        return 1;
    }

    @Override
    public int defenseDice(int atkPID, String defStr, int atkDice, int armies)
    {
        System.out.println(realThoughts.get(r.nextInt(realThoughts.size()))); // says
                                                                              // a
                                                                              // thing
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

}
