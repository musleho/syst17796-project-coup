package Effects;

import Game.Player;

public class Coup extends Effect {
    
    public Coup() {
        super("coup", false, false, true, 7);
    }

    public void execute(Player targetPlayer){
        targetPlayer.loseInfluence();
    }

    public String toString(){
        return "has launched a coup!";
    }
}
