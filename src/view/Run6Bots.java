package view;

import java.util.Scanner;

import model.Game;

public class Run6Bots
{

    public static void main(String[] args) throws Exception
    {
        System.out.println("================================================");
        System.out.println("Intro shit");
        System.out.println("================================================");
        Game g = new Game(6, 0);
        g.beginGame();
    }

}
