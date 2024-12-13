package fr.rammex.juraforge.rune.customeffects;

import org.bukkit.entity.Player;

public class NoDamageFallEffect implements CustomEffect {
    @Override
    public void apply(Player player, int level) {
        player.setFallDistance(0);
    }

    @Override
    public void remove(Player player, int level) {
        player.setFallDistance(0);
    }
}
