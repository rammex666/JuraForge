package fr.rammex.juraforge.rune.customeffects;

import fr.rammex.juraforge.utils.TimerListener;
import fr.rammex.juraforge.utils.TimerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class AitherEffect implements CustomEffect, TimerListener, Listener {

    private final Map<Player, Boolean> cooldowns = new HashMap<>();
    private final Map<Player, Integer> arrowCount = new HashMap<>();

    @Override
    public void apply(Player player, int level) {
        if (cooldowns.getOrDefault(player, false)) {
            player.sendMessage("L'effet est en rechargement.");
            return;
        }

        arrowCount.put(player, 0);
        player.sendMessage("Les 5 prochaines flèches feront le double des dégâts.");

        TimerManager timerManager = new TimerManager();
        timerManager.start15SecondTimer(player, this);
        startCooldownTimer(player);
    }

    @Override
    public void remove(Player player, int level) {
        arrowCount.remove(player);
    }

    @Override
    public void onTimerFinished(Player player) {
        arrowCount.remove(player);
    }

    private void startCooldownTimer(Player player) {
        cooldowns.put(player, true);
        Timer cooldownTimer = new Timer();
        cooldownTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                cooldowns.put(player, false);
                player.sendMessage("L'effet Aither est prêt à être utilisé à nouveau.");
                cooldownTimer.cancel();
            }
        }, 300000); // 300000 millisecondes = 300 secondes
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (arrowCount.containsKey(player)) {
                int count = arrowCount.get(player);
                if (count < 5) {
                    arrowCount.put(player, count + 1);
                } else {
                    arrowCount.remove(player);
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof org.bukkit.entity.Arrow) {
            org.bukkit.entity.Arrow arrow = (org.bukkit.entity.Arrow) event.getDamager();
            if (arrow.getShooter() instanceof Player) {
                Player player = (Player) arrow.getShooter();
                if (arrowCount.containsKey(player) && arrowCount.get(player) <= 5) {
                    event.setDamage(event.getDamage() * 2);
                }
            }
        }
    }
}