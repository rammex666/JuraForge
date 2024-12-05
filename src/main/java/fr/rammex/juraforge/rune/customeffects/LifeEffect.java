package fr.rammex.juraforge.rune.customeffects;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LifeEffect implements CustomEffect {
    @Override
    public void apply(Player player, int level) {
        player.setHealth(player.getHealth() + level + 4);
        if(level == 5) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 1, true, false));
        }
    }

    @Override
    public void remove(Player player, int level) {
        player.setHealth(player.getHealth() - level - 4);
        if(level == 5){
            player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
        }
    }
}
