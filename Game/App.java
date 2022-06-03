package Game;

import Exceptions.*;
import Effects.*;
import java.util.*;

//This will eventually be the application file. For now it can serve as a sort of test playground.
public class App {
    public static final Deck deck = new Deck(); //the deck do be used throughout the game.

    public static void main(String[] args) {
        //Game initiation logic
        Game.buildEffects(deck); //Build the Hashtable associating effect name strings with effects.
        Tools.showOnlyMessage("Welcome to JavaCoup!\n", 1.5);
        
        Tools.showOnlyMessage("Before we begin, take a moment to review the short guide below\n", 2.5);
        Tools.showTable();
        Tools.sleep(5);
        System.out.println();System.out.println();
        
        Tools.showMessage("Now let's get some players registered. ", 1.5);

        String[] validNumPlayers = {"3", "4", "5", "6"};
        String rawPlayerCount = Tools.promptInput("Firstly, how many players are there? (Minimum of 3 and maximum of 6): ",
                          "Sorry, that won't work. Please enter a number between 3 and 6: ", 
                          validNumPlayers);
        
        int plyrCount = Integer.parseInt(rawPlayerCount);
        for (int i = 1; i <= plyrCount; i++) {
            Game.registerPlayer();
            System.out.println("Thanks for joining, " + Game.ALL_PLAYERS.get(Game.ALL_PLAYERS.size()-1).getName() + "!");
            if (i < plyrCount) {
                System.out.println("Please press enter and pass the computer to the next registrant.");
                Tools.input.nextLine(); 
            }
            Tools.input.nextLine();
        }

        //Rounds loop
        boolean stillPlaying = true;
        while(stillPlaying){
            //Round Initiation
            Game.resetActivePlayers(); //Puts all registered players into active players list and reset their fields.
            deck.shuffle(); //shuffle the deck.
            for(Player player : Game.PLAYERS) { //Each player draws two cards;
                player.getHand().add(deck.drawCard());
                player.getHand().add(deck.drawCard());
                Game.showCards(player); //Shows the drawn cards to the player;
            }

            
            String[] yN = {"y", "n"};
            String cont = Tools.promptInput("Would you like to play another round [y/n]: ",
                                        "Sorry, I didn't get that. Would you like to play another round [y/n]",
                                        yN);
            stillPlaying = cont.toLowerCase().equals("y");
        }
    }
}
