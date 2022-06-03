package Game;

import java.util.ArrayList;
import java.util.Scanner;
import Effects.*;
import Cards.*;

public class Player {
    private String name;
    private ArrayList<Card> hand = new ArrayList<Card>();
    private int coins;
    private int influence;
    private boolean alive;
    private int score;

    public Player(String name) {
        this.name = name;
        this.coins = 2;
        this.influence = 2;
        this.alive = true;
        this.score = 0;
    }

    //Accessors
    public String getName() {return this.name;}
    
    public ArrayList<Card> getHand() {
        return this.hand;
    }
    
    public int getCoins() {
        return this.coins;
    }
    
    public void addCoins(int num) {
        coins += num;
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

    public boolean isAlive() {return this.alive;}


}
