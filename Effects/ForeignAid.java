package Effects;

import Game.Player;

public class ForeignAid implements Effect {
    public void execute(Player player) {
        player.addCoins(2);
    }
}