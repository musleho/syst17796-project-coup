package Effects;

import Game.Player;
import Game.Deck;

public class Assassinate extends Effect{
    private Deck deck;

    public Assassinate(Deck deck) {
        super("assassinate", true, true, true, 3);
        this.deck = deck;
    }
    
    public void execute(Player targetPlayer){
        targetPlayer.loseInfluence(deck);
    }

    public String toString(){
        return "wants to perform an assassination!";
    }
}
