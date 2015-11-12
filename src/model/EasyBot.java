/*
 * If i had a clue how to do this one, I would have
 */

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

    @Override
    public void fortify()
    {
        // implement later when human's fortify is done.
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
}
