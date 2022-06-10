package Effects;

import Game.Player;
import Game.Deck;

public class Coup extends Effect {
    private Deck deck;

    public Coup(Deck deck) {
        super("coup", false, false, true, 7);
        this.deck = deck;
    }

    public void execute(Player targetPlayer){
        targetPlayer.loseInfluence(deck);
    }

    public String toString(){
        return "has launched a coup!";
    }
}
