package model;

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
            int response= s.nextInt();
            Territory fortifyTo = this.getTerritories().get(response-1);
            System.out.println(fortifyTo.toString());
            List<Territory> possible = this.getTerritories();
            System.out.println(
                    "Please choose a territory to fortify troops from.");
             int counter=1;
            for (Territory t : possible)
            {
                if(t.getOccupier().equals(this)){
                System.out.println(counter+")"+t.toString() +"-----"+ t.getArmies()+"\n");
                }
                counter++;
            }
            Territory fortifyFrom = possible.get(s.nextInt()-1);
            System.out.println("Please choose a number of armies to move from "
                    + fortifyFrom.toString() + " to " + fortifyTo.toString()
                    + " that is less than " + (fortifyFrom.getArmies() - 1));
            int move = s.nextInt();
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
            terr = territories.get(choice);
            terr.addArmies(1);
            loseAnArmy();
        }
    }
}
