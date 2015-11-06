package model;

public class Deck {

	//MEMBER VARIABLES
	private int size;
	Card na1 = new Card(CardType.INFANTRY, Countries.ALASKA);
	Card na2 = new Card(CardType.ARTILLERY, Countries.NW_TERRITORY);
    Card na3 = new Card(CardType.CALVARY, Countries.GREENLAND);
	Card na4 = new Card(CardType.INFANTRY, Countries.ALBERTA);
	Card na5 = new Card(CardType.CALVARY, Countries.ONTARIO);
	Card na6 = new Card(CardType.ARTILLERY, Countries.QUEBEC);
	Card na7 = new Card(CardType.INFANTRY, Countries.WESTERN_US);
	Card na8 = new Card(CardType.ARTILLERY, Countries.EASTERN_US);
	Card na9 = new Card(CardType.CALVARY, Countries.CENT_AMERICA);
	
	Card sa1 = new Card(CardType.ARTILLERY,Countries.VENENZUELA);
	Card sa2 = new Card(CardType.CALVARY,Countries.PERU);
	Card sa3 = new Card(CardType.ARTILLERY,Countries.BRAZIL);
	Card sa4 = new Card(CardType.INFANTRY,Countries.ARGENTINA);
	
	Card eu1 = new Card(CardType.INFANTRY,Countries.ICELAND);
	Card eu2 = new Card(CardType.ARTILLERY,Countries.SCANDINAVIA);
	Card eu3 = new Card(CardType.ARTILLERY,Countries.UKRAINE);
	Card eu4 = new Card(CardType.CALVARY,Countries.GREAT_BRITAIN);
	Card eu5 = new Card(CardType.CALVARY,Countries.N_EUROPE);
	Card eu6 = new Card(CardType.INFANTRY,Countries.W_EUROPE);
	Card eu7 = new Card(CardType.CALVARY,Countries.S_EUROPE);
	
	Card af1 = new Card(CardType.INFANTRY,Countries.N_AFRICA);
	Card af2 = new Card(CardType.INFANTRY,Countries.EGYPT);
	Card af3 = new Card(CardType.ARTILLERY,Countries.E_AFRICA);
	Card af4 = new Card(CardType.CALVARY,Countries.CONGO);
	Card af5 = new Card(CardType.ARTILLERY,Countries.S_AFRICA);
	Card af6 = new Card(CardType.INFANTRY,Countries.MADAGASCAR);
	
	Card as1 = new Card(CardType.ARTILLERY,Countries.SIBERIA);
	Card as2 = new Card(CardType.CALVARY,Countries.YAKUTSK);
	Card as3 = new Card(CardType.CALVARY,Countries.KAMCHATKA);
	Card as4 = new Card(CardType.CALVARY,Countries.URAL);
	Card as5 = new Card(CardType.INFANTRY,Countries.IRKUTSK);
	Card as6 = new Card(CardType.INFANTRY,Countries.AFGHANISTAN);
	Card as7 = new Card(CardType.ARTILLERY,Countries.MONGOLIA);
	Card as8 = new Card(CardType.INFANTRY,Countries.JAPAN);
	Card as9 = new Card(CardType.CALVARY,Countries.CHINA);
	Card as10 = new Card(CardType.ARTILLERY,Countries.MIDDLE_EAST);
	Card as11 = new Card(CardType.INFANTRY,Countries.INDIA);
	Card as12 = new Card(CardType.ARTILLERY,Countries.SIAM);
	
	Card ar1 = new Card(CardType.CALVARY,Countries.INDONESIA);
	Card ar2 = new Card(CardType.CALVARY,Countries.NEW_GUINEA);
	Card ar3 = new Card(CardType.ARTILLERY,Countries.W_AUSTRALIA);
	Card ar4 = new Card(CardType.INFANTRY,Countries.E_AUSTRALIA);
	
	
	
	
	//PUBLIC METHODS
	
	//getters
	public int size() {
		return size;
	}
	
}
