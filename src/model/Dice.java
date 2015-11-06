package model;

import java.util.ArrayList;
import java.util.Random;

public class Dice {

	//MEMBER VARIABLES
	private Random luck;
	
	//CONSTRUCTOR
	public Dice() {
		luck = new Random();
	}
	
	//PUBLIC METHODS
	public int roll() {
		return luck.nextInt(6) + 1; //nextInt(6) returns 0 <= retVal <= 5
	}
	
	public int[] roll(int numDiceRolled) {
		int[] result = new int[6];
		for(int ii = 0; ii < numDiceRolled; ii++)
			result[ii] = roll();
		return result;
	}
	
}
