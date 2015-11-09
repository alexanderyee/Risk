package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Board {

	//MEMBER VARIABLES
	private ArrayList<Territory> territories;
	private Deck deck;

	//CONSTRUCTOR
	public Board() {
		RiskResources r = new RiskResources();
		territories = r.getTerritories();
		deck = new Deck();
		
	}
	
	//PUBLIC METHODS
	//getters
	public Card drawCard() {
		return deck.dealCard();
	}
	
	public Territory getTerritory(String name) {
		for(Territory t : territories) {
			if(t.toString().equals(name)) {
				return t;
			}
		}
		System.out.println("Territory not found by Board's method getTerritory.");
		return null;
	}
	
	public Territory getCountry(Countries c) {
		for(Territory t : territories) {
			if(c.toString().equals(t.toString()))
				return t;
		}
		System.out.println("Error, territory not found by Board's getCountry method.");
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

}