package fr.rammex.juraforge.rune.customeffects;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FireResistanceEffect implements CustomEffect {
    @Override
    public void apply(Player player, int level) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1000000, 1));
    }

    @Override
    public void remove(Player player, int level) {
        player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
    }
}
