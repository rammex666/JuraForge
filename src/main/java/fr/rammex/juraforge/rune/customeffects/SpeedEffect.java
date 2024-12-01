package fr.rammex.juraforge.rune.customeffects;

import org.bukkit.entity.Player;

public class SpeedEffect implements CustomEffect {

    @Override
    public void apply(Player player, int level) {
        float speed = 0.2f + (0.1f * level);
        player.setWalkSpeed(speed);
    }

    @Override
    public void remove(Player player, int level) {
        player.setWalkSpeed(0.2f);
    }
}
