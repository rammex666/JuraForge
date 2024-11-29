package fr.rammex.juraforge.rune;

import fr.rammex.juraforge.runes.RuneManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class RuneEffectListener implements Listener {
    private final RuneManager runeManager;

    public RuneEffectListener(RuneManager runeManager) {
        this.runeManager = runeManager;
    }

    @EventHandler
    public void onItemHeldChange(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack newItem = player.getInventory().getItem(event.getNewSlot());
        ItemStack oldItem = player.getInventory().getItem(event.getPreviousSlot());

        if (oldItem != null) {
            removeRuneEffects(player, oldItem);
        }

        if (newItem != null) {
            applyRuneEffects(player, newItem);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item != null) {
                removeRuneEffects(player, item);
            }
        }
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        if (heldItem != null) {
            removeRuneEffects(player, heldItem);
        }
    }

    private void applyRuneEffects(Player player, ItemStack item) {
        Runes rune = runeManager.getRuneFromItem(item);
        if (rune != null) {
            for (PotionEffect effect : rune.getEffects()) {
                player.addPotionEffect(effect);
            }
        }
    }

    private void removeRuneEffects(Player player, ItemStack item) {
        Runes rune = runeManager.getRuneFromItem(item);
        if (rune != null) {
            for (PotionEffect effect : rune.getEffects()) {
                player.removePotionEffect(effect.getType());
            }
        }
    }
}