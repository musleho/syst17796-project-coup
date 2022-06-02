package Game;

import java.util.ArrayList;
import Effects.*;
import Cards.*;

public class Player {
    private String name;
    private ArrayList<Card> Hand;
    private int coins;
    private int influence;

    public Player(String name) {
        Card card1 = Deck.drawCard();
        Card card2 = Deck.drawCard()
    }
    
    public void declareEffect(Effect effect){
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
        this.influence--; //may change
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
}
