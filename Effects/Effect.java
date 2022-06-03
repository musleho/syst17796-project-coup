package Effects;

import Game.Player;

public abstract class Effect {
    /**
     * Refutable: A given effect can be challenged (true)
     * Blockable: A given effect has a blocking counteraction (true)
     * Targeted: A given effect targets another player (true)
     * Cost: Number of coins needed to execute the action
     */
    private String name;
    private boolean refutable,blockable,targeted;
    private int cost;
    
    public abstract void execute(Player targetPlayer);

    //Accessors
    public String getName() {return name;}

    public boolean isRefutable() {return refutable;}

    public boolean isBlockable() {return blockable;}

    public boolean isTargeted() {return targeted;}

    public int getCost() {return cost;}

    //Generic methods
    public abstract String toString();

    public boolean equals(Effect eff){
        return this.name.equals(eff.getName());
    }
}
