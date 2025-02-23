package fr.rammex.juraforge.rune;

import fr.rammex.juraforge.Juraforge;
import fr.rammex.juraforge.rune.customeffects.CustomEffect;
import fr.rammex.juraforge.rune.customeffects.GroundEffect;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class RuneEffectListener implements Listener {
    private final RuneManager runeManager;
    private final Juraforge plugin;

    public RuneEffectListener(Juraforge plugin, RuneManager runeManager) {
        this.runeManager = runeManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            ItemStack currentItem = event.getCurrentItem();
            ItemStack cursorItem = event.getCursor();

            // Check if the player is equipping armor
            if (event.getSlotType() == InventoryType.SlotType.ARMOR && cursorItem != null && isArmor(cursorItem.getType())) {
                applyRuneEffects(player, cursorItem, "onEquip");
            }

            // Check if the player is unequipping armor
            if (event.getSlotType() == InventoryType.SlotType.ARMOR && currentItem != null && isArmor(currentItem.getType())) {
                removeRuneEffects(player, currentItem, "onEquip");
            }
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
                    effect.apply(player, rune.getLevel());
                }
            }
        }
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item != null) {
            Runes rune = runeManager.getRuneFromItem(item);
            if (rune != null) {
                for (CustomEffect effect : rune.getOnPlayerInteractEffects()) {
                    effect.apply(player, rune.getLevel());
                }
            }
        }
    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event){
        for (PotionEffect effect : event.getPotion().getEffects()) {
            if (effect.getType().equals(PotionEffectType.SLOWNESS) || effect.getType().equals(PotionEffectType.WEAKNESS) || effect.getType().equals(PotionEffectType.POISON)) {
                for (LivingEntity player : event.getAffectedEntities()) {
                    if(player instanceof Player && hasGroundEffectRune((Player) player)){
                        player.removePotionEffect(PotionEffectType.SLOWNESS);
                        player.removePotionEffect(PotionEffectType.WEAKNESS);
                        player.removePotionEffect(PotionEffectType.POISON);
                    }
                }
            }
        }
    }

    private void applyRuneEffects(Player player, ItemStack item, String effectType) {
        Runes rune = runeManager.getRuneFromItem(item);
        if (rune != null) {
            List<CustomEffect> effects = getEffectsByType(rune, effectType);
            for (CustomEffect effect : effects) {
                effect.apply(player, rune.getLevel());
            }
        } else {
        }
    }

    private void removeRuneEffects(Player player, ItemStack item, String effectType) {
        Runes rune = runeManager.getRuneFromItem(item);
        if (rune != null) {
            List<CustomEffect> effects = getEffectsByType(rune, effectType);
            for (CustomEffect effect : effects) {
                effect.remove(player, rune.getLevel());
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

    public boolean hasGroundEffectRune(Player player) {
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item != null) {
                Runes rune = runeManager.getRuneFromItem(item);
                if (rune != null) {
                    for (CustomEffect effect : rune.getOnEquipEffects()) {
                        if (effect instanceof GroundEffect) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isArmor(Material material) {
        switch (material) {
            case LEATHER_HELMET:
            case LEATHER_CHESTPLATE:
            case LEATHER_LEGGINGS:
            case LEATHER_BOOTS:
            case CHAINMAIL_HELMET:
            case CHAINMAIL_CHESTPLATE:
            case CHAINMAIL_LEGGINGS:
            case CHAINMAIL_BOOTS:
            case IRON_HELMET:
            case IRON_CHESTPLATE:
            case IRON_LEGGINGS:
            case IRON_BOOTS:
            case DIAMOND_HELMET:
            case DIAMOND_CHESTPLATE:
            case DIAMOND_LEGGINGS:
            case DIAMOND_BOOTS:
            case GOLDEN_HELMET:
            case GOLDEN_CHESTPLATE:
            case GOLDEN_LEGGINGS:
            case GOLDEN_BOOTS:
            case NETHERITE_HELMET:
            case NETHERITE_CHESTPLATE:
            case NETHERITE_LEGGINGS:
            case NETHERITE_BOOTS:
                return true;
            default:
                return false;
        }
    }
}