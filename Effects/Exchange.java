package Effects;

import Game.*;

public class Exchange extends Effect {
    private boolean refutable = true;
    private boolean blockable = false;
    private boolean targeted = false;
    private int cost = 0;
    
    public void execute(Player targetPlayer) {
        //do something
    }

    public String toString(){
        return "wants to perform an exchange!";
    }
}
