package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Map {

	//MEMBER VARIABLES
	private ArrayList<Territory> territories;
	private Deck deck;

	//CONSTRUCTOR
	public Map() {
		RiskResources r = new RiskResources();
		territories = r.getTerritories(); //returns array with all territories
		deck = new Deck();
		
	}
	
	//PUBLIC METHODS
	//getters
	public Card drawCard() {
		return deck.dealCard();
	}
	public void assignInitialTerritories(){// will choode the 
		
		
	}
	public Territory getTerritory(String name) {
		for(Territory t : territories) {
			if(t.toString().equals(name)) {
				return t;
			}
		}
		System.out.println("Territory not found by Map's method getTerritory.");
		return null;
	}
	
	public Territory getCountry(Countries c) {
		for(Territory t : territories) {
			if(c.toString().equals(t.toString()))
				return t;
		}
		System.out.println("Error, territory not found by Map's getCountry method.");
		return null;
	}
	
	public List<Territory> getConnectedTerritories(Territory t) {
		List<Territory> all = t.getAdjacentTerritories();
		// TODO gotta fill this in with actual logic...
		return all;
	}
	
	public String listUnclaimed() {
		String list = "";
		for(Territory t : territories) {
			if(t.getOccupier() == null) {
				list += t.toString() + "\n";
			}
		}
		return list;
	}
	public List<Territory> getUnclaimedTerritories(){
		List<Territory> result = new ArrayList<Territory>();
		for (Territory t: territories){
			if (t.getOccupier() == null)
				result.add(t);
		}
		return result;
	}
	
	public String listPlayerTerritories(Player p) {
		String list = "";
		for(Territory t : territories) {
			if(t.getOccupier() == p) {
				list += t.toString() + "\n";
			}
		}
		return list;
	}
	
	//other
	public void returnCard(Card c) {
		deck.returnCardToDeck(c);
	}

	public void giveRandomTerritory(Player p) { // used when assigning territories at the start, NOTE: territories is shuffled. 
		Collections.shuffle(territories);
		while (territories.get(0).getOccupier() != null)
			Collections.shuffle(territories);
		p.territoryObtained(territories.get(0));
		p.loseAnArmy();
		territories.get(0).changeOccupier(p);
		territories.get(0).setArmies(1);
	
	}

}