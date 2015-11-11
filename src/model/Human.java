package model;

import java.util.List;
import java.util.Scanner;

public class Human extends Player
{

    public Human(int pid, int initArmies, Map b)
    {
        super(pid, initArmies, b);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String claim()
    {
        System.out.println("Please choose an unclaimed territory.");
        Scanner s = new Scanner(System.in);
        return s.next();
    }

    @Override
    public String placeRemaining()
    {
        System.out.println("Please reinforce one of your territories.");
        Scanner s = new Scanner(System.in);
        return s.next();
    }


	@Override
	public void fortify() {
		boolean legitChoice = false;
		while(!legitChoice) {
			System.out.println(map.listPlayerTerritories(this));
			System.out.println("Please choose a territory to fortify with troops.");
			Scanner s = new Scanner(System.in);
			Territory fortifyTo = map.getTerritory(s.next());
			List<Territory> possible = map.getConnectedTerritories(fortifyTo);
			System.out.println("Please choose a territory to fortify troops from.");
			for(Territory t : possible) {
				System.out.println(t.toString() + "\n");
			}
			Territory fortifyFrom = map.getTerritory(s.next());
			System.out.println("Please choose a number of armies to move from " +
								fortifyFrom.toString() + " to " + fortifyTo.toString() +
								" that is less than " + (fortifyFrom.getArmies()-1));
			int move = s.nextInt();
			fortifyFrom.addArmies(move * -1); //TODO this is some sketchy code
			fortifyTo.addArmies(move);
		}
	}

	@Override
	protected boolean decideCardExchange() {
		// TODO Auto-generated method stub
				if(cards.size() >= 5) {
					//implement logic or call a method to do the actual exchange
					System.out.println("You must trade in cards this turn.");
					System.out.println("Type the int+enter of the cards to trade in, one at a time.");
					for(int i = 0; i < cards.size(); i ++) {
						Card curr = cards.dealCard();
						System.out.println(curr.toString() + "\n");
						cards.returnCardToDeck(curr);
					}
					Scanner s = new Scanner(System.in);
					int first = s.nextInt();
					for(int i = 0; i < first-1; i ++) {
						Card curr = cards.dealCard();
						Territory onCard1 = map.getCountry(curr.getCountry());
						if(onCard1 != null) {
							onCard1.addArmies(2);
							gainArmies(2);
						}
						cards.returnCardToDeck(curr);
					}
					map.returnCard(cards.dealCard());
					int second = s.nextInt();
					for(int i = 0; i < second-1; i ++) {
						Card curr = cards.dealCard();
						Territory onCard2 = map.getCountry(curr.getCountry());
						if(onCard2 != null) {
							onCard2.addArmies(2);
							gainArmies(2);
						}
						cards.returnCardToDeck(curr);
					}
					map.returnCard(cards.dealCard());
					int third = s.nextInt();
					for(int i = 0; i < third-1; i ++) {
						Card curr = cards.dealCard();
						Territory onCard3 = map.getCountry(curr.getCountry());
						if(onCard3 != null) {
							onCard3.addArmies(2);
							gainArmies(2);
						}
						cards.returnCardToDeck(curr);
					}
					map.returnCard(cards.dealCard());
					return true;
				}
				return false;
	}
	
	@Override
	public boolean decideCardExchange2() {
				if(cards.size() >= 5) {
					//implement logic or call a method to do the actual exchange
					System.out.println("You must trade in cards this turn.");
					System.out.println("Enter the int of the cards to trade in, one at a time.");
					for(int i = 0; i < cards.size(); i ++) {
						Card curr = cards.dealCard();
						System.out.println(i + ": " + curr.toString() + "\n");
						cards.returnCardToDeck(curr);
					}
					Scanner s = new Scanner(System.in);
					int first = s.nextInt();
					for(int i = 0; i < first-1; i ++) {
						Card curr = cards.dealCard();
						Territory onCard1 = map.getCountry(curr.getCountry());
						if(onCard1 != null) {
							onCard1.addArmies(2);
							gainArmies(2);
						}
						cards.returnCardToDeck(curr);
					}
					map.returnCard(cards.dealCard());
					int second = s.nextInt();
					for(int i = 0; i < second-1; i ++) {
						Card curr = cards.dealCard();
						Territory onCard2 = map.getCountry(curr.getCountry());
						if(onCard2 != null) {
							onCard2.addArmies(2);
							gainArmies(2);
						}
						cards.returnCardToDeck(curr);
					}
					map.returnCard(cards.dealCard());
					int third = s.nextInt();
					for(int i = 0; i < third-1; i ++) {
						Card curr = cards.dealCard();
						Territory onCard3 = map.getCountry(curr.getCountry());
						if(onCard3 != null) {
							onCard3.addArmies(2);
							gainArmies(2);
						}
						cards.returnCardToDeck(curr);
					}
					map.returnCard(cards.dealCard());
					return true;
				}
				return false;
	}
	
	public void placeDeployedArmies2(int armies)
	{
		Scanner s = new Scanner(System.in);
		int choice;
		Territory terr;
		for(int i = 0; i < armies; i++)
		{
			System.out.println("Enter the number of the territory you would like to deploy a single army to.");
			for(int j = 0; j < territories.size(); j++)
			{
				System.out.println(j + ": " + territories.get(j).toString());
			}
			System.out.println("-----------------------");
			choice = s.nextInt();
			terr = territories.get(choice);
			terr.addArmies(1);
			loseAnArmy();
		}
	}
	


    @Override
    public void attack()
    {
        

    }


}
