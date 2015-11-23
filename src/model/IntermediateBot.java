package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IntermediateBot extends Player
{

    private Random r;
    private int pid;
    private int atkFrom;
    private int atkTo;
    private ArrayList<String> realThoughts = new ArrayList<String>();

    /*
     * Represents the Intermediate AI bot in Risk, will choose a single
     * advantageous attack per turn
     */
    public IntermediateBot(int pid, int initArmies, Map b)
    {
        super(pid, initArmies, b);
        r = new Random();
        this.pid = pid;
        addThoughts();
    }

    public IntermediateBot(int pid, int initArmies, Map b, Random s)
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
            System.out.println(this.getTerroritories());

            System.out
                    .println(realThoughts.get(r.nextInt(realThoughts.size()))); // says
                                                                                // a
                                                                                // thing
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
                System.out.println(
                        "i give up, this one is beyond me, I choose to not fortify");
                thinking = false;
            }
            else
            {
                List<Territory> possible = fortifyTo.getAdjacentTerritories();
                System.out.println(
                        "Let me choose a territory to fortify troops from.");
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
                int move = r.nextInt(fortifyFrom.getArmies() - 1);
                fortifyFrom.addArmies(move * -1);
            }
        }
    }

    @Override
    public void placeDeployedArmies(int armies)
    {
        Territory terr;

        for (int i = 0; i < armies; i++)
        {
            terr = territories.get(r.nextInt(2));
            terr.addArmies(1);
            loseAnArmy();
        }
        System.out
                .println("IntermediateBot " + this.pid + "has deployed armies");
    }

    @Override
    public boolean willAttack()
    {
        return true;
    }

    @Override
    public int attackFrom()
    {
        int maxDif = 0;
        int from;
        int at;
        int choice = 0;
        int choice2 = 0;
        for (Territory t : getTerritories()) // go through my terr's
        {
            if (t.getArmies() > 1) // if I can attack from it
            {
                from = t.getArmies(); // check armies here
                for (Territory e : t.getAdjacentTerritories()) // go through
                // adjacents
                {
                    if (e.getOccupier() != this) // if it's an enemy terr
                    {
                        at = e.getArmies(); // get its armies
                        if ((from - at) > maxDif)
                        {
                            maxDif = from - at;
                            atkFrom = choice; // choose to attack where I have
                            // greatest advantage
                            atkTo = choice2;
                        }
                    }
                    choice2++;
                }
            }
            choice++;
        }
        return atkFrom;
    }

    @Override
    public int attackAt(int atkTerrNum)
    {
        return atkTo;
    }

    @Override
    public boolean attackAgain()
    {
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
        return 1;
    }

    @Override
    public int defenseDice(int atkPID, String defStr, int atkDice, int armies)
    {
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
