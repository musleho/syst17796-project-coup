package Effects;

import Cards.Card;
import Game.Player;

public class Coup extends Effect {
    private boolean targeted = true;
    
    public boolean execute(Player targetPlayer, Player activePlayer){
        if (paidCoins(activePlayer, 7))
            targetPlayer.loseInfluence();
        
        return paidCoins(activePlayer, 3);
    }
}
