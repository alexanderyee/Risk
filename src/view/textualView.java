package view;

import java.util.Scanner;

import model.Game;

public class textualView {

	public static void main(String [] args)
	{
		/*Scanner s = new Scanner(System.in);
		System.out.println("Enter number of humans.");
		int humans = s.nextInt();
		System.out.println("Enter number of bots.");
		int bots = s.nextInt();*/
		Game g = new Game(0, 3);
	
	    System.out.println("Player 1 territories \n" + g.getTerritories(1));
	    System.out.println("Player 2 territories \n" + g.getTerritories(2));
	    System.out.println("Player 3 territories \n" + g.getTerritories(3));
	}
	
}
