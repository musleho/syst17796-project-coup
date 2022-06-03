package Game;

import java.util.ArrayList;
import java.util.Scanner;
import Effects.*;

public class Player implements Comparable<Player>{
    private String name;
    private int number; //keeps track of this player's index in the list of all registered players
    private ArrayList<Card> hand = new ArrayList<Card>();
    private int coins;
    private int influence;
    private boolean alive;
    private int score;

    public Player(String name, int number) {
        this.name = name;
        this.number = number;
        this.score = 0;
        resetPlayer();
    }

    //Accessors
    public String getName() {return this.name;}

    public int getNumber() {return this.number;}
    
    public ArrayList<Card> getHand() {
        return this.hand;
    }
    
    public int getCoins() {
        return this.coins;
    }

    public int getScore() {return this.score;}
    
    public void addCoins(int num) {
        coins += num;
    }

    public void resetPlayer() {
        coins = 2;
        influence = 2;
        alive = true;
        hand.clear();
    }

    public void spendCoins(int num) {
        coins -= num; //should add exception handling so cannot spend more coins than available
    }

    public Effect declareEffect(String effect){
        System.out.println();
        return Game.EFFECTS.get(effect);
        //determine if effect is bluff or not
        //declares effect without executing
    }

    public void challenge(String playerName){
        //Find player by playerName
        //Challenge player
    }

    public void declareCounteract(Effect effect){
        //determine if countraction is bluff or not
        //declares counteraction to a given effect
    }

    public void loseInfluence() {
        int cardIndex = 0;
        
        //gets player choice on which card to reveal
        if(influence == 2) {
            Scanner input = new Scanner(System.in);
            System.out.println("Please choose a card to lose.");
            System.out.print("Type '1' to lose your " + hand.get(0).getName() + "or type '2' to lose your " + hand.get(1) + ":");

        }
        hand.remove(cardIndex); //remove a chosen card from the player's hand
        influence--; //reduce player influence by 1
        if (influence < 1) { //if player influence falls to 0 or less, they are no longer active
            alive = false;
        }
    }

    public int compareTo(Player player) {
        if(player.getScore() > this.score) return -1;
        else if (player.getScore() < this.score) return 1;
        else return 0;
    }

    public String toString() {
        String cards = "";
        if (hand.size() > 0) {
            for (Card card : hand){
                cards += " " + card.getName();
            }
        }
        return "Player Name: " + this.name + "\n" +
               "Player Number: " + this.number + "\n" +
               "Current Coins: " + this.coins + "\n" +
               "Current Influence: " + this.influence + "\n" +
               "Is active: " + this.alive + "\n" +
               "Current hand:" + cards + "\n" +
               "Current score: " + this.score;
    }

    public boolean isAlive() {return this.alive;}


}
