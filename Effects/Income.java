package Effects;

import Game.Player;

public class Income implements Effect {
    public void execute(Player player){
        player.addCoins(1);
    }
}
