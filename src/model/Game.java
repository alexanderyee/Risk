package model;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;

import java.util.Random;

import java.util.Scanner;

public class Game
{

    // MEMBER VARIABLES
    // logic variables
    private int turnsPlayed;
    private int roundsPlayed;
    private boolean gameOver;
    private int cardSetValue; // we can delete this now, moving it to Map class
    // game piece variables
    private Map map;
    // player variables
    private int numPlayers;

    private ArrayList<Player> players;
    private int currentPID;

    // CONSTRUCTOR
    public Game(int numBots, int numHumans)
    {
        initializeMemberVariables(numBots, numHumans);
        initializePlayers(numBots, numHumans);
        setupGame();
    }

    // CONSTRUCTOR HELPER METHODS
    private void initializeMemberVariables(int numBots, int numHumans)
    {
        turnsPlayed = 0;
        roundsPlayed = 0;
        gameOver = false;
        cardSetValue = 4;
        map = new Map();
        this.numPlayers = numBots + numHumans;
        players = new ArrayList<Player>();
    }

    private void initializePlayers(int numBots, int numHumans)
    {
        int initArmies = 0;
        if (numPlayers == 3)
            initArmies = 35;
        else if (numPlayers == 4)
            initArmies = 30;
        else if (numPlayers == 5)
            initArmies = 25;
        else if(numPlayers == 6) 
            initArmies = 20;
        for (int ii = 0; ii < numBots; ii++)
        { // instantiate bots
           
          
            Player p = new EasyBot(ii, initArmies, map);
            players.add(p);
        }
        for (int jj = numBots; jj < numPlayers; jj++)
        { // instantiate
          // humans
           
            Player p = new Human(jj, initArmies, map);

            players.add(p);
        }
    }

    private void setupGame()
    {
        rollToGoFirst();
        claimTerritories();
        // beginGame2(); //this is the new re-structured one
        // beginGame(); //we don't need this any more
    }

    private void rollToGoFirst()
    {
        Random r = new Random();
        currentPID = r.nextInt(players.size());
    }

    private void claimTerritories()
    {
        System.out.println("Randomly claiming territories.");
        for (int ii = 0; ii < 42; ii++)
        {
         
            if (currentPID >= players.size()) currentPID = 0;
            map.giveRandomTerritory(players.get(currentPID));
          
            currentPID++;
        }

    }

    private void beginGame()
    {
        Player curr;
        while (!gameOver)
        {
            curr = players.get(currentPID);
            int bonus = curr.deploy();
            bonus += map.exchangeCards(curr);
            curr.placeDeployedArmies(bonus);
            attack();
            if (curr.getTotalTerritories() == 42)
                gameOver = true;
            else
            {
                curr.fortify(); // TODO (AI-01): You'll have to change this to a
                                // dynamic value
                System.out.println("Randomly claiming territories.");
                for (int ii = 0; ii < 42; ii++)
                {
                    if (currentPID >= players.size()) currentPID = 0;
                    map.giveRandomTerritory(players.get(currentPID));
                    currentPID++;
                }
            }
        }
    }

    // PRIVATE METHODS
    public String getTerritories(int k)
    {
        return players.get(k - 1).getTerroritories();
    }

    public void placeArmyInPlayerTerritory(int p, int terrNumber)
    {
        players.get(p - 1).addArmy(terrNumber);
    }

    public void attack()
    {

        // Asks if the player wants to attack or no

        Scanner k = new Scanner(System.in);
        System.out.printf("Player %d, would you like to attack? \n",
                currentPID);
        int y = k.nextInt();
        if (y == 1)
        {
            Player currentPlayer = players.get(currentPID - 1);
            // Determines the current player object
            System.out.println(currentPlayer.getTerritories());
            try
            {

                // Prints out a list of the number of territories this player
                // has and the territories that are adjacent to each other
                boolean attackUnresolved = true;
                while (attackUnresolved)
                {

                    ArrayList<Territory> tList = currentPlayer.getTerritories();
                    ArrayList<Territory> adjList = null;

                    int k1 = 0;
                    for (int i = 0; i < currentPlayer.getTotalTerritories(); i++)
                    {
                    //  System.out.printf(i);
                        System.out.printf("(%d) Territory %s has %d armies and can attack: \n",k1, tList.get(i),tList.get(i).getArmies());
                        k1++;

                        adjList = (ArrayList<Territory>) tList.get(i).getAdjacentTerritories();

                        for (int j = 0; j < adjList.size(); j++)
                        {
                            if (!adjList.get(j).getOccupier().equals(currentPlayer) && adjList.get(j).getArmies()>1 )
                            {
                                System.out.printf("\t (%d) %s---%d armies", j,
                                        adjList.get(j).toString(),adjList.get(j).getArmies());
                            }
                       
                        }
                        System.out.printf("\n");
                    }
                  
                    System.out.printf(

                            "Enter the number of the territory would like to attack from:");

                    System.out.printf(

                            "Enter the number of the territory would like to attack with:");

                    int attackingTerritoryNumber = k.nextInt();

                    Territory attackingTerritory = currentPlayer
                            .getTerritories().get(attackingTerritoryNumber);

                    System.out.printf(

                            "Enter the number of the territory that you would like to attack: ");

                    int defendingTerritoryNumber = k.nextInt();
                    ////
                    Territory defendingTerritory = currentPlayer
                            .getTerritoryArray().get(attackingTerritoryNumber)

                            .getAdjacentTerritories()
                            .get(defendingTerritoryNumber);

                  
                    
                    resolveAttack(attackingTerritory, defendingTerritory);

                   
                    System.out.println("Attarckers terrys \n"+this.getTerritories(currentPID));
                  Player defendingPlayer =defendingTerritory.getOccupier(); 
                    System.out.println("defenders terrys \n"+ defendingPlayer.getTerroritories());
                    System.out.printf(
                            "Player %d, would you like to continue attacking? \n",
                            currentPID);
                    int response = k.nextInt();
                    if (response == 0)
                    {

                        attackUnresolved = false;
                        System.out.println("would you like to fortify? ");
                        int response1 = k.nextInt();
                        if(response1==1){
                            currentPlayer.fortify();
                        }
                        
                    }

                }
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
        }
        System.out.println("Method ended");

        return;
    }

    ///////////////////// Resolved attack

    public boolean resolveAttack(Territory attacking, Territory defending)
    {
        /*
         * Needs error checking to make sure that there are at least 2 armies in
         * the attacking territory
         */

        Scanner k = new Scanner(System.in);

        Dice dice = new Dice();

        System.out.printf(
                "Player %d, decide how many dice you would like to roll?",

                attacking.getOccupier().getPID() + 1);

        int attackerRollNumber = k.nextInt();
        if(attackerRollNumber>attacking.getArmies()-1){
         System.out.printf(  "you can roll at most %d please try again \n\n",Math.min(attacking.getArmies()-1, 3));

                   
        }
         ArrayList<Integer> attackersRolls;
        // attackersRolls.addAll(dice.roll2(attackerRollNumber));

        System.out.printf(
                "Player %d, pick how many dice you would like to roll?(Player %d who is attacking %s has chosen to use %d dice)",

                defending.getOccupier().getPID() + 1,
                attacking.getOccupier().getPID() + 1, defending.toString(),

                attackerRollNumber);
        

        int defenderRollNumber = k.nextInt();

        ArrayList<Integer> defendersRolls;
      int min=Math.max(attackerRollNumber, defenderRollNumber);
        attackersRolls = dice.roll2(attackerRollNumber);
     
        defendersRolls = dice.roll2(defenderRollNumber);
       

        Collections.sort(attackersRolls,Collections.reverseOrder());
        Collections.sort(defendersRolls,Collections.reverseOrder());
         
        for (int i = 0; i < min; i++)
        {
            //System.out.printf("attacker %d        defender %d \n",attackersRolls.get(i),defendersRolls.get(i));
            
             if (attackersRolls.get(i) <= defendersRolls.get(i))
                   
            {
               System.out.printf(" attacker rolled %d \ndefenders roll %d \n",attackersRolls.get(i),defendersRolls.get(i));
               
                attacking.removeArmies(1);
            }
            else if (attackersRolls.get(i) > defendersRolls.get(i) )
            {
                System.out.printf(" attacker rolled %d \ndefenders roll %d \n",attackersRolls.get(i),defendersRolls.get(i));
                defending.removeArmies(1);
            }
        }

        // if the defending player loses, give the territory to the attacking
        // player
        // and return true, the attack has been resolved
        if (defending.getArmies() == 0)
        {
            defending.changeOccupier(attacking.getOccupier());
            return true;
        }
        else if (attacking.getArmies() == 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
