package Game;

import Effects.*;
import Cards.*;
import java.util.Hashtable;

// A static helper class to Game.java application with the main method.
// The purpose of this class is primarily logic-handling
public class Brain {
    public static final Hashtable<String,Effect> EFFECTS = new Hashtable<String,Effect>(7);

    public static void buildEffects(){
        EFFECTS.put("Income", new Income());
        EFFECTS.put("Foreign Aid", new ForeignAid());
        EFFECTS.put("Coup", new Coup());
        EFFECTS.put("Tax", new Tax());
        EFFECTS.put("Assassinate", new Assassinate());
        EFFECTS.put("Steal", new Steal());
        EFFECTS.put("Income", new Income());
    }

    public static boolean isBluffing(Player player, Effect effect) {
        for (Card card : player.getHand()){
            
        }
    }
}
