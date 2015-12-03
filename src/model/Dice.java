package model;

import java.util.ArrayList;
import java.util.Random;

public class Dice
{

    // MEMBER VARIABLES
    private Random luck;

    // CONSTRUCTOR
    public Dice()
    {
        luck = new Random();
    }

    public Dice(int seed)
    {
        luck = new Random(seed);
    }

    // PUBLIC METHODS
    public int roll()
    {
        return luck.nextInt(6) + 1; // nextInt(6) returns 0 <= retVal <= 5
    }

    public int[] roll(int numDiceRolled)
    {
        int[] result = new int[numDiceRolled];
        for (int ii = 0; ii < numDiceRolled; ii++)
            result[ii] = roll();
        return result;
    }

    public ArrayList<Integer> roll2(int numDiceRolled)
    {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int ii = 0; ii < numDiceRolled; ii++)
            result.add(roll());
        return result;
    }

}
