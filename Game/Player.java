package Game;

import java.util.ArrayList;
import Effects.*;
import Exceptions.InsufficientCoinsException;

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

    public void increaseScore() {score++;}
    
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

    public void drawCard(Deck deck){
        this.hand.add(deck.drawCard());
    }

    public void swapCards(Deck deck, int cardIndex) {
        if (cardIndex >= 0 && cardIndex < hand.size()) {
            Card oldCard = hand.get(cardIndex);
            deck.returnCard(oldCard);
            hand.remove(cardIndex);
            deck.shuffle();
            drawCard(deck);
            Tools.showMessage("\n" + this.name + " has swapped cards. Preparing to show new hand...\n", 2);
            Game.showCards(this); //should be replaced with something a bit more elegant.
        }
    }

    public Effect declareEffect(int effect) throws InsufficientCoinsException{
        Effect eff = Game.EFFECTS[effect - 1];
        if (this.coins >= eff.getCost()) 
            return Game.EFFECTS[effect - 1];
        else throw new InsufficientCoinsException();
        //determine if effect is bluff or not
        //declares effect without executing
    }

    public boolean challenge(Player targetPlayer, boolean isBluffing, int cardIndex, Deck deck){
        String loser = name;
        if (isBluffing) {
            loser = targetPlayer.getName();
            Tools.showMessage(loser + " was bluffing! ", 1.25);
            Tools.showMessage(loser + " loses influence.", 1);
            targetPlayer.loseInfluence(deck);
            return true; //challenge succeeded
        }
        else {
            Tools.showMessage(targetPlayer.getName() + " wasn't bluffing!\n", 1.25);
            Tools.showMessage(loser + " loses influence.\n", 1);
            loseInfluence(deck);
            targetPlayer.swapCards(deck, cardIndex);
            return false; //challenge failed
        }
    }

    public String counteract(Effect effect){
        return "block " + effect.getName();
    }

    public void loseInfluence(Deck deck) {
        if (this.isAlive()) { //don't do anything if the player is already dead.
            int cardIndex = 0;
            
            //gets player choice on which card to reveal if they have more than one card.
            if(influence == 2) {     
                System.out.println(name + ", please choose a card to lose.");
                String prompt = "Type '1' to lose your " + hand.get(0).getName() + " or type '2' to lose your " + hand.get(1).getName() + ": ";
                String[] validInputs = {"1", "2"};
                cardIndex = Integer.parseInt(Tools.promptInput(prompt, "Sorry, I didn't understand that. " + prompt, validInputs)) - 1;
            }
            
            Tools.showOnlyMessage(name + " has discarded their " + hand.get(cardIndex).getName() + "!\n", 3.2);
            influence--; //reduce player influence by 1
            deck.discard(hand.get(cardIndex));
            hand.remove(cardIndex); //remove a chosen card from the player's hand
            Tools.showMessage(name + " has " + influence + " influence left!\n", 1.75);
            
            if (influence < 1) { //if player influence falls to 0 or less, they are no longer active
                alive = false;
                Tools.showMessage(name + " is out of the game!", 1.1);
            }

            System.out.print("\nPress enter to continue.");
            Tools.input.nextLine();
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

    public boolean equals(Player player){
        return this.name == player.name;
    }

    public boolean isAlive() {return this.alive;}


}
