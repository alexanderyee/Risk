package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game
{

    // MEMBER VARIABLES
    // logic variables
    private int turnsPlayed;
    private int roundsPlayed;
    private boolean gameOver;
    private int cardSetValue; // we can delete this now, moving it to Map class
    // game piece variables
    private Map map;
    // player variables
    private int numPlayers;
    private int handsReedemed = 0;
    private ArrayList<Player> players;
    private int currentPID;

    // CONSTRUCTOR
    public Game(int numBots, int numHumans)
    {
        initializeMemberVariables(numBots, numHumans);
        initializePlayers(numBots, numHumans);
        setupGame();
    }

    // CONSTRUCTOR HELPER METHODS
    private void initializeMemberVariables(int numBots, int numHumans)
    {
        turnsPlayed = 0;
        roundsPlayed = 0;
        gameOver = false;
        cardSetValue = 4;
        map = new Map();
        this.numPlayers = numBots + numHumans;
        players = new ArrayList<Player>();
    }

    private void initializePlayers(int numBots, int numHumans)
    {
        int initArmies = 0;
        if (numPlayers == 3)
            initArmies = 35;
        else if (numPlayers == 4)
            initArmies = 30;
        else if (numPlayers == 5)
            initArmies = 25;
        else if (numPlayers == 6) initArmies = 20;
        for (int ii = 0; ii < numBots; ii++)
        { // instantiate bots

            Player p = new EasyBot(ii, initArmies, map);
            players.add(p);
        }
        for (int jj = numBots; jj < numPlayers; jj++)
        { // instantiate
          // humans

            Player p = new Human(jj, initArmies, map);

            players.add(p);
        }
    }

    private void setupGame()
    {
        rollToGoFirst();
        claimTerritories();
        beginGame();
    }

    private void rollToGoFirst()
    {
        Random r = new Random();
        currentPID = r.nextInt(players.size()); // so this number is 0 to
                                                // (size-1)
    }

    private void claimTerritories()
    {
        System.out.println("Randomly claiming territories.");
        for (int ii = 0; ii < 42; ii++)
        {

            if (currentPID >= players.size()) currentPID = 0;
            map.giveRandomTerritory(players.get(currentPID));

            currentPID++;
        }

    }

    private void beginGame()
    {
        Player curr;
        while (!gameOver)
        {
            curr = players.get(currentPID);
            int bonus = curr.deploy();
            bonus += map.exchangeCards(curr);
            curr.placeDeployedArmies(bonus);
            attack();
            if (curr.getTotalTerritories() == 42)
                gameOver = true;
            else
            {
                curr.fortify(); // TODO (AI-01): You'll have to change this to a
                // dynamic value
                // System.out.println("Randomly claiming territories.");
                // for (int ii = 0; ii < 42; ii++)
                // {
                // if (currentPID >= players.size()) currentPID = 0;
                // map.giveRandomTerritory(players.get(currentPID));
                // currentPID++;
                // }
                // THIS SEEMS LIKE A BUG ^ IT'S FROM claimTerritories() METHOD
            }
        }
    }

    // PRIVATE METHODS
    public String getTerritories(int k) // not meant to take in an index, but a
                                        // player number
    {
        return players.get(k - 1).getTerroritories();
    }

    public void placeArmyInPlayerTerritory(int p, int terrNumber)
    {
        players.get(p - 1).addArmy(terrNumber);
    }

    public void attack()
    {

        // Asks if the player wants to attack or no

        boolean choice = players.get(currentPID).willAttack();
        if (choice == true)
        {
            Player currentPlayer = players.get(currentPID); // don't use
                                                            // get(currentPID-1),
                                                            // I think that
                                                            // gives index out
                                                            // of bounds
            // Determines the current player object
            System.out.println(currentPlayer.getTerritories());
            try
            {

                // Prints out a list of the number of territories this player
                // has and the territories that are adjacent to each other
                boolean attackUnresolved = true;
                while (attackUnresolved)
                {
                    // get the two territory choices involved in the battle
                    int attackingTerritoryNumber = currentPlayer.attackFrom();
                    Territory attackingTerritory = currentPlayer
                            .getTerritories().get(attackingTerritoryNumber);

                    int defendingTerritoryNumber = currentPlayer
                            .attackAt(attackingTerritoryNumber);
                    Territory defendingTerritory = attackingTerritory
                            .getAdjacentTerritories()
                            .get(defendingTerritoryNumber);

                    // carry out the dice rolling and army losses
                    resolveAttack(attackingTerritory, defendingTerritory);

                    System.out.println("Attarckers terrys \n"
                            + this.getTerritories(currentPID));
                    Player defendingPlayer = defendingTerritory.getOccupier();
                    System.out.println("defenders terrys \n"
                            + defendingPlayer.getTerroritories());

                    if (currentPlayer.attackAgain())
                    {
                        attackUnresolved = false;
                        if (currentPlayer.willFortify())
                            currentPlayer.fortify();
                    }
                } // end while
            } // end try
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
        } // end if
        return;
    }// end method

    ///////////////////// Resolved attack

    public boolean resolveAttack(Territory attacking, Territory defending)
    {
        /*
         * Needs error checking to make sure that there are at least 2 armies in
         * the attacking territory
         */
        Dice dice = new Dice();

        Player attacker = attacking.getOccupier();
        Player defender = defending.getOccupier();

        int attackerRollNumber = attacker.attackDice();
        if (attackerRollNumber > attacking.getArmies() - 1)
        {
            System.out.printf("you can roll at most %d please try again \n\n",
                    Math.min(attacking.getArmies() - 1, 3));

        }
        ArrayList<Integer> attackersRolls;
        // attackersRolls.addAll(dice.roll2(attackerRollNumber));

        int atkPID = attacking.getOccupier().getPID() + 1;
        String defStr = defending.toString();
        int atkDice = attackerRollNumber;

        int defenderRollNumber = defender.defenseDice(atkPID, defStr, atkDice);

        ArrayList<Integer> defendersRolls;
        int min = Math.max(attackerRollNumber, defenderRollNumber);
        attackersRolls = dice.roll2(attackerRollNumber);

        defendersRolls = dice.roll2(defenderRollNumber);

        Collections.sort(attackersRolls, Collections.reverseOrder());
        Collections.sort(defendersRolls, Collections.reverseOrder());

        for (int i = 0; i < min; i++)
        {
            // System.out.printf("attacker %d defender %d
            // \n",attackersRolls.get(i),defendersRolls.get(i));

            if (attackersRolls.get(i) <= defendersRolls.get(i))

            {
                System.out.printf(" attacker rolled %d \ndefenders roll %d \n",
                        attackersRolls.get(i), defendersRolls.get(i));

                attacking.removeArmies(1);
            }
            else if (attackersRolls.get(i) > defendersRolls.get(i))
            {
                System.out.printf(" attacker rolled %d \ndefenders roll %d \n",
                        attackersRolls.get(i), defendersRolls.get(i));
                defending.removeArmies(1);
            }
        }

        // if the defending player loses, give the territory to the attacking
        // player
        // and return true, the attack has been resolved
        if (defending.getArmies() == 0)
        {
            defending.changeOccupier(attacking.getOccupier());
            return true;
        }
        else if (attacking.getArmies() == 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Territory getTerritory(String c)
    {
        return map.getTerritory(c);
    }

}
