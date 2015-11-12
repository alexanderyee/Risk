package model;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;

import java.util.Random;

import java.util.Scanner;

public class Game {

	//MEMBER VARIABLES
	//logic variables
	private int turnsPlayed;
	private int roundsPlayed;
	private boolean gameOver;
	private int cardSetValue; //we can delete this now, moving it to Map class
	//game piece variables
	private Map map;
	//player variables
	private int numPlayers;
	
	private ArrayList<Player> players;
	private int currentPID;
	
	//CONSTRUCTOR
	public Game(int numBots, int numHumans) {
		initializeMemberVariables(numBots, numHumans);
		initializePlayers(numBots, numHumans);
		setupGame();
	}

	// CONSTRUCTOR HELPER METHODS
	private void initializeMemberVariables(int numBots, int numHumans) {
		turnsPlayed = 0;
		roundsPlayed = 0;
		gameOver = false;
		cardSetValue = 4;
		map = new Map();
		this.numPlayers = numBots + numHumans;
		players = new ArrayList<Player>();
	}
	
	private void initializePlayers(int numBots, int numHumans) {
		int initArmies = 0;
		if(numPlayers == 3)
			initArmies = 35;
		else if(numPlayers == 4)
			initArmies = 30;
		else if(numPlayers == 5)
			initArmies = 25;
		else if(numPlayers == 6)
			initArmies = 20;
		for(int ii = 0; ii < numBots; ii++) { //instantiate bots
			Player p = new Bot(ii, initArmies, map);
			players.add(p);
		}
		for(int jj = numBots; jj < (numPlayers-numBots); jj++) { //instantiate humans
			Player p = new Human(jj, initArmies, map);

			players.add(p);
		}
	}
	
	private void setupGame() {
		rollToGoFirst();
		claimTerritories();
		//beginGame2(); //this is the new re-structured one
		//beginGame(); //we don't need this any more
	}

	private void rollToGoFirst() {
		Random r = new Random();
		currentPID = r.nextInt(players.size());
	}

	private void claimTerritories() {
		System.out.println("Randomly claiming territories.");
		for (int ii = 0; ii < 42; ii++) {
			if (currentPID >= players.size())
				currentPID = 0;
			map.giveRandomTerritory(players.get(currentPID));
			currentPID++;
		}

	}
	
	private void beginGame()
	{
		Player curr;
		while(!gameOver) {
			curr = players.get(currentPID);
			int bonus = curr.deploy();
			bonus += map.exchangeCards(curr);
			curr.placeDeployedArmies(bonus);
			attack();
			if(curr.getTotalTerritories() == 42)
				gameOver = true;
			else {
				curr.fortify(); //TODO (AI-01): You'll have to change this to a dynamic value
        		System.out.println("Randomly claiming territories.");
        		for (int ii = 0; ii < 42; ii++) {
        			if (currentPID >= players.size())
        				currentPID = 0;
        			map.giveRandomTerritory(players.get(currentPID));
        			currentPID++;
        		}
			}
		}
	}
	
	// PRIVATE METHODS
	public String getTerritories(int k) {
		return players.get(k - 1).getTerroritories();
	}
    public void placeArmyInPlayerTerritory(int p, int terrNumber){
	   players.get(p-1).addArmy(terrNumber);
    }
    public void attack()
    {

        // Asks if the player wants to attack or no
      
        Scanner k = new Scanner(System.in);
        System.out.printf("Player %d, would you like to attack? \n", currentPID);
        int y = k.nextInt();
        if (y == 1)
        {
          Player currentPlayer= players.get(currentPID-1);
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

                    for (int i = 0; i < currentPlayer
                            .getTotalTerritories(); i++)
                    {
                        System.out.printf("(%d) Territory %s can attack: ", i,
                                tList.get(i));

                        adjList = (ArrayList<Territory>) tList.get(i)
                                .getAdjacentTerritories();

                        for (int j = 0; j < adjList.size(); j++)
                        {
                            System.out.printf("\t (%d) %s", j,
                                    adjList.get(j).toString());
                        }
                    }

                    System.out.printf(
                            "Enter the number of the territory would like to attack with:");

                    int attackingTerritoryNumber = k.nextInt();

                    Territory attackingTerritory = currentPlayer
                            .getTerritories().get(attackingTerritoryNumber);

                    System.out.printf(
                            "Enter the number of the territory that you would like to attack(0-%d):",
                            currentPlayer.getTerritories().size());

                    int defendingTerritoryNumber = k.nextInt();

                    Territory defendingTerritory = currentPlayer
                            .getTerritories().get(attackingTerritoryNumber)
                            .getAdjacentTerritories()
                            .get(defendingTerritoryNumber);

                    //Starts the loop to allow the attacker to continue attacking if they still have armies to attack with
                    //or they capture the territory
                    boolean attackResolved = false;
                    while(!(attackResolved))
                    {
                        attackResolved = resolveAttack(attackingTerritory, defendingTerritory);
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
    public boolean resolveAttack(Territory attacking, Territory defending)
    {
        /*
         * Needs error checking to make sure that there are at least 2 armies in the attacking territory
         */

        Scanner k = new Scanner(System.in);
        
        Dice dice = new Dice();
                    
        System.out.printf("Player %d, decide how many dice you would like to roll?", attacking.getOccupier().getPID());
        int attackerRollNumber = k.nextInt();

        
        
        ArrayList<Integer> attackersRolls = new ArrayList<Integer>();
        attackersRolls.addAll(dice.roll2(attackerRollNumber));
        
        
        
        System.out.printf("Player %d, pick how many dice you would like to roll?(Player %d who is attacking %s has chosen to use %d dice)", defending.getOccupier().getPID()
                , attacking.getOccupier().getPID(), defending.toString(), attackerRollNumber);
        
        int defenderRollNumber = k.nextInt();
        
        ArrayList<Integer> defendersRolls = new ArrayList<Integer>();
        
        
        //Sort and compare the rolls
        attackersRolls = dice.roll2(attackerRollNumber);
        
        defendersRolls = dice.roll2(defenderRollNumber);
        
        Collections.sort(attackersRolls);
        Collections.sort(defendersRolls);
        
        for(int i = 0; i < 3; i++)
        {
            if(attackersRolls.get(i) == (defendersRolls.get(i)) && attackersRolls.get(i).equals(0))
            {
                //Do nothing because both players didn't want to roll this many
            }
            else if(attackersRolls.get(i) == (defendersRolls.get(i)) || attackersRolls.get(i) < defendersRolls.get(i))
            {
                attacking.removeArmies(1);
            }
            else if(attackersRolls.get(i) < defendersRolls.get(i))
            {
                defending.removeArmies(1);
            }
        }
        
        //if the defending player loses, give the territory to the attacking player
        //and return true, the attack has been resolved
        if(defending.getArmies() == 0)
        {
            defending.changeOccupier(attacking.getOccupier());
            return true;
        }
        else if(attacking.getArmies() == 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
}