package view;

import model.Game;

public class Run6Bots
{

    public static void main(String[] args)
    {
        System.out.println("================================================");
        System.out.println("Intro stuff");
        System.out.println("================================================");
        
        Game g = new Game(6, 0);
        int easy = 0;
        int medium = 0;
        for(int i = 0; i < 1000; i++) //run 1,000 games
        {
            g = new Game(6, 0);
            if(g.beginGame() > 3) //increment the wins of the type of player from that game
                easy++;
            else
                medium++;
        }
        System.out.println("Easy bots had a total of "+easy+" wins.");
        System.out.println("Int. bots had a total of "+medium+" wins.");
    }
    
    
    

}
