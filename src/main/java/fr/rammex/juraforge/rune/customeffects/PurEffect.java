package fr.rammex.juraforge.rune.customeffects;

import fr.rammex.juraforge.utils.TimerListener;
import fr.rammex.juraforge.utils.TimerManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class PurEffect implements CustomEffect, TimerListener {

    private final Map<Player, Boolean> cooldowns = new HashMap<>();

    @Override
    public void apply(Player player, int level) {
        if (cooldowns.getOrDefault(player, false)) {
            player.sendMessage("L'effet est en rechargement.");
            return;
        }

        // Envoie un cercle de feu sur l'adversaire
        Location playerLocation = player.getLocation();
        for (Entity entity : player.getNearbyEntities(10, 10, 10)) {
            if (entity instanceof Player && entity != player) {
                Player target = (Player) entity;
                createFireCircle(target.getLocation(), 3);
                applyFireDamage(target, 3);
            }
        }

        TimerManager timerManager = new TimerManager();
        timerManager.start15SecondTimer(player, this);
        startCooldownTimer(player);
    }

    @Override
    public void remove(Player player, int level) {
        // Aucun effet à retirer
    }

    @Override
    public void onTimerFinished(Player player) {
        // Aucun effet à gérer à la fin du timer
    }

    private void startCooldownTimer(Player player) {
        cooldowns.put(player, true);
        Timer cooldownTimer = new Timer();
        cooldownTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                cooldowns.put(player, false);
                player.sendMessage("L'effet Pûr est prêt à être utilisé à nouveau.");
                cooldownTimer.cancel();
            }
        }, 300000); // 300000 millisecondes = 300 secondes
    }

    private void createFireCircle(Location center, int radius) {
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                Location loc = center.clone().add(x, 0, z);
                if (loc.distance(center) <= radius) {
                    loc.getBlock().setType(org.bukkit.Material.FIRE);
                }
            }
        }
    }

    private void applyFireDamage(Player target, int duration) {
        new BukkitRunnable() {
            int seconds = 0;

            @Override
            public void run() {
                if (seconds >= duration) {
                    cancel();
                    return;
                }
                target.damage(8);
                seconds++;
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("juraforge"), 0, 20); // 20 ticks = 1 second
    }
}