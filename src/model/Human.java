package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Human extends Player
{

    public Human(int pid, int initArmies, Map b)
    {
        super(pid, initArmies, b);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void fortify()
    {
        boolean legitChoice = false;
        while (!legitChoice)
        {
            System.out.println(this.getTerroritories());
            System.out.println(
                    "Please choose a territory to fortify with troops.");
            Scanner s = new Scanner(System.in);
            int response = s.nextInt();

            Territory fortifyTo = this.getTerritories().get(response - 1);
            System.out.println(fortifyTo.toString());
            List<Territory> possible = this.getTerritories();
            System.out.println(
                    "Please choose a territory to fortify troops from.");
            int counter = 1;
            for (Territory t : possible)
            {
                if (t.getOccupier().equals(this))
                {
                    System.out.println(counter + ")" + t.toString() + "-----"
                            + t.getArmies() + "\n");
                }
                counter++;
            }
            Territory fortifyFrom = possible.get(s.nextInt() - 1);
            System.out.println("Please choose a number of armies to move from "
                    + fortifyFrom.toString() + " to " + fortifyTo.toString()
                    + " that is less than " + (fortifyFrom.getArmies() - 1));
            int move = s.nextInt();
            s.close();
            fortifyFrom.addArmies(move * -1); // TODO this is some sketchy code
            fortifyTo.addArmies(move);
        }
    }

    public void placeDeployedArmies(int armies)
    {
        Scanner s = new Scanner(System.in);
        int choice;
        Territory terr;
        for (int i = 0; i < armies; i++)
        {
            System.out.println(
                    "Enter the number of the territory you would like to deploy a single army to.");
            for (int j = 0; j < territories.size(); j++)
            {
                System.out.println(j + ": " + territories.get(j).toString());
            }
            System.out.println("-----------------------");
            choice = s.nextInt();
            s.close();
            terr = territories.get(choice);
            terr.addArmies(1);
            loseAnArmy();
        }
    }

    // By changing game methods to be scanner-less, bots can better return
    // choices
    public boolean willAttack()
    {
        System.out.println("Player " + (getPlayerID() + 1)
                + ", would you like to attack? 'y' or 'n'");
        Scanner s = new Scanner(System.in);
        String answer = s.next().toLowerCase();
        s.close();
        if (answer.equals("y"))
            return true;
        else if (answer.equals("n"))
            return false;
        else
            System.out.println("Error. Try again.");
        return willAttack();
    }

    public int attackFrom()
    {
        ArrayList<Territory> adjList = null;
        int terrNumber = 0;
        ArrayList<Integer> validChoices = new ArrayList<Integer>();
        ArrayList<Territory> tList = getTerritories();
        for (int i = 0; i < getTotalTerritories(); i++)
        {
            // System.out.printf(i);
            System.out.printf(
                    "(%d) Territory %s has %d armies and can attack: \n",
                    terrNumber, tList.get(i), tList.get(i).getArmies());
            validChoices.add(terrNumber);
            terrNumber++;

            adjList = (ArrayList<Territory>) tList.get(i)
                    .getAdjacentTerritories();

            for (int j = 0; j < adjList.size(); j++)
            { // if(not attacking yourself
                if (!(adjList.get(j).getOccupier()
                        .getPlayerID() == getPlayerID())
                        && adjList.get(j).getArmies() > 1)
                {
                    System.out.printf("\t (%d) %s---%d armies", j,
                            adjList.get(j).toString(),
                            adjList.get(j).getArmies());

                }

            }
            System.out.printf("\n");
        }

        Scanner s = new Scanner(System.in);
        int choice = -999;
        while (!validChoices.contains(choice)
                || tList.get(choice).getArmies() < 2)
        {
            System.out.printf(
                    "Enter the number of the territory would like to attack from:");
            System.out.println(
                    "Be sure to choose a territory with at least 2 armies.");
            choice = s.nextInt();
        }
        s.close();
        return choice;
    }

    public int attackAt(int atkTerrNum)
    {
        System.out.printf(
                "Enter the number of the enemy territory would like to attack:");
        Scanner s = new Scanner(System.in);
        int num = s.nextInt();
        s.close();
        return num;
    }

    public boolean attackAgain()
    {
        System.out.printf("Player %d, would you like to continue attacking? \n",
                (getPlayerID() + 1));
        Scanner s = new Scanner(System.in);
        String choice = s.next().toLowerCase();
        s.close();
        if (choice.equals("y"))
            return true;
        else if (choice.equals("n"))
            return false;
        else
        {
            System.out.println("Error. Please try again.");
            return attackAgain();
        }
    }

    public boolean willFortify()
    {
        System.out.println("Would you like to fortify? ");
        Scanner s = new Scanner(System.in);
        String choice = s.next().toLowerCase();
        s.close();
        if (choice.equals("y"))
            return true;
        else if (choice.equals("n"))
            return false;
        else
        {
            System.out.println("Error. Please try again.");
            return willFortify();
        }
    }

    public int attackDice()
    {
        System.out.printf(
                "Player %d, decide how many dice you would like to roll?",
                getPlayerID() + 1);
        Scanner s = new Scanner(System.in);
        int num = s.nextInt();
        s.close();
        return num;
    }

    public int defenseDice(int atkPID, String defStr, int atkDice)
    {
        System.out.printf(
                "Player %d, pick how many dice you would like to roll?",
                getPID() + 1);
        System.out.printf("(Player " + atkPID + " who is attacking " + defStr
                + " has chosen to use " + atkDice + " dice)");
        Scanner s = new Scanner(System.in);
        int num = s.nextInt();
        s.close();
        return num;
    }

}
