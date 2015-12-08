package view;

import model.Game;

public class Run6Bots
{

    public static void main(String[] args)
    {
//        System.out.println("================================================");
//        System.out.println("Intro stuff");
//        System.out.println("================================================");
//        
//        
//        Game g = new Game(6, 0);
//        int easy = 0;
//        int medium = 0;
//        for(int i = 0; i < 1000; i++) //run 1,000 games
//        {
//            g = new Game(6, 0);
//            if(g.beginGame() > 3) //increment the wins of the type of player from that game
//                easy++;
//            else
//                medium++;
//        }
//        System.out.println("Easy bots had a total of "+easy+" wins.");
//        System.out.println("Int. bots had a total of "+medium+" wins.");
        
        
        Game test = new Game(3, 3, 0, 0);
        
        int easyWins = 0;
        int mediumWins = 0;
        int hardWins = 0;
        
        for(int i = 0; i < 1000; i++)
        {
            test = new Game(3, 2, 1, 0);
            if(test.beginGame() == 5)
            {
                
            }
            else if(test.beginGame() >= 3)
            {
                mediumWins ++;
            }
            else
                easyWins++;
            
        }
        
        System.out.println("Easy bots had a total of "+easyWins+" wins.");
        System.out.println("Int. bots had a total of "+mediumWins+" wins.");
        System.out.printf("Hard bots has a total of %d wins%n", hardWins);
        
    }
    
    
    

}
