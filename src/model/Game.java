package model;

import java.util.ArrayList;
import java.util.Collections;
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

	//CONSTRUCTOR HELPER METHODS
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
		beginGame2(); //this is the new re-structured one
		beginGame(); //we don't need this any more
	}

	private void rollToGoFirst() {
		Random r = new Random();
		currentPID = r.nextInt(players.size());
	}

	private void claimTerritories() {
		for(int ii = 0; ii < 42; ii++) {
			System.out.println(map.listUnclaimed());
			if(currentPID > players.size())
				currentPID = 0;
			String choice = players.get(currentPID).claim();
			Territory t = map.getTerritory(choice);
			giveClaimedTerritory(players.get(currentPID), t);
			currentPID++;
		}
		while(players.get(currentPID).getArmies() != 0) {
			if(currentPID >= players.size())
				currentPID = 0;
			System.out.println(map.listPlayerTerritories(players.get(currentPID)));
			String choice = players.get(currentPID).placeRemaining();
			players.get(currentPID).loseAnArmy();
			map.getTerritory(choice).addArmies(1);
			currentPID++;
		}
	}
	
	private void beginGame() {
		Player curr;
		while(!gameOver) {
			curr = players.get(currentPID);
			if(curr.deploy(cardSetValue)) //if the player turned in a set of cards, raise value of card sets
				raiseCardSetValue();
			curr.attack();
			if(curr.getTotalTerritories() == 42)
				gameOver = true;
			else {
				curr.fortify(); //TODO (AI-01): You'll have to change this to a dynamic value
				currentPID++;
			}
		}
	}
	
	private void beginGame2()
	{
		Player curr;
		while(!gameOver) {
			curr = players.get(currentPID);
			int bonus = curr.deploy2();
			bonus += map.exchangeCards(curr);
			curr.placeDeployedArmies2(bonus);
			curr.attack();
			if(curr.getTotalTerritories() == 42)
				gameOver = true;
			else {
				curr.fortify(); //TODO (AI-01): You'll have to change this to a dynamic value
				currentPID++;
			}
		}
	}
	
	//PRIVATE METHODS
	private void raiseCardSetValue() { //can delete this now
		if(cardSetValue <= 10)
			cardSetValue += 2;
		else if(cardSetValue == 12)
			cardSetValue += 3;
		else
			cardSetValue += 5;
	}
	
	private void giveClaimedTerritory(Player p, Territory t) {
	//	p.territoryObtained(t.getContinent());
		p.loseAnArmy();
		t.changeOccupier(p);
		t.setArmies(1);
	}
	
	public void attack()
    {

        // Asks if the player wants to attack or no
        System.out.printf("Player %d, would you like to attack?", currentPID);
        Scanner k = new Scanner(System.in);

        if (k.next() == "Y")
        {
            // Determines the current player object
            Player currentPlayer = null;
            for (Player i : players)
            {
                if (i.getPID() == currentPID)
                {
                    currentPlayer = i;
                }
            }

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
                }
            }
            catch(NullPointerException e)
            {
            	e.printStackTrace();
            }
        }
        else
        {
        	return;
        }
    }
    
    public boolean resolveAttack(Territory attacking, Territory defending)
    {
    	/*
    	 * Needs error checking to make sure that there are at least 2 armies in
    	 * the attacking terrioty
    	 */
    	
    	Scanner k = new Scanner(System.in);
    	
    	Dice dice = new Dice();
    	
    	System.out.printf(
    			"Player %d, decide how many dice you would like to roll?",
    			attacking.getOccupier().getPID());
    	int attackerRollNumber = k.nextInt();
    	
    	ArrayList<Integer> attackersRolls = new ArrayList<Integer>();
        attackersRolls.addAll(dice.roll2(attackerRollNumber));
        
        System.out.printf("Player %d, pick how many dice you would like to roll?(Player %d who is attacking %s has chosen to use %d dice)",
        		defending.getOccupier().getPID(),
                attacking.getOccupier().getPID(), defending.toString(), 
                attackerRollNumber);
        
        int defenderRollNumber = k.nextInt();
        
        ArrayList<Integer> defendersRolls = new ArrayList<Integer>();        
        
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