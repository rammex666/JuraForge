package fr.rammex.juraforge.rune.customeffects;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static fr.rammex.juraforge.rune.RuneSetup.plugin;

public class VascelEffect implements CustomEffect {
    private static final int MAX_DISTANCE = plugin.getConfig().getInt("effects.Vascel.max_distance");
    private static final long COOLDOWN_TIME = plugin.getConfig().getInt("effects.Vascel.coldown")*1000;
    private final Map<UUID, Long> lastUseTime = new HashMap<>();
    private Location targetLocation;

    public VascelEffect() {
    }




    @Override
    public void apply(Player player, int level) {
        UUID playerId = player.getUniqueId();
        long currentTime = System.currentTimeMillis();
        targetLocation = player.getTargetBlockExact(MAX_DISTANCE).getLocation();

        if(targetLocation == null){
            return;
        }

        if (lastUseTime.containsKey(playerId)) {
            long lastUse = lastUseTime.get(playerId);
            if (currentTime - lastUse < COOLDOWN_TIME) {
                player.sendMessage("Vous devez attendre avant de pouvoir utiliser cette rune à nouveau !");
                return;
            }
        }

        if (player.getLocation().distance(targetLocation) > MAX_DISTANCE) {
            player.sendMessage("Vous ne pouvez pas vous téléporter aussi loin !");
        } else {
            player.teleport(targetLocation);
            lastUseTime.put(playerId, currentTime);
        }
    }

    @Override
    public void remove(Player player, int level) {
    }

}