package fr.rammex.juraforge.rune;

import fr.rammex.juraforge.rune.customeffects.CustomEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

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
            removeRuneEffects(player, oldItem, "onHoldItem");
        }

        if (newItem != null) {
            applyRuneEffects(player, newItem, "onHoldItem");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item != null) {
                removeRuneEffects(player, item, "onEquip");
            }
        }
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        if (heldItem != null) {
            removeRuneEffects(player, heldItem, "onHoldItem");
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item != null) {
            Runes rune = runeManager.getRuneFromItem(item);
            if (rune != null) {
                for (CustomEffect effect : rune.getOnPlayerInteractEffects()) {
                    effect.apply(player);
                }
            }
        }
    }

    private void applyRuneEffects(Player player, ItemStack item, String effectType) {
        Runes rune = runeManager.getRuneFromItem(item);
        if (rune != null) {
            List<CustomEffect> effects = getEffectsByType(rune, effectType);
            for (CustomEffect effect : effects) {
                effect.apply(player);
            }
        }
    }

    private void removeRuneEffects(Player player, ItemStack item, String effectType) {
        Runes rune = runeManager.getRuneFromItem(item);
        if (rune != null) {
            List<CustomEffect> effects = getEffectsByType(rune, effectType);
            for (CustomEffect effect : effects) {
                effect.remove(player);
            }
        }
    }

    private List<CustomEffect> getEffectsByType(Runes rune, String effectType) {
        switch (effectType) {
            case "onHoldItem":
                return rune.getOnHoldItemEffects();
            case "onEquip":
                return rune.getOnEquipEffects();
            case "onPlayerInteract":
                return rune.getOnPlayerInteractEffects();
            default:
                return new ArrayList<>();
        }
    }
}