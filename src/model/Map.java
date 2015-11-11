package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class Map {

	//MEMBER VARIABLES
	private ArrayList<Territory> territories;
	private List<Territory> unclaimedTerritories;
	private Deck deck;
	private int cardSetValue; // this was added here by Ben, moved from Game class

	//CONSTRUCTOR
	public Map() {
		RiskResources r = new RiskResources();
		territories = r.getTerritories();
		deck = new Deck();
		unclaimedTerritories = getUnclaimedTerritories();
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
		if(unclaimedTerritories.size()==0){
			return;
		}
		
		Collections.shuffle(unclaimedTerritories);
	Territory temp= unclaimedTerritories.get(0);
	    p.territoryObtained(temp);
		p.loseAnArmy();
		temp.changeOccupier(p);
		temp.setArmies(1);
		unclaimedTerritories.remove(0);
		
	}
	
	//new method here
	public int exchangeCards(Player p)
	{
		ArrayList<Card> playersHand = p.cards2; //get the players cards
		int bonus = 0;
		boolean willExchange = false;
		Scanner s = new Scanner(System.in);		
		if(playersHand.size() >= 5) { //check if they HAVE TO exchange cards
			System.out.println("Because you have 5 or move cards, you must trade in this turn.");
			willExchange = true;
		}
		else if(playersHand.size() >= 3){ //check if they would like to
			System.out.println("Would you like to trade in a card set? 'y' or 'n'.");
			String answer = s.next();
			if(answer.toLowerCase().equals("y"))
				willExchange = true;
		}		
		if(willExchange) { //do the actual exchange
			//Still needs logic to check if they are trading in a valid set
			System.out.println("You must trade in cards this turn.");
			System.out.println("Enter the int of the cards to trade in, one at a time.");
			for(int i = 0; i < playersHand.size(); i ++) {
				Card curr = playersHand.get(i);
				System.out.println(i + ": " + curr.toString() + "\n");
			}
			int first = s.nextInt();
			Card curr = playersHand.remove(first);
			Territory onCard1 = getCountry(curr.getCountry());
			if(onCard1 != null) {
				onCard1.addArmies(2);
				p.gainArmies(2);
			}
			deck.returnCardToDeck(curr);
			int second = s.nextInt();
			curr = playersHand.remove(second);
			Territory onCard2 = getCountry(curr.getCountry());
			if(onCard2 != null) {
				onCard2.addArmies(2);
				p.gainArmies(2);
			}
			deck.returnCardToDeck(curr);
			int third = s.nextInt();
			curr = playersHand.remove(third);
			Territory onCard3 = getCountry(curr.getCountry());
			if(onCard3 != null) {
				onCard3.addArmies(2);
				p.gainArmies(2);
			}
			deck.returnCardToDeck(curr);
			bonus = cardSetValue;
			raiseCardSetValue();
		}
		return bonus;
	}
	
	private void raiseCardSetValue() {
		if(cardSetValue <= 10)
			cardSetValue += 2;
		else if(cardSetValue == 12)
			cardSetValue += 3;
		else
			cardSetValue += 5;
	}
	
}