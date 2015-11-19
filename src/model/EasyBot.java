package model;

import java.util.ArrayList;
import java.util.Random;

public class EasyBot extends Player
{

    private Random r;
    private int pid;

    /*
     * Represents the Easy AI bot in Risk, will choose pseudo random decisions
     */
    public EasyBot(int pid, int initArmies, Map b)
    {
        super(pid, initArmies, b);
        r = new Random();
        this.pid = pid;
    }

    public EasyBot(int pid, int initArmies, Map b, Random s)
    {
        super(pid, initArmies, b);
        this.r = s;
        this.pid = pid;
    }

    @Override
    public void fortify()
    {
        // implement later when human's fortify is done.
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
        ArrayList<Territory> tList = getTerritories();
        for (int i = 0; i < getTotalTerritories(); i++)
        {
            adjList = (ArrayList<Territory>) tList.get(i)
                    .getAdjacentTerritories();
            for (int j = 0; j < adjList.size(); j++)
            { // if(not attacking yourself
                if (!(adjList.get(j).getOccupier()
                        .getPlayerID() == getPlayerID())
                        && adjList.get(j).getArmies() > 1)
                {
                    validChoices.add(i);
                    /*
                     * we have to make sure that only territories that are adj
                     * to enemy territories are selected as options
                     */
                }
            }
        }
        int choice = r.nextInt(validChoices.size());
        while (!validChoices.contains(choice)
                || tList.get(choice).getArmies() < 2)
        {
            choice = r.nextInt(validChoices.size());
        }
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
    public boolean attackAgain()
    {
        for (Territory t : getTerritories())
        {
            if (t.getArmies() > 1) return true;
        }
        return false;
    }

    @Override
    public boolean willFortify()
    {
        return false;
    }

    @Override
    public int attackDice()
    {
        return 1;
    }

    @Override
    public int defenseDice(int atkPID, String defStr, int atkDice)
    {
        return 1;
    }
}
