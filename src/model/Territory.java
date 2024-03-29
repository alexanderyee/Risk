package model;

import java.util.ArrayList;
import java.util.List;

public class Territory
{
    // need to collaborate with player on attacking/defending!!
    // MEMBER VARIABLES
    private int armies;
    private Player occupier;
    private int defDice;
    private Continent cont;
    private Color color;
    private Countries name;
    private List<Territory> adj;
    private int numAdj;
    private int X, Y;

    // CONSTRUCTOR
    /*
     * Constructor for the territory
     */
    public Territory(Countries name, int x, int y)
    {
        adj = new ArrayList<Territory>();
        this.cont = name.getContinent();
        this.name = name;
        this.color = cont.getColor();
        armies = 0;
        occupier = null;
        this.X = x;
        this.Y = y;
    }

    public void setAdj(Territory[] adjs)
    {
        for (int i = 0; i < adjs.length; i++)
            this.adj.add(adjs[i]);
        this.numAdj = adjs.length;
    }

    // PUBLIC METHODS
    public Countries getCountry()
    {
        return this.name;
    }

    // getters
    public int getArmies()
    {
        return this.armies;
    }

    public Player getOccupier()
    {
        return this.occupier;
    }

    public List<Territory> getAdjacentTerritories()
    {
        return this.adj;
    }

    public Continent getContinent()
    {
        return this.cont;
    }

    public Color getColor()
    {
        return this.color;
    }

    // setters
    public void setArmies(int a)
    {
        armies = a;
    }

    public int addArmies(int n)
    {
        
        if (armies + n < 0)
        {
            System.out.println("Negative armies  "+ n);
            return this.armies;
        }
        else
        {
            this.armies += n;
            return this.armies;
        }
        
    }

    public int removeArmies(int n)
    {
        if (this.armies - n > 0)
        {
            this.armies -= n;
            return this.armies;
        }
        System.out.println("Error: One army must be left on territory");
        return this.armies;
    }

    public void changeOccupier(Player player)
    {

        this.occupier = player;

    }

    public void lose()
    {
        armies--;
        occupier.loseAnArmy();
    }

    public boolean isOccupied()
    {
        if (this.occupier == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    // board methods
    @Override
    public String toString()
    {
        return name.toString() + " number of armies is: " + armies;
    }

    public int getPointX()
    {
        // TODO Auto-generated method stub
        return this.X;
    }

    public int getPointY()
    {
        return this.Y;
    }

}
