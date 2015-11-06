package game;

import java.util.Arrays;

public abstract class Player {

	//MEMBER VARIABLES
	private int playerID;
	private int totalArmies;
	private int totalTerritories;

	private int occupiedNAmerica;
	private int occupiedSAmerica;
	private int occupiedAsia;
	private int occupiedAutstralia;
	private int occupiedAfrica;
	private int occupiedEurope;
	
	private Deck cards;
	private Dice dice;

	//CONSTRUCTOR
	public Player(int pid, int initArmies) {
		playerID = pid;
		totalArmies = initArmies;
		occupiedNAmerica = 0;
		occupiedSAmerica = 0;
		occupiedAsia = 0;
		occupiedAutstralia = 0;
		occupiedAfrica = 0;
		occupiedEurope = 0;
		dice = new Dice();
	}

	//PUBLIC METHODS

	//getters
	public int getArmies() {
		return totalArmies;
	}
	
	public int getPID() {
		return playerID;
	}
	
	public int getTerritories() {
		return totalTerritories;
	}
	
	//setters
	public void territoryObtained() {
		totalTerritories++;
	}
	
	public void loseAnArmy() {
		totalArmies--;
	}

	//controller methods
	public void claim() {

	}
	public void placeRemaining() {
		// TODO Place remaining armies after board has been claimed
	}

	public int[] rollDice(int numDiceRolled) {
		return dice.roll(numDiceRolled);
	}
	
	public boolean deploy(int cardSetVal) {
		boolean exchanged;
		int armies = fromTerritories() + fromContinents();
		if(fromCards(cardSetVal) == 0)
			exchanged = false;
		else
			exchanged = true;
		placeDeployedArmies(armies);
		totalArmies += armies;
		return exchanged;
	}
	
	public void drawCard() {
		// TODO we'll have to work on this one
	}

	public void attack() {
		boolean attacking = true;
		while(attacking) {
			attacking = chooseContinueAttacking();
			Territory myTerr = chooseAttacker();
			Territory oppTerr = chooseTarget(); //oppTerr means opponent's territory
			int dice = chooseDice(myTerr, oppTerr);
			if(executeAttack(myTerr, oppTerr, dice)) {
				invade(myTerr, oppTerr);
				drawCard();
			}
		}
	}

	public void fortify() {
		// TODO will have two implementations
	}
	
	public void placeCardArmies() {
		// TODO probably needs a parameter
	}

	public int defend(int oppDice) {
		// TODO Auto-generated method stub
		return 1 | 2;
	}

	//PRIVATE METHODS
	
	//deploy methods
	private int fromTerritories() {
		return Math.max(totalTerritories % 3, 3); //3 is the min armies you get to deploy regardless of the number of territories controlled
	}

	private int fromContinents() {
		int bonus = 0;
		if(occupiedNAmerica == 9)
			bonus += 5;
		if(occupiedSAmerica == 4)
			bonus += 2;
		if(occupiedAsia == 12)
			bonus += 7;
		if(occupiedAutstralia == 4)
			bonus += 2;
		if(occupiedAfrica == 6)
			bonus += 3;
		if(occupiedEurope == 7)
			bonus += 5;
		return bonus;
	}

	private int fromCards(int cardSetVal) {
		boolean exchanged = decideCardExchange();
		if(exchanged)
			return cardSetVal;
		else
			return 0;
	}

	private boolean decideCardExchange() {
		// TODO Auto-generated method stub
		if(cards.size() >= 5) {
			//implement logic or call a method to do the actual exchange
			return true;
		}
		return false;
	}

	//attacking methods
	private boolean chooseContinueAttacking() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private Territory chooseAttacker() {
		// TODO Auto-generated method stub
		return null;
	}

	private Territory chooseTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	private int chooseDice(Territory mine, Territory opp) {
		// TODO Auto-generated method stub
		int numDice = 0;
		if(mine.getArmies() == 2) //TODO you'll use code like this elsewhere
			numDice = 1;
		opp.defend(numDice);
		return numDice;
	}

	private boolean executeAttack(Territory mine, Territory opp, int myDice) {
		// TODO Auto-generated method stub
		int[] myRoll = rollDice(myDice);
		int[] oppRoll = rollDice(opp.defDice());
		Arrays.sort(myRoll);
		Arrays.sort(oppRoll);
		int comparisons = Math.min(myRoll.length, oppRoll.length);
		for(int ii = comparisons; ii >= 0; ii--) {
			if(myRoll[ii] > oppRoll[ii]) //attacker won the throw
				opp.lose();
			else //defender won the throw
				mine.lose();
		}
		if(opp.getArmies() == 0) //return whether enemy territory's armies were all destroyed
			return true;
		else
			return false;
	}
	
	private void invade(Territory mine, Territory opp) {
		// TODO Auto-generated method stub
		if(opp.getOccupier().totalTerritories == 0) { // TODO maybe move this to a territory method??
			//Take the loser's cards
			if(cards.size() >= 6) {
    			while(cards.size() >= 4) {
    				decideCardExchange();
    				placeCardArmies();
    			}
			}
		}
	}
	
	//deploy methods
	private void placeDeployedArmies(int armies) {
		
	}

}
