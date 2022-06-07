package Game;

import Effects.*;
import Exceptions.*;
import java.util.*;

// A static helper class to the App.java application.
// This class effectively handles the implementation of the game logic.
// The Tools class handles generic functions like getting valid inputs and printing messages
// while the App class runs and handles the flow of gameplay.
public abstract class Game {
    public static final Deck deck = new Deck();
    public static final Effect[] EFFECTS = {new Income(), new ForeignAid(), new Coup(deck), new Tax(), new Assassinate(deck), new Exchange(deck), new Steal()};
    public static final ArrayList<Player> ALL_PLAYERS = new ArrayList<Player>();
    public static final ArrayList<Player> PLAYERS = new ArrayList<Player>();

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

    public static int checkEffectBluff(Player player, String effect) {
        if (effect.equals("income") || effect.equals("foreign aid") || effect.equals("coup")) return 2; //if the effect isn't refutable
        else {
            for (int i = 0; i < player.getHand().size(); i++){
                if (player.getHand().get(i).getEffect().equals(effect)) return i; //if player is not bluffing, index of card to be swapped
            }
            return -1; //if they are bluffing
        }
    }

    public static int checkCounteractBluff(Player player, String counteract) {
        for (int i = 0; i < player.getHand().size(); i++){
            if(player.getHand().get(i).getCounteraction().equals(counteract)) return i; //if blocker is not bluffing, 
        }
        return -1; //if blocker is bluffing
    }

    public static Card findCardByName(Player player, String cardName) throws InvalidNameException{
        for (Card card : player.getHand()) {
            if (card.getName().equals(cardName)) return card;
        }
        throw new InvalidNameException();
    }

    public static void registerPlayer(){
        int currentIndex = ALL_PLAYERS.size();
        System.out.print("Please enter the name for Player " + (currentIndex + 1) + ". ");
        System.out.print("Note - Player names cannot contain spaces: ");
        String playerName = Tools.input.next();
        ALL_PLAYERS.add(new Player(playerName, currentIndex + 1));
    }

    public static void registerPlayer(String name){ //for debugging, to skip the whole registration process
        int currentIndex = ALL_PLAYERS.size();
        ALL_PLAYERS.add(new Player(name, currentIndex + 1));
    }

    public static Player[] findWaitingPlayers(Player activePlayer) {
        Player[] waitingPlayers = new Player[PLAYERS.size() - 1];
        int otherPlayersIndex = 0;
        for (Player player : Game.PLAYERS) {
            if (!player.equals(activePlayer)) {
                waitingPlayers[otherPlayersIndex++] = player;
            }
        }
        return waitingPlayers;
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

    public static Player findTurnPlayer(int turnNum, int startPlayerNum) {
        int turnPlayerNum = (startPlayerNum + turnNum) % PLAYERS.size();
        return PLAYERS.get(turnPlayerNum);
    }

    public static Player findLivingPlayer(String name) throws PlayerNotFoundException{
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
                PLAYERS.remove(i);
            }
        }
    }

    public static boolean [] processChallenges(Player[] otherPlayers, Player activePlayer, boolean apIsBluffing, int cardFlag, Deck deck) {
        String[] yN = {"y", "n"};
        boolean[] challenge = {false, false}; //first element stores whether a challenge was issued, second one whether it was successful
        for (Player player : otherPlayers) {
            String willChallenge = Tools.promptInput(player.getName() + ", would you like to challenge? [y/n]: ", 
                                            "Sorry, I didn't understand. Would you like to challenge? [y/n]: ", yN).toLowerCase();
            if (willChallenge.equals("y")) {
                challenge[0] = true; //at this point, a challenge was issued, so this is true
                challenge[1] = player.challenge(activePlayer, apIsBluffing, cardFlag, deck); //this stores the result of the challenge.
                break;
            }
        }
        return challenge;
    }
}
