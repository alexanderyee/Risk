package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class HardCpu extends Player
{

    private Random r;
    private int pid;
    private int atkFrom;
    private int atkTo;
    private ArrayList<String> realThoughts = new ArrayList<String>();

    // what have I conquered so far
    private boolean nAmer = false, sAmer = false, africa = false, austr = false,
            asia = false, euro = false;

    /*
     * Represents the Hard AI bot in Risk, will choose pseudo random decisions
     */
    public HardCpu(int pid, int initArmies, Map b)
    {
        super(pid, initArmies, b);
        r = new Random();
        this.pid = pid;
        addThoughts();
    }

    public HardCpu(int pid, int initArmies, Map b, Random s)
    {
        super(pid, initArmies, b);
        this.r = s;
        this.pid = pid;
        addThoughts();
    }

    private void addThoughts()
    {
        realThoughts.add("you suck at risk");
        realThoughts.add("you honestly suck");
        realThoughts.add("maybe you should quit");
        realThoughts.add("your moves are bad only 3 moves into the future..");
        realThoughts.add("i am rngesus, and i don't answer prayers");
    }

    @Override
    public void fortify()
    {
        ArrayList<Territory> borders = this.identifyBorders();
        HashMap<Territory, Integer> threatMap = this.determineThreats(borders);
        int redeployCount = 0;

        for (Territory i : this.territories)
        {
            if (redeployCount >= this.getArmies())
            {
                break;
            }

            if (!(borders.contains(i)))
            {
                while (i.getArmies() > 1)
                {
                    /*
                     * Finds the most threatened territory and moves an army
                     * there
                     */
                    Territory threatenedTerritory = borders.get(0);
                    for (Territory j : threatMap.keySet())
                    {
                        if (threatMap.get(j) > threatMap
                                .get(threatenedTerritory))
                        {
                            threatenedTerritory = j;
                        }
                    }

                    i.removeArmies(1);
                    threatenedTerritory.addArmies(1);
                    threatMap.put(threatenedTerritory,
                            threatMap.get(threatenedTerritory) - 1);
                }
            }
        }

    }

    private boolean isFortified(Territory t)
    {
        boolean result = true;
        for (Territory adj : t.getAdjacentTerritories())
        {
            if (t.getArmies() < (2 * adj.getArmies())) result = false;
        }
        return result;
    }

    public void fortifyAlternative()
    {
        ArrayList<Territory> borders = identifyBorders();
    }

    /*
     * Identify borders method, determines this units "border" countries.
     * (countries which have an enemy state near them)
     */
    public ArrayList<Territory> identifyBorders()
    {
        ArrayList<Territory> territoryList = super.getTerritories();
        ArrayList<Territory> adjList = new ArrayList<Territory>();
        ArrayList<Territory> borders = new ArrayList<Territory>();

        borders.removeAll(borders);
        for (Territory i : territoryList)
        {
            adjList.removeAll(adjList);
            adjList = (ArrayList<Territory>) i.getAdjacentTerritories();
            for (Territory j : adjList)
            {
                if (j.getOccupier().getPID() != this.pid)
                {
                    borders.add(i);
                }
            }
        }
        return borders;
    }

    /*
     * This is a non-random deploy method, should be re-factored later
     */
    public void placeDeployedArmies(int armiesToDeployWith)
    {
        ArrayList<Territory> borders = identifyBorders();
        HashMap<Territory, Integer> deployMap = determineThreats(borders);

        for (int i = 0; i < armiesToDeployWith; i++) // find my territory that
                                                     // is in the most danger
        {
            Territory smallest = ((ArrayList<Territory>) deployMap.keySet())
                    .get(0);
            for (Territory j : deployMap.keySet())
            {
                if (deployMap.get(j) < deployMap.get(smallest))
                {
                    smallest = j;

                }
            }
            smallest.addArmies(1); // add a troop to that territory
            deployMap.put(smallest, deployMap.get(smallest) - 1); // update

                                                                  // deployMap
                                                                  // too
        }
        System.out.println("HardCpu " + this.pid + "has deployed armies");
    }

    /*
     * This method is used by deploy, which will determine the best locations to
     * deploy the hard AI's armies. It does this by searching through the ai's
     * borders and finding the difference between armies and mapping that to the
     * territory, it only maps the highest difference between the territories
     * [Territory a] -> [difference of armies between Territory a and Territory
     * b]
     * @return HashMap<Territory, Integer>
     */
    private HashMap<Territory, Integer> determineThreats(
            ArrayList<Territory> borders)
    {
        ArrayList<Territory> threatList = new ArrayList<Territory>();

        HashMap<Territory, Integer> deployMap = new HashMap<Territory, Integer>();

        for (Territory i : borders)
        {
            ArrayList<Territory> adjList = (ArrayList<Territory>) i
                    .getAdjacentTerritories();
            for (Territory j : adjList)
            {
                if (j.getOccupier().getPID() != this.getPID() && deployMap
                        .get(i) < (i.getArmies() - (j.getArmies() - 1)))
                    deployMap.put(i, i.getArmies() - (j.getArmies() - 1));
            }

        }

        return deployMap;
    }

    @Override
    public void placeDeployedArmiesRand(int armies)
    {
        Territory terr;

        for (int i = 0; i < armies; i++)
        {
            if (!terrNAmer.isEmpty() && !nAmer)
                terr = terrNAmer.get(r.nextInt(terrNAmer.size()));
            else if (!terrSAmer.isEmpty() && !sAmer)
                terr = terrSAmer.get(r.nextInt(terrSAmer.size()));
            else if (!terrAfrica.isEmpty() && !africa)
                terr = terrAfrica.get(r.nextInt(terrAfrica.size()));
            else if (!terrAustr.isEmpty() && !austr)
                terr = terrAustr.get(r.nextInt(terrAustr.size()));
            else if (!terrAsia.isEmpty() && !asia)
                terr = terrAsia.get(r.nextInt(terrAsia.size()));
            else if (!terrEuro.isEmpty() && !euro)
                terr = terrEuro.get(r.nextInt(terrEuro.size()));
            else
                terr = territories.get(r.nextInt(territories.size()));
            terr.addArmies(1);
            loseAnArmy();
        }
        System.out.println("HardCpu " + this.pid + "has deployed armies");
    }

    @Override
    public boolean willAttack()
    {
        return true;
    }

    @Override
    public int attackFrom()
    {
        countTerritories();
        int maxDif = 0;
        int from = 0;
        int at = 0;
        int choice = 0;
        int choice2 = 0;
        if (!nAmer)
        {
            for (Territory t : terrNAmer)
                attackChoice(t, maxDif, from, at, choice, choice2);
            return atkFrom;
        }
        else if (!sAmer)
        {
            for (Territory t : terrSAmer)
                attackChoice(t, maxDif, from, at, choice, choice2);
            return atkFrom;
        }
        else if (!africa)
        {
            for (Territory t : terrAfrica)
                attackChoice(t, maxDif, from, at, choice, choice2);
            return atkFrom;
        }
        else if (!austr)
        {
            for (Territory t : terrAustr)
                attackChoice(t, maxDif, from, at, choice, choice2);
            return atkFrom;
        }
        else if (!asia)
        {
            for (Territory t : terrAsia)
                attackChoice(t, maxDif, from, at, choice, choice2);
            return atkFrom;
        } // end if(!asia)
        else
        { // if(!euro)
            for (Territory t : terrEuro)
                attackChoice(t, maxDif, from, at, choice, choice2);
            return atkFrom;
        }
    }

    private void attackChoice(Territory t, int maxDif, int from, int at,
            int choice, int choice2)
    {
        if (t.getArmies() > 1)
        {// if I can attack from it
            from = t.getArmies(); // check armies here
            for (Territory e : t.getAdjacentTerritories())
            {// go through adjacents
                if (e.getOccupier() != this)
                {// if it's an enemy terr
                    at = e.getArmies(); // get its armies
                    if ((from - at) > maxDif)
                    {
                        maxDif = from - at;
                        atkFrom = choice; // choose to attack where I have
                                          // greatest advantage
                        atkTo = choice2;
                    }
                }
                choice2++;
            } // end for
        }
        choice++;
    }

    /*
     * Returns a hashmap with a territory mapped to an object arrayList that
     * contains at index 0 the territory to attack, and index 1 the number of
     * armies to attack with
     */
    public HashMap<Territory, ArrayList<Object>> getAttackList()
    {
        HashMap<Territory, ArrayList<Object>> attackList = new HashMap<Territory, ArrayList<Object>>();
        
        ArrayList<Territory> borders = this.identifyBorders();
        
        for(Territory i : borders)
        {
            if(i.getArmies() > 1)
            {
            for(Territory j : i.getAdjacentTerritories())
            {
                if(!(j.getOccupier().equals(this) && j.getArmies() < i.getArmies() && !(attackList.containsValue(j))))
                {
                    Integer armiesToAttackWith = 0;
                    if(i.getArmies() > 3)
                    {
                        armiesToAttackWith = 3;
                    }
                    else
                    {
                        armiesToAttackWith = i.getArmies() - 1;
                        
                    }
                            
                        ArrayList<Object> toAttackWithDiceRolls = new ArrayList<Object>();
                        toAttackWithDiceRolls.add(j);
                        toAttackWithDiceRolls.add(armiesToAttackWith);
                        
                        attackList.put(i, toAttackWithDiceRolls);
                    }
                }
            }
        }
        
        return attackList;
    }



    private void countTerritories() // just to know if continents are captured
    {
        if (terrNAmer.size() == 9)
            nAmer = true;
        else
            nAmer = false;
        if (terrSAmer.size() == 4)
            sAmer = true;
        else
            sAmer = false;
        if (terrAfrica.size() == 6)
            africa = true;
        else
            africa = false;
        if (terrAustr.size() == 4)
            austr = true;
        else
            austr = false;
        if (terrAsia.size() == 12)
            asia = true;
        else
            asia = false;
        if (terrEuro.size() == 9)
            euro = true;
        else
            euro = false;
    }

    @Override
    public int attackAt(int atkTerrNum)
    {
        return atkTo;
    }

    @Override
    public boolean attackAgain() // prioritizes continent capture, looks for
                                 // double army advantage
    {
        if (terrNAmer.size() < 9)
        {
            for (Territory t : terrNAmer)
            {
                for (Territory adj : t.getAdjacentTerritories())
                {
                    if (adj.getOccupier() != this
                            && (adj.getArmies() * 2) < t.getArmies())
                        return true;
                }
            }
        }
        else if (terrSAmer.size() < 4)
        {
            for (Territory t : terrSAmer)
            {
                for (Territory adj : t.getAdjacentTerritories())
                {
                    if (adj.getOccupier() != this
                            && (adj.getArmies() * 2) < t.getArmies())
                        return true;
                }
            }
        }
        else if (terrAfrica.size() < 6)
        {
            for (Territory t : terrAfrica)
            {
                for (Territory adj : t.getAdjacentTerritories())
                {
                    if (adj.getOccupier() != this
                            && (adj.getArmies() * 2) < t.getArmies())
                        return true;
                }
            }
        }
        else if (terrAustr.size() < 4)
        {
            for (Territory t : terrAustr)
            {
                for (Territory adj : t.getAdjacentTerritories())
                {
                    if (adj.getOccupier() != this
                            && (adj.getArmies() * 2) < t.getArmies())
                        return true;
                }
            }
        }
        else if (terrAsia.size() < 12)
        {
            for (Territory t : terrAsia)
            {
                for (Territory adj : t.getAdjacentTerritories())
                {
                    if (adj.getOccupier() != this
                            && (adj.getArmies() * 2) < t.getArmies())
                        return true;
                }
            }
        }
        else if (terrEuro.size() < 4)
        {
            for (Territory t : terrEuro)
            {
                for (Territory adj : t.getAdjacentTerritories())
                {
                    if (adj.getOccupier() != this
                            && (adj.getArmies() * 2) < t.getArmies())
                        return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean willFortify() // why wouldn't you
    {
        return true;
    }

    @Override
    public int attackDice(int armies) // errs on the side of caution
    {
        if (armies <= 6)
            return 3;
        else if (armies <= 4)
            return 2;
        else
            return 1;
    }

    @Override
    public int defenseDice(int atkPID, String defStr, int atkDice, int armies) // errs
                                                                               // on
                                                                               // the
                                                                               // side
                                                                               // of
                                                                               // caution
    {
        if (armies <= 6)
            return 3;
        else if (armies <= 4)
            return 2;
        else
            return 1;
    }

    @Override
    public boolean willTradeCards()
    {
        if (cards.size() == 5)
            return true;
        else
            return false;
    }

    public ArrayList<Integer> cardSetChoices()
    {
        ArrayList<Integer> choices = new ArrayList<Integer>(3);
        int matches = 0;
        for (int i = 0; i < cards.size(); i++) // for each card in hand
        {
            choices.add(i);
            for (int j = 0; j < cards.size(); j++) // compare the other cards to
                                                   // i
            {
                if (i != j && cards.get(i).getCardType() == cards.get(j)
                        .getCardType())
                { // find 3 matches
                    matches++;
                    choices.add(j);
                }
            }
            if (matches == 3)
                return choices;
            else if (matches == 0) // or find 3 cards of 3 kinds
            {
                for (int j = 0; j < cards.size(); j++)
                {
                    if (i != j && cards.get(i).getCardType() != cards.get(j)
                            .getCardType())
                        choices.add(j);
                }
                return choices;
            }
        }
        return choices;
    }

    @Override
    public Territory claimTerritory(List<Territory> list)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int attackInvade(int armies)
    {
        // TODO Auto-generated method stub
        return 0;
    }

}
