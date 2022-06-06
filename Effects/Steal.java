package Effects;

import Game.Player;

public class Steal extends Effect {
    /**Steals two coins from another player */

    public Steal() {
        super("steal", true, true, true, -2); // Sort of "hacky" way to add two coins to the player's bank
    }

    public void execute(Player targetPlayer){
        int coinsTaken = Math.min(2, targetPlayer.getCoins());
        targetPlayer.spendCoins(coinsTaken);
        setCost(-coinsTaken);
    }

    public String toString(){
        return "wants to steal!";
    }
}
