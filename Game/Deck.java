package Game;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> activeCards = new ArrayList<Card>();
    private ArrayList<Card> discardPile = new ArrayList<Card>();

    
    public Deck() {
        buildDeck();
    }

    public void buildDeck() {
        for (String cardName : Card.validNames){
            for(int i = 0; i < 3; i++){
                try{activeCards.add(new Card(cardName));}
                catch(Exception e) {System.out.println(e.getMessage());} //We pull names from the validNames array so this should never trigger.
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

    public void drawCard(Player player) {
        Card newCard = activeCards.get(0);
        activeCards.remove(0);
        player.getHand().add(newCard);
    }

    public void discard(Card card){
        //put a card in the discard pile
    }

    public void inspectDiscard() {
        for(Card card : discardPile) {
            System.out.println(card.getName());
        }
    }

    /**@EndOfRoundMethod*/
    public void resetDeck() {
        activeCards.clear();
        discardPile.clear();
        buildDeck();
    }
}
