/*
 * If i had a clue how to do this one, I would have 
 */

package model;

public class EasyBot extends Player
{

    private int seed;
    
    /* Represents the Easy AI bot in Risk, will choose pseudo random decisions 
     * 
     */
    public EasyBot(int pid, int initArmies, Map b)
    {
        super(pid, initArmies, b);
        // TODO Auto-generated constructor stub
    }

    public EasyBot(int pid, int initArmies, Map b, int s)
    {
        super(pid, initArmies, b);
        
    }
    
    public void attack()
    {
        
    }
    
    public int getSeed()
    {
        return seed;
    }
    
    @Override
    public String claim()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String placeRemaining()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void fortify()
    {
        // TODO Auto-generated method stub
        
    }

	@Override
	public void placeDeployedArmies(int armies) {
		// TODO Auto-generated method stub
		
	}

}
