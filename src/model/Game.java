
package model;

import java.util.ArrayList;
import java.util.Random;

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
	



}



