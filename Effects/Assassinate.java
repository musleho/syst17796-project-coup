package Effects;

import Game.Player;

public class Assassinate extends Effect{
    
    public void execute(Player targetPlayer, Player activePlayer){
        if (paidCoins(activePlayer, 3))
            targetPlayer.loseInfluence();
        else System.out.println("You do not have enough coins to do this.");
    }
}
