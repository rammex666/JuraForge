package fr.rammex.juraforge.rune.customeffects;

import fr.rammex.juraforge.Juraforge;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class GroundEffect implements CustomEffect {
    private BukkitRunnable task;
    Juraforge plugin;

    public GroundEffect(Juraforge plugin) {
        this.plugin = plugin;
    }

    @Override
    public void apply(Player player, int level) {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                player.removePotionEffect(PotionEffectType.WEAKNESS);
                player.removePotionEffect(PotionEffectType.SLOWNESS);
                player.removePotionEffect(PotionEffectType.POISON);
            }
        };
        task.runTaskTimer(plugin, 0, 20L); // Run every second
    }

    @Override
    public void remove(Player player, int level) {
        if (task != null) {
            task.cancel();
        }
    }
}
