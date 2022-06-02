package Effects;

import Game.Player;

public abstract class Effect {
    private boolean targeted;
    
    /*
     * Determines if a player has sufficient coins to perform an action
     * and returns true or false.
    */
    public boolean paidCoins(Player player, int numCoins) {
        if (player.getCoins() >= numCoins) {
            player.spendCoins(numCoins);
            return true;
        }
        else {
            return false;
        }
    }
    
    /*
     * Executes the action and returns a boolean corresponding to success (true)
     * or failure (false).
     */
    public abstract boolean execute(Player targetPlayer, Player activePlayer);

    /*
     * Determines if an effect targets another player or targets active player
     */
    public boolean isTargeted() {
        return targeted;
    }
}
