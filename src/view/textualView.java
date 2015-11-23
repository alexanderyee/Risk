package view;

import java.util.Scanner;

import model.Game;

public class textualView
{

    public static void main(String[] args)
    {
        /*
         * System.out.println("Enter number of humans."); int humans =
         * s.nextInt(); System.out.println("Enter number of bots."); int bots =
         * s.nextInt();
         */
        Game g = new Game(0, 6);

        System.out.println("Player 1 territories \n" + g.getTerritories(0));
        System.out.println("Player 2 territories \n" + g.getTerritories(1));
        System.out.println("Player 3 territories \n" + g.getTerritories(2));
        System.out.println("Player 4 territories \n" + g.getTerritories(3));
        System.out.println("Player 5 territories \n" + g.getTerritories(4));
        System.out.println("Player 6 territories \n" + g.getTerritories(5));

        for (int i = 1; i <= 7; i++)
        {
            g.placeArmyInPlayerTerritory(1, i);

        }
        for (int i = 1; i <= 7; i++)
        {
            g.placeArmyInPlayerTerritory(1, i);

        }
        System.out.println("Player 1 territories \n" + g.getTerritories(0));

        for (int i = 1; i <= 7; i++)
        {
            g.placeArmyInPlayerTerritory(2, i);

        }
        for (int i = 1; i <= 7; i++)
        {
            g.placeArmyInPlayerTerritory(2, i);

        }
        System.out.println("Player 2 territories \n" + g.getTerritories(1));

        for (int i = 1; i <= 7; i++)
        {
            g.placeArmyInPlayerTerritory(3, i);

        }
        for (int i = 1; i <= 7; i++)
        {
            g.placeArmyInPlayerTerritory(3, i);

        }
        System.out.println("Player 3 territories \n" + g.getTerritories(2));

        for (int i = 1; i <= 7; i++)
        {
            g.placeArmyInPlayerTerritory(3, i);

        }
        for (int i = 1; i <= 7; i++)
        {
            g.placeArmyInPlayerTerritory(3, i);

        }
        System.out.println("Player 3 territories \n" + g.getTerritories(2));
        for (int i = 1; i <= 7; i++)
        {
            g.placeArmyInPlayerTerritory(4, i);

        }
        for (int i = 1; i <= 7; i++)
        {
            g.placeArmyInPlayerTerritory(4, i);

        }
        System.out.println("Player 4 territories \n" + g.getTerritories(3));
        for (int i = 1; i <= 7; i++)
        {
            g.placeArmyInPlayerTerritory(5, i);

        }
        for (int i = 1; i <= 7; i++)
        {
            g.placeArmyInPlayerTerritory(5, i);

        }
        System.out.println("Player 5 territories \n" + g.getTerritories(4));
        for (int i = 1; i <= 7; i++)
        {
            g.placeArmyInPlayerTerritory(6, i);

        }
        for (int i = 1; i <= 7; i++)
        {
            g.placeArmyInPlayerTerritory(6, i);

        }
        System.out.println("Player 6 territories \n" + g.getTerritories(5));
        g.attack();

    }

}
