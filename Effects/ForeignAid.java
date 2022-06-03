package Effects;

import Game.Player;

public class ForeignAid extends Effect {
    private String name = "foreign aid";
    private boolean refutable = false;
    private boolean blockable = true;
    private boolean targeted = false;
    private int cost = 0;
    
    public void execute(Player player) {
        player.addCoins(2);
    }
    

    public String toString(){
        return "wants to collect foreign aid!";
    }
}