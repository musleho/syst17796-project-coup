package Effects;

import Game.Player;

public abstract class Effect {
    private boolean refutable,blockable,targeted;
    private int cost;
    
    // public boolean paidCoins(Player player, int numCoins) {
    //     if (player.getCoins() >= numCoins) {
    //         player.spendCoins(numCoins);
    //         return true;
    //     }
    //     else {
    //         return false;
    //     }
    // } 
    // removed as better construct would be to have cost as field and check in game logic
    // otherwise would need to actually get to execute step before telling player to pick 
    // a different action
    
    /*
     * Executes the action of that effect object
     */
    public abstract void execute(Player targetPlayer);

    /*
     * Determines if an effect targets another player or targets active player
     */
    public boolean isTargeted() {
        return targeted;
    }

    public boolean isRefutable() {
        return refutable;
    }

    public boolean isBlockable() {
        return blockable;
    }

    public int getCost() {
        return cost;
    }

    public abstract String toString();
}
