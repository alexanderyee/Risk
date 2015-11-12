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

    // CONSTRUCTOR
    /*
     * Constructor for the territory
     */
    public Territory(Countries name)
    {
        adj = new ArrayList<Territory>();
        this.cont = name.getContinent();
        this.name = name;
        this.color = cont.getColor();
        armies = 0;
        occupier = null;
    }

    public void setAdj(Territory[] adjs)
    {
        for (int i = 0; i < adjs.length; i++)
            this.adj.add(adjs[i]);
        this.numAdj = adjs.length;
    }
    // PUBLIC METHODS

    // getters
    public int getArmies()
    {
        return this.armies;
    }

    public Player getOccupier()
    {
        return this.occupier;
    }

    public int defDice()
    {
        return this.defDice;
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
        this.armies += n;
        return this.armies;
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

    // player methods
    public void defend(int oppDice)
    {
        defDice = occupier.defend(oppDice);
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
        return name.toString();
    }

}
