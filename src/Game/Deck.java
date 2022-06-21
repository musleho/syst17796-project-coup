package src.Game;

import java.util.ArrayList;
import java.util.Collections;
import src.Exceptions.InvalidNameException;

public class Deck {
    private final ArrayList<Card> activeCards = new ArrayList<Card>();
    private final ArrayList<Card> discardPile = new ArrayList<Card>();
    
    public Deck() {
        buildDeck();
    }

    public void buildDeck() {
        for (String cardName : Card.validNames){
            for(int i = 0; i < 3; i++){
                try{activeCards.add(new Card(cardName));}
                catch(InvalidNameException e) {System.out.println(e.getMessage());} //We pull names from the validNames array so this should never trigger.
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(activeCards);
    }

    public void showCards() {
        for (Card card : activeCards){
            System.out.println(card.getName());
        }
    }

    public Card drawCard() {
        Card newCard = activeCards.get(0);
        activeCards.remove(0);
        return newCard;
    }

    public void returnCard(Card card) {
        activeCards.add(card);
        shuffle();
    }

    public void discard(Card card){
        discardPile.add(card);
    }

    public void inspectDiscard() {
        for(Card card : discardPile) {
            System.out.print(" " + card.getName());
        }
    }

    public void resetDeck() {
        activeCards.clear();
        discardPile.clear();
        buildDeck();
    }
}