package view;

import java.util.Scanner;

import model.Game;

public class textualView {

	public static void main(String [] args)
	{
		Scanner s = new Scanner(System.in);
		System.out.println("Enter number of humans.");
		int humans = s.nextInt();
		System.out.println("Enter number of bots.");
		int bots = s.nextInt();
		Game letsPlay = new Game(bots, humans);
	
	
	}
	
}
