package Effects;

import Game.Player;

public class Assassinate extends Effect{

    public Assassinate() {
        super("assassinate", true, true, true, 3);
    }
    
    public void execute(Player targetPlayer){
        targetPlayer.loseInfluence();
    }

    public String toString(){
        return "wants to perform an assassination!";
    }
}
