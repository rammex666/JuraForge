package fr.rammex.juraforge.rune.customeffects;

import fr.rammex.juraforge.utils.TimerListener;
import fr.rammex.juraforge.utils.TimerManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class AspisEffect implements CustomEffect, TimerListener {

    private final Map<Player, Boolean> cooldowns = new HashMap<>();

    @Override
    public void apply(Player player, int level) {
        if (cooldowns.getOrDefault(player, false)) {
            player.sendMessage("L'effet est en rechargement.");
            return;
        }
        player.setInvulnerable(true);
        TimerManager timerManager = new TimerManager();
        timerManager.start15SecondTimer(player, this);
        startCooldownTimer(player);
    }

    @Override
    public void remove(Player player, int level) {
        player.setInvulnerable(false);
    }

    @Override
    public void onTimerFinished(Player player) {
        player.setInvulnerable(false);
    }

    private void startCooldownTimer(Player player) {
        cooldowns.put(player, true);
        Timer cooldownTimer = new Timer();
        cooldownTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                cooldowns.put(player, false);
                player.sendMessage("L'effet Aspis est prêt à être utilisé à nouveau.");
                cooldownTimer.cancel();
            }
        }, 300000); // 300000 millisecondes = 300 secondes
    }
}