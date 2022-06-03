package Game;

import Effects.*;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Scanner;
import Exceptions.*;

// A static helper class to the App.java application.
// This class effectively handles the implementation of the game logic
// while the Application class handles the flow of gameplay.
public class Game {
    public static final Hashtable<String,Effect> EFFECTS = new Hashtable<String,Effect>(7);
    public static final ArrayList<Player> ALL_PLAYERS = new ArrayList<Player>();
    public static final ArrayList<Player> PLAYERS = new ArrayList<Player>();
    public static final Scanner input = new Scanner(System.in); // Creates a single scanner to be used throughout.

    public static void buildEffects(){
        EFFECTS.put("income", new Income());
        EFFECTS.put("foreign aid", new ForeignAid());
        EFFECTS.put("coup", new Coup());
        EFFECTS.put("tax", new Tax());
        EFFECTS.put("assassinate", new Assassinate());
        EFFECTS.put("steal", new Steal());
        EFFECTS.put("income", new Income());
    }

    public static boolean checkEffectBluff(Player player, Effect effect) {
        for (Card card : player.getHand()){
            if (card.getEffect().equals(effect)) return false; //returns false if it is NOT a bluff, to be stored in a variable called "isBluffing"
        }
        return true; //returns true if it IS a bluff, to be stored in a variable called "isBluffing"
    }

    public static boolean checkCounteractBluff(Player player, Effect effect) {
        for (Card card : player.getHand()){
            if(card.getCounteraction().equals("block "+ effect.getName())) return false; //returns false if it is NOT a bluff , to be stored in a variable called "isBluffing"
        }
        return true; //returns true if it IS a bluff, to be stored in a variable called "isBluffing"
    }

    public static void registerPlayer(){
        int currentIndex = ALL_PLAYERS.size();
        System.out.print("Please enter the name for Player " + (currentIndex + 1) + ". ");
        System.out.print("Note - Player names cannot contain spaces: ");
        String playerName = input.next();
        ALL_PLAYERS.add(new Player(playerName));
    }

    public static void resetActivePlayers() {
        PLAYERS.clear();
        PLAYERS.addAll(ALL_PLAYERS);
    }

    public static String getWinner() {
        /**
         * Checks if only one active player is left at the end of the round.
         * If so, prints a message returns that player's name as a string.
         * Otherwise, returns null.
         */
        if(PLAYERS.size() == 1) {
            String winner = PLAYERS.get(0).getName();
            System.out.println(winner + " is the winner of this round!");
            return winner;
        }
        return null;
    }

    public static Player findActivePlayer(String name) throws PlayerNotFoundException{
        for(Player player : PLAYERS) {
            if (player.getName().toLowerCase().equals(name.toLowerCase()))
                return player;
        }
        
        throw new PlayerNotFoundException();
    }

    public static Player findAnyPlayer(String name) throws PlayerNotFoundException{
        for(Player player : ALL_PLAYERS) {
            if (player.getName().toLowerCase().equals(name.toLowerCase()))
                return player;
        }
        
        throw new PlayerNotFoundException();
    }

    public static void updatePlayerList() {
        for (int i = 0; i < PLAYERS.size(); i++) {
            if(!PLAYERS.get(i).isAlive()){
                System.out.println(PLAYERS.get(i).getName() + " has been exiled!");
                PLAYERS.remove(i);
            }
        }
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void sleep(int seconds){
        try {
            Thread.sleep(1000*seconds);
        }
        catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public static String promptInput(String prompt, String reprompt, String[] validInputs) {
        int iters = 0;
        boolean valid = false;
        String userInput = "";
        if (validInputs != null){
            while(!(valid)) {    
                String statement = ++iters == 1 ?
                                            prompt :
                                            reprompt; 
                System.out.print(statement);
                userInput = input.nextLine();
                for (String validInput : validInputs) {
                    if (userInput.toLowerCase().equals(validInput.toLowerCase())){
                        valid = true;
                        break;
                    }
                }
            }
        }
        else {
            System.out.print(prompt);
            userInput = input.nextLine();
        }
        return userInput;
    }

    public static void showTable(){
        System.out.println(
        "+------------+-------------+---------------------------------------------------+--------------------+\n"+
        "| Character  |   Action    |                      Effect                       |   Counteraction    |\n"+
        "+------------+-------------+---------------------------------------------------+--------------------+\n"+
        "| All        | Income      | Collect 1 coin                                    | N/A                |\n"+
        "| All        | Foreign Aid | Collect 2 coins                                   | N/A                |\n"+
        "| All        | Coup        | Pay 7 coins to reduce a player's influence by 1   | N/A                |\n"+
        "| Duke       | Tax         | Collect 3 coins                                   | Blocks Foreign Aid |\n"+
        "| Assassin   | Assassinate | Pay 3 coins to reduce a player's influence by 1   | N/A                |\n"+
        "| Ambassador | Exchange    | Exchange cards in your hand for cards in the deck | Blocks Steal       |\n"+
        "| Captain    | Steal       | Take 2 coins from another player                  | Blocks Steal       |\n"+
        "| Contessa   | N/A         | N/A                                               | Blocks Assassinate |\n"+
        "+------------+-------------+---------------------------------------------------+--------------------+");
    }
}
