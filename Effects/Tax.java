package Effects;

import Game.Player;

public class Tax extends Effect {
    private String name = "tax";
    private boolean refutable = true;
    private boolean blockable = false;
    private boolean targeted = false;
    private int cost = 0;
    
    public void execute(Player targetPlayer){
        targetPlayer.addCoins(3);
    }

    public String toString(){
        return "wants to collect tax!";
    }
}
