package fr.rammex.juraforge.rune;

import fr.rammex.juraforge.config.menu.ConfigRunesMainMenu;
import fr.rammex.juraforge.rune.RuneManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class RuneMenu implements Listener {
    private final JavaPlugin plugin;
    private final RuneManager runeManager;
    private static final int ITEM_SLOT = 10;
    private static final int RUNE_SLOT = 16;
    private static final int CONFIG_SLOT = 26;

    public RuneMenu(JavaPlugin plugin, RuneManager runeManager) {
        this.plugin = plugin;
        this.runeManager = runeManager;
    }

    public static void openMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, "Apply Rune");

        ItemStack greenItem = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta greenMeta = greenItem.getItemMeta();
        greenMeta.setDisplayName(" ");
        greenItem.setItemMeta(greenMeta);

        ItemStack grayItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta grayMeta = grayItem.getItemMeta();
        grayMeta.setDisplayName(" ");
        grayItem.setItemMeta(grayMeta);

        for (int i = 0; i < inventory.getSize(); i++) {
            if (i == ITEM_SLOT || i == RUNE_SLOT) {
                continue;
            }
            if (i == CONFIG_SLOT && player.hasPermission("juraforge.config")){
                ItemStack configItem = new ItemStack(Material.REDSTONE);
                ItemMeta configMeta = configItem.getItemMeta();
                configMeta.setDisplayName("§c§lConfig");
                configMeta.addEnchant(Enchantment.MENDING, 1, true);
                configItem.setItemMeta(configMeta);
                inventory.setItem(i, configItem);
                continue;
            }
            inventory.setItem(i, (i % 2 == 0) ? greenItem : grayItem);
        }

        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Apply Rune")) {
            ItemStack clickedItem = event.getCurrentItem();
            ItemStack cursorItem = event.getCursor();
            Inventory inventory = event.getInventory();

            if (event.getSlot() == ITEM_SLOT) {
                event.setCancelled(true);
                if (cursorItem != null && cursorItem.getType() != Material.AIR) {
                    inventory.setItem(ITEM_SLOT, cursorItem.clone());
                    event.getWhoClicked().setItemOnCursor(null);
                } else if (clickedItem != null && clickedItem.getType() != Material.AIR) {
                    event.getWhoClicked().setItemOnCursor(clickedItem.clone());
                    inventory.setItem(ITEM_SLOT, null);
                }
            } else if (event.getSlot() == RUNE_SLOT) {
                event.setCancelled(true);
                ItemStack itemStack = inventory.getItem(ITEM_SLOT);
                if (itemStack != null && itemStack.getType() != Material.AIR) {
                    if (cursorItem != null && cursorItem.getType() != Material.AIR) {
                        ItemMeta meta = cursorItem.getItemMeta();
                        if (meta != null && meta.hasLore()) {
                            List<String> lore = meta.getLore();
                            if (lore != null && !lore.isEmpty()) {
                                String levelLine = lore.get(0);
                                int runeLevel = Integer.parseInt(levelLine.split(": ")[1]);
                                String runeId = RuneManager.getRuneIdByNameAndLevel(meta.getDisplayName(), runeLevel);
                                Runes rune = RuneManager.getRune(runeId);
                                if (rune != null && rune.getLevel() == runeLevel && runeManager.canApplyRune(itemStack, rune)) {
                                    Runes existingRune = runeManager.getRuneFromItem(itemStack);
                                    if (existingRune != null && existingRune.getId().equals(rune.getId()) && existingRune.getLevel() == rune.getLevel() && existingRune.getIsUpgradeable()) {
                                        runeManager.upgradeRune(itemStack, existingRune);
                                    } else {
                                        runeManager.applyRune(itemStack, rune);
                                    }
                                    event.getWhoClicked().setItemOnCursor(null);
                                }
                            }
                        }
                    }
                }
            } else if (clickedItem.getType() == Material.REDSTONE && clickedItem.getItemMeta().getDisplayName() == ChatColor.translateAlternateColorCodes('&',"&c&lConfig")) {
                ConfigRunesMainMenu.getConfigRunesMenu((Player) event.getWhoClicked());
            } else if (clickedItem != null && (clickedItem.getType() == Material.GREEN_STAINED_GLASS_PANE || clickedItem.getType() == Material.GRAY_STAINED_GLASS_PANE)) {
                event.setCancelled(true);
            }
        }
    }
}