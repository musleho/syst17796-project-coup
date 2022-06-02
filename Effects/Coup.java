package Effects;

import Cards.Card;
import Game.Player;

public class Coup extends Effect {
    private boolean refutable = false;
    private boolean blockable = false;
    private boolean targeted = true;
    private int cost = 7;
    
    public void execute(Player targetPlayer){
        targetPlayer.loseInfluence();
    }

    public String toString(){
        return "has launched a coup!";
    }
}
