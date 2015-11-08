package model;

import java.util.List;

public class Territory {
// need to collaborate with player on attacking/defending
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
		this.color = cont.getColor();
		armies = 0;
		occupier = null;
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
	public void addArmy() {
		armies++;
	}
	public void removeArmy(){
		armies--;
	}
	public void changeOccupier(Player player){
		this.occupier = player;
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
