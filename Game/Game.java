package Game;

import Effects.*;
import Exceptions.*;
import java.util.*;

// A static helper class to the App.java application.
// This class effectively handles the implementation of the game logic.
// The Tools class handles generic functions like getting valid inputs and printing messages
// while the App class runs and handles the flow of gameplay.
public abstract class Game {
    public static final Hashtable<String,Effect> EFFECTS = new Hashtable<String,Effect>(8);
    public static final ArrayList<Player> ALL_PLAYERS = new ArrayList<Player>();
    public static final ArrayList<Player> PLAYERS = new ArrayList<Player>();

    public static void buildEffects(Deck deck){
        EFFECTS.put("income", new Income());
        EFFECTS.put("foreign aid", new ForeignAid());
        EFFECTS.put("coup", new Coup());
        EFFECTS.put("tax", new Tax());
        EFFECTS.put("assassinate", new Assassinate());
        EFFECTS.put("steal", new Steal());
        EFFECTS.put("income", new Income());
        EFFECTS.put("exchange", new Exchange(deck));
    }

    public static void showCards(Player player) {
        if (player.getHand().size() > 0) {
            Tools.showOnlyMessage("Showing cards for " + player.getName() + ". Press enter if this is you.", 0);
            Tools.input.nextLine();
            System.out.print("Revealing cards in ");
            for(int i = 3; i > 0; i--){
                Tools.showMessage(i + "... ",1);
            }
            for(Card card : player.getHand()) {
                System.out.println();
                System.out.println(card);
            }
            Tools.showMessage("\n", 1);
            System.out.println("Press enter to continue.");
            Tools.input.nextLine();
            Tools.clearConsole();
        }

        else Tools.showOnlyMessage(player.getName() + " has no cards left.", 0);
    }

    public static boolean checkEffectBluff(Player player, String effect) {
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
        String playerName = Tools.input.next();
        ALL_PLAYERS.add(new Player(playerName, currentIndex + 1));
    }

    public static void resetActivePlayers() {
        PLAYERS.clear();
        PLAYERS.addAll(ALL_PLAYERS);
        for (Player player : Game.PLAYERS) {
            player.resetPlayer();
        }
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
}
