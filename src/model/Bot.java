package model;

public class Bot extends Player {
	
	//MEMBER VARIABLES
	private int turnsTaken;
	
	/*
	 *  Constructor for the bot class
	 *  @param int player identification number
	 *  @param int inital armies
	 *  @param 
	 * 
	 */
	public Bot(int pid, int initArmies, Map b) {
		super(pid, initArmies, b);
		// TODO Auto-generated constructor stub
		turnsTaken = 0;
	}

	//PUBLIC METHODS
	public void fortify() {
		//do a bunch of decision making
		tookTurn();
	}
	
	//getters
	public int getTurns() {
		return turnsTaken;
	}

	//setters
	public void tookTurn() {
		turnsTaken++;
	}

	@Override
	public String claim() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String placeRemaining() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean decideCardExchange() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean decideCardExchange2() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void placeDeployedArmies2(int armies) {
		// TODO Auto-generated method stub
		
	}

}
