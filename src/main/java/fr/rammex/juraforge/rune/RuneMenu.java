package fr.rammex.juraforge.rune;

import fr.rammex.juraforge.rune.RuneManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class RuneMenu {
    private final JavaPlugin plugin;
    private final RuneManager runeManager;
    private static final int ITEM_SLOT = 10;
    private static final int RUNE_SLOT = 16;

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
            inventory.setItem(i, (i % 2 == 0) ? greenItem : grayItem);
        }

        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Apply Rune")) {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            ItemStack cursorItem = event.getCursor();
            Inventory inventory = event.getInventory();

            if (event.getSlot() == ITEM_SLOT) {
                if (cursorItem != null && cursorItem.getType() != Material.AIR) {
                    inventory.setItem(ITEM_SLOT, cursorItem.clone());
                    event.getWhoClicked().setItemOnCursor(null);
                } else if (clickedItem != null && clickedItem.getType() != Material.AIR) {
                    event.getWhoClicked().setItemOnCursor(clickedItem.clone());
                    inventory.setItem(ITEM_SLOT, null);
                }
            } else if (event.getSlot() == RUNE_SLOT) {
                ItemStack itemStack = inventory.getItem(ITEM_SLOT);
                if (itemStack != null && itemStack.getType() != Material.AIR) {
                    if (cursorItem != null && cursorItem.getType() != Material.AIR) {
                        Runes rune = runeManager.getRune(cursorItem.getItemMeta().getDisplayName());
                        if (rune != null && runeManager.canApplyRune(itemStack, rune)) {
                            runeManager.applyRune(itemStack, rune);
                            event.getWhoClicked().setItemOnCursor(null);
                        }
                    } else if (clickedItem != null && clickedItem.getType() != Material.AIR) {
                        Runes rune = runeManager.getRune(clickedItem.getItemMeta().getDisplayName());
                        if (rune != null) {
                            runeManager.removeRune(itemStack, rune);
                            event.getWhoClicked().setItemOnCursor(new ItemStack(Material.PAPER)); // Example item for the removed rune
                        }
                    }
                }
            }
        }
    }
}