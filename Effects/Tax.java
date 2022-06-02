package Effects;

import Game.Player;

public class Tax extends Effect {
    private boolean refutable = true;
    private boolean blockable = false;
    private boolean targeted = false;
    private int cost = 0;
    
    public void execute(Player targetPlayer){
        targetPlayer.addCoins(3);
    }
}
