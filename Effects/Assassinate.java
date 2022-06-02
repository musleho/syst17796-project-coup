package Effects;

import Game.Player;

public class Assassinate extends Effect{
    private boolean refutable = true;
    private boolean blockable = true;
    private boolean targeted = true;
    private int cost = 3;

    public void execute(Player targetPlayer){
        targetPlayer.loseInfluence();
    }

    public String toString(){
        return "wants to perform an assassination!";
    }
}
