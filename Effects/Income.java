package Effects;

import Game.Player;

public class Income extends Effect {
    private String name = "income";
    private boolean refutable = false;
    private boolean blockable = false;
    private boolean targeted = false;
    private int cost = 0;
    
    public void execute(Player player){
        player.addCoins(1);
    }
    

    public String toString(){
        return "has collected income!";
    }
}
