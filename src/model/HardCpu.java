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
    private ArrayList<Territory> validChoices = new ArrayList<Territory>();
    private int numTerrsAtBeginningOfTurn;
    private ArrayList<String> realThoughts = new ArrayList<String>();
    
    //factors to take into account, for attackChoice
    private int maxDif = 0;
    private int from = 0;
    private int at = 0;
    private int choice = 0;
    private int choice2 = 0;
    private int attacksThisTurn;

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

        System.out.println(borders.toString());
        System.out.println(threatMap.toString());



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

                    threatenedTerritory.addArmies(1);
                    i.removeArmies(1);

                    threatMap.put(threatenedTerritory,
                            threatMap.get(threatenedTerritory) - 1);
                }
            }
            redeployCount ++;
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
        
        for (Territory i : territoryList)
        {
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
    //@Override
    public void placeDeployedArmies(int armiesToDeployWith)
    {
        ArrayList<Territory> borders = identifyBorders();
        HashMap<Territory, Integer> deployMap = determineThreats(borders);


        System.out.println(borders.toString());
        System.out.println(deployMap.toString());

        for (int i = 0; i < armiesToDeployWith; i++) // find my territory that
            // is in the most danger
        {
            Territory smallest = ((ArrayList<Territory>) deployMap.keySet()).get(0);
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
        //System.out.println("HardCpu " + this.pid + "has deployed armies");
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
                if (j.getArmies() > 1 && j.getOccupier().getPID() != this.getPID() && (i.getArmies() - (j.getArmies() - 1) > 1))
                    deployMap.put(i, i.getArmies() - (j.getArmies() - 1));
            }

        }

        //System.out.println(deployMap.toString());
        return deployMap;
    }

    @Override
    public void placeDeployedArmiesRand(int armies)
    {
        ArrayList<Territory> borders = identifyBorders();
        for (int i = 0; i < armies; i++)
        {
            Territory least = borders.get(0);
            for (Territory terr : borders)
            {
                if (terr.getArmies() < least.getArmies())
                {
                    least = terr;
                }
            }
            least.addArmies(1);
            loseAnArmy();
        }
        //System.out.println("EasyBot " + this.pid + " has deployed armies");
//        Territory terr;
//
//        for (int i = 0; i < armies; i++)
//        {
//            if (!terrNAmer.isEmpty() && !nAmer)
//                terr = terrNAmer.get(r.nextInt(terrNAmer.size()));
//            else if (!terrSAmer.isEmpty() && !sAmer)
//                terr = terrSAmer.get(r.nextInt(terrSAmer.size()));
//            else if (!terrAfrica.isEmpty() && !africa)
//                terr = terrAfrica.get(r.nextInt(terrAfrica.size()));
//            else if (!terrAustr.isEmpty() && !austr)
//                terr = terrAustr.get(r.nextInt(terrAustr.size()));
//            else if (!terrAsia.isEmpty() && !asia)
//                terr = terrAsia.get(r.nextInt(terrAsia.size()));
//            else if (!terrEuro.isEmpty() && !euro)
//                terr = terrEuro.get(r.nextInt(terrEuro.size()));
//            else
//                terr = territories.get(r.nextInt(territories.size()));
//            terr.addArmies(1);
//            loseAnArmy();
//        }
    }

    @Override
    public boolean willAttack()
    {
        if (territories.size() == 42) // I won!
            return false;
        ArrayList<Territory> newValidChoices = new ArrayList<Territory>();
        ArrayList<Territory> adjList = null;
        for (Territory terr : territories) // go through our territories
        {
            if (terr.getArmies() >= 2) // if one has at least 2 territories it
                // can attack as long as...
            {
                adjList = (ArrayList<Territory>) terr.getAdjacentTerritories();
                for (Territory enemy : adjList) // make sure you have a valid
                    // enemy target from that
                    // location
                { // if(not attacking yourself
                    if (enemy.getOccupier().getPlayerID() != pid)
                    {
                        newValidChoices.add(terr);
                        break; // we only need to add it once
                    }
                }
            }
        }
        validChoices = newValidChoices;
        boolean canAttack = !validChoices.isEmpty();
        //numTerrsAtBeginningOfTurn = territories.size();
        return canAttack;
    }

    @Override
    public int attackFrom()
    {
        int choice = r.nextInt(validChoices.size()); // bound must be positive
        Territory from = validChoices.get(choice);
        int indexInAllTerritories = this.territories.indexOf(from);
        return indexInAllTerritories;
//        countTerritories(); //just to know if continents are captured
//        maxDif = 0; //reset our factors we consider
//        from = 0;
//        at = 0;
//        choice = 0;
//        choice2 = 0;
//        if (!terrNAmer.isEmpty() && !nAmer)
//        {
//            for (Territory t : terrNAmer)
//                attackChoice(t, maxDif, from, at, choice, choice2);
//            Territory fuckingFromHere = terrNAmer.get(atkFrom);
//            int fuckingFromIndex = territories.indexOf(fuckingFromHere);
//            return fuckingFromIndex;
//            //return atkFrom;
//        }
//        else if (!terrSAmer.isEmpty() && !sAmer)
//        {
//            for (Territory t : terrSAmer)
//                attackChoice(t, maxDif, from, at, choice, choice2);
//            Territory fuckingFromHere = terrSAmer.get(atkFrom);
//            int fuckingFromIndex = territories.indexOf(fuckingFromHere);
//            return fuckingFromIndex;
//            //return atkFrom;
//        }
//        else if (!terrAfrica.isEmpty() && !africa)
//        {
//            for (Territory t : terrAfrica)
//                attackChoice(t, maxDif, from, at, choice, choice2);
//            Territory fuckingFromHere = terrAfrica.get(atkFrom);
//            int fuckingFromIndex = territories.indexOf(fuckingFromHere);
//            return fuckingFromIndex;
//            //return atkFrom;
//        }
//        else if (!terrAustr.isEmpty() && !austr)
//        {
//            for (Territory t : terrAustr)
//                attackChoice(t, maxDif, from, at, choice, choice2);
//            Territory fuckingFromHere = terrAustr.get(atkFrom);
//            int fuckingFromIndex = territories.indexOf(fuckingFromHere);
//            return fuckingFromIndex;
//            //return atkFrom;
//        }
//        else if (!terrAsia.isEmpty() && !asia)
//        {
//            for (Territory t : terrAsia)
//                attackChoice(t, maxDif, from, at, choice, choice2);
//            Territory fuckingFromHere = terrAsia.get(atkFrom);
//            int fuckingFromIndex = territories.indexOf(fuckingFromHere);
//            return fuckingFromIndex;
//            //return atkFrom;
//        } // end if(!asia)
//        else
//        {
//            if(!terrEuro.isEmpty() && !euro)
//            {
//                System.out.println("Huge error, HardCPU has no terr's and is being asked where to attack from");
//            }
//            for (Territory t : terrEuro)
//                attackChoice(t, maxDif, from, at, choice, choice2);
//            Territory fuckingFromHere = terrEuro.get(atkFrom);
//            int fuckingFromIndex = territories.indexOf(fuckingFromHere);
//            return fuckingFromIndex;
//            //return atkFrom;
//        }
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

        for (Territory i : borders)
        {
            if (i.getArmies() > 1)
            {
                for (Territory j : i.getAdjacentTerritories())
                {
                    if (!(j.getOccupier().equals(this)
                            && j.getArmies() < i.getArmies()
                            && !(attackList.containsValue(j))))
                    {
                        Integer armiesToAttackWith = 0;
                        if (i.getArmies() > 3)
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
     //   return atkTo;
        Territory from = this.getTerritories().get(atkTerrNum);
        int choice = -1;
        boolean keepGoing = true;
        while (keepGoing)
        {
            choice = r.nextInt(from.getAdjacentTerritories().size());
            if (from.getAdjacentTerritories().get(choice).getOccupier() != this)
                keepGoing = false;
        }
        Territory at = from.getAdjacentTerritories().get(choice);
        return choice;
    }

    @Override
    public boolean attackAgain() // prioritizes continent capture, looks for
    // double army advantage
    {
        attacksThisTurn++;
        if(attacksThisTurn >= 2 && this.limitedAttacks) //for GUI
        {
            attacksThisTurn = 0;
            return false;
        }
        if(attacksThisTurn >= 3) //for run6bots
        {
            attacksThisTurn = 0;
            return false;
        }
        if (territories.size() == 42) // If you won, don't attack again
            return false;
        ArrayList<Territory> newValidChoices = new ArrayList<Territory>(); 
        // otherwise check if I CAN attack
        ArrayList<Territory> adjList = null;
        for (Territory terr : territories) // go through our territories
        {
            if (terr.getArmies() >= 2)
                // if one has at least 2 territories it can attack as long as...
            {
                adjList = (ArrayList<Territory>) terr.getAdjacentTerritories();
                for (Territory enemy : adjList) 
                    // make sure you have a valid enemy target from that location if (not attacking yourself)
                {
                    if (enemy.getOccupier().getPlayerID() != pid)
                    {
                        newValidChoices.add(terr);
                        break; // we only need to add it once
                    }
                }
            }
        }

        validChoices = newValidChoices;
        boolean canAttack = !validChoices.isEmpty();
        if(!canAttack)
            return false;
        if (terrNAmer.size() < 9)
        {
            for (Territory t : terrNAmer)
            {
                if(validChoices.contains(t))
                {
                    for (Territory adj : t.getAdjacentTerritories())
                    {
                        if (adj.getOccupier() != this
                                && (adj.getArmies() * 2) < t.getArmies())
                            return true;
                    }
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
        return false;
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
        for (Territory i : territories)
        {
            if (i.getOccupier() == null)
            {
                return i;
            }
        }
        return null;
    }

    @Override
    public int attackInvade(int armies)
    {
        // TODO Auto-generated method stub
        return 0;
    }

}