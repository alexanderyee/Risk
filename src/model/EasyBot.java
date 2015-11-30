

package model;

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

    /*
     * Fortify method for the easy bot, picks the first territory it can and
     * moves an army to the first space it can
     */
    @Override
    public void fortify()
    {
        // implement later when human's fortify is done.
        Territory fortifyFrom = null;

        for (Territory i : this.getTerritories())
        {
            if (i.getArmies() > 1 && i.getAdjacentTerritories().size() >= 1)
            {
                i.getAdjacentTerritories().get(0).addArmies(i.removeArmies(1));
            }
        }

    }

    @Override
    public void placeDeployedArmies(int armies)
    {
        int choice;
        Territory terr;

        for (int i = 0; i < armies; i++)
        {
            terr = territories.get(r.nextInt(2));
            terr.addArmies(1);
            loseAnArmy();
        }
        System.out.println("EasyBot " + this.pid + "has deployed armies");
        
    }
    
    
    
    /*
     * A consolidation method, plays the turn for the bot
     * WIP, attack needs to be moved to Territory
     */
    public void playTurn(int armiesToPlace)
    {
        //Place the current number of armies
        this.placeDeployedArmies(armiesToPlace);
        for(Territory i : this.territories)
        {
            for(Territory j : i.getAdjacentTerritories())
            {
                if(j.getOccupier().getPID() != this.getPID())
                {
                    //i.attack(j)
                }
            }
        }
        
    }
    
}
