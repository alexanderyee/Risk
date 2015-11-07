package model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Deck {

	// Declaring all the cards

	private int size;

	private static Card a1 = new Card(CardType.INFANTRY, Countries.ALASKA);
	private static Card a2 = new Card(CardType.ARTILLERY, Countries.NW_TERRITORY);
	private static Card a3 = new Card(CardType.CALVARY, Countries.GREENLAND);
	private static Card a4 = new Card(CardType.INFANTRY, Countries.ALBERTA);
	private static Card a5 = new Card(CardType.CALVARY, Countries.ONTARIO);
	private static Card a6 = new Card(CardType.ARTILLERY, Countries.QUEBEC);
	private static Card a7 = new Card(CardType.INFANTRY, Countries.WESTERN_US);
	private static Card a8 = new Card(CardType.ARTILLERY, Countries.EASTERN_US);
	private static Card a9 = new Card(CardType.CALVARY, Countries.CENT_AMERICA);

	private static Card a10 = new Card(CardType.ARTILLERY, Countries.VENENZUELA);
	private static Card a11 = new Card(CardType.CALVARY, Countries.PERU);
	private static Card a12 = new Card(CardType.ARTILLERY, Countries.BRAZIL);
	private static Card a13 = new Card(CardType.INFANTRY, Countries.ARGENTINA);

	private static Card a14 = new Card(CardType.INFANTRY, Countries.ICELAND);
	private static Card a15 = new Card(CardType.ARTILLERY, Countries.SCANDINAVIA);
	private static Card a16 = new Card(CardType.ARTILLERY, Countries.UKRAINE);
	private static Card a17 = new Card(CardType.CALVARY, Countries.GREAT_BRITAIN);
	private static Card a18 = new Card(CardType.CALVARY, Countries.N_EUROPE);
	private static Card a19 = new Card(CardType.INFANTRY, Countries.W_EUROPE);
	private static Card a20 = new Card(CardType.CALVARY, Countries.S_EUROPE);

	private static Card a21 = new Card(CardType.INFANTRY, Countries.N_AFRICA);
	private static Card a22 = new Card(CardType.INFANTRY, Countries.EGYPT);
	private static Card a23 = new Card(CardType.ARTILLERY, Countries.E_AFRICA);
	private static Card a24 = new Card(CardType.CALVARY, Countries.CONGO);
	private static Card a25 = new Card(CardType.ARTILLERY, Countries.S_AFRICA);
	private static Card a26 = new Card(CardType.INFANTRY, Countries.MADAGASCAR);
	private static Card a27 = new Card(CardType.ARTILLERY, Countries.SIBERIA);
	private static Card a28 = new Card(CardType.CALVARY, Countries.YAKUTSK);
	private static Card a29 = new Card(CardType.CALVARY, Countries.KAMCHATKA);
	private static Card a30 = new Card(CardType.CALVARY, Countries.URAL);
	private static Card a31 = new Card(CardType.INFANTRY, Countries.IRKUTSK);
	private static Card a32 = new Card(CardType.INFANTRY, Countries.AFGHANISTAN);
	private static Card a33 = new Card(CardType.ARTILLERY, Countries.MONGOLIA);
	private static Card a34 = new Card(CardType.INFANTRY, Countries.JAPAN);
	private static Card a35 = new Card(CardType.CALVARY, Countries.CHINA);
	private static Card a36 = new Card(CardType.ARTILLERY, Countries.MIDDLE_EAST);
	private static Card a37 = new Card(CardType.INFANTRY, Countries.INDIA);
	private static Card a38 = new Card(CardType.ARTILLERY, Countries.SIAM);

	private static Card a39 = new Card(CardType.CALVARY, Countries.INDONESIA);
	private static Card a40 = new Card(CardType.CALVARY, Countries.NEW_GUINEA);
	private static Card a41 = new Card(CardType.ARTILLERY, Countries.W_AUSTRALIA);
	private static Card a42 = new Card(CardType.INFANTRY, Countries.E_AUSTRALIA);

	// Queue which will contain all of the cards
	private Queue<Card> CardQ = new LinkedList<Card>();

	// Constructor which will initialize the queue with all the cards in it
	public Deck() {
     initializeDeck(); //Adds all the cards to the queue
	 shuffleDeck();     //suffles the queue
	
	}

	public int size() {    
		return size;
	}
	public Card dealCard(){      
		return CardQ.remove();
	}
	public void returnCardToDeck(Card card){
		CardQ.add(card);
	}
    public void shuffleDeck(){
    	Collections.shuffle((List<?>) CardQ);
    }
	private void initializeDeck() {
		CardQ.add(a1);
		CardQ.add(a2);
		CardQ.add(a3);
		CardQ.add(a4);
		CardQ.add(a5);
		CardQ.add(a6);
		CardQ.add(a7);
		CardQ.add(a8);
		CardQ.add(a9);
		CardQ.add(a10);
		CardQ.add(a11);
		CardQ.add(a12);
		CardQ.add(a13);
		CardQ.add(a14);
		CardQ.add(a15);
		CardQ.add(a16);
		CardQ.add(a17);
		CardQ.add(a18);
		CardQ.add(a19);
		CardQ.add(a20);
		CardQ.add(a21);
		CardQ.add(a22);
		CardQ.add(a23);
		CardQ.add(a24);
		CardQ.add(a25);
		CardQ.add(a26);
		CardQ.add(a27);
		CardQ.add(a28);
		CardQ.add(a29);
		CardQ.add(a30);
		CardQ.add(a31);
		CardQ.add(a32);
		CardQ.add(a33);
		CardQ.add(a34);
		CardQ.add(a35);
		CardQ.add(a36);
		CardQ.add(a37);
		CardQ.add(a38);
		CardQ.add(a39);
		CardQ.add(a40);
		CardQ.add(a41);
		CardQ.add(a42);
	}
	// getters

}
