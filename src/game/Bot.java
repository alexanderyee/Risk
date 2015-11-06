package game;

public class Bot extends Player {
	
	//MEMBER VARIABLES
	private int turnsTaken;
	
	//CONSTRUCTOR
	public Bot(int pid, int initArmies) {
		super(pid, initArmies);
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

}
