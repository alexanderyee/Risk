package game;

import java.util.ArrayList;

public class Territory {

	//MEMBER VARIABLES
	private int armies;
	private Player occupier;
	private int defDice;
	private Continent cont;
	private Color color;
	private String name;
	private Territory[] adj;
	private int numAdj;
	
	//CONSTRUCTOR
	public Territory(Continent con, String terrName) {
		cont = con;
		name = terrName;
	}
	
	//PUBLIC METHODS
	
	//getters
	public int getArmies() {
		return armies;
	}

	public Player getOccupier() {
		return occupier;
	}
	
	public int defDice() {
		return defDice;
	}
	
	//setters
	public void setArmies(int a) {
		armies = a;
	}	
	public void setAdj(Territory[] na1Adj) {
		adj = na1Adj;
		numAdj = adj.length;
	}
	
	//player methods
	public void defend(int oppDice) {
		defDice = occupier.defend(oppDice);
	}
	
	public void lose() {
		armies--;
		occupier.loseAnArmy();
	}
	
}
