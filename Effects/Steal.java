package Effects;

import Game.Player;

public class Steal extends Effect {
    /**Steals two coins from another player */
    private String name = "steal";
    private boolean refutable = true;
    private boolean blockable = true;
    private boolean targeted = true;
    private int cost = -2; // Sort of "hacky" way to use the generic player.spendCoins() call to add two coins to the stealing player's hand.
    
    public void execute(Player targetPlayer){
        targetPlayer.spendCoins(2);
    }

    public String toString(){
        return "wants to steal!";
    }
}
