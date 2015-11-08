package model;

import java.util.List;

public class Territory {

	// MEMBER VARIABLES
	private int armies;
	private Player occupier;
	private int defDice;
	private Continent cont;
	private Color color;
	private Countries name;
	private List<Territory> adj;
	private int numAdj;

	// CONSTRUCTOR
	public Territory(Continent cont, Countries name, List<Territory> adj) {
		this.cont = cont;
		this.name = name;
		this.adj = adj;
		numAdj = adj.size();
	}

	// PUBLIC METHODS

	// getters
	public int getArmies() {
		return armies;
	}

	public Player getOccupier() {
		return occupier;
	}

	public int defDice() {
		return defDice;
	}

	// setters
	public void setArmies(int a) {
		armies = a;
	}


	// player methods
	public void defend(int oppDice) {
		defDice = occupier.defend(oppDice);
	}

	public void lose() {
		armies--;
		occupier.loseAnArmy();
	}

}
