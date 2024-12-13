package fr.rammex.juraforge.rune.customeffects;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WatterEffect implements CustomEffect{
    @Override
    public void apply(Player player, int level) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 1000000, 1));
    }

    @Override
    public void remove(Player player, int level) {
        player.removePotionEffect(PotionEffectType.WATER_BREATHING);
    }

}
