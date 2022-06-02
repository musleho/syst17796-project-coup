package Effects;

import Game.Player;

public class Tax extends Effect {
    private boolean targeted = false;
    
    public boolean execute(Player targetPlayer, Player activePlayer){
        activePlayer.addCoins(3);
        return true;
    }
}
