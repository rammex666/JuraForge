package fr.rammex.juraforge.config.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class OnClickRuneConfigMenu implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack clickedItem = event.getCurrentItem();
        if (event.getView().getTitle().equals("§cConfig Runes")) {
            event.setCancelled(true);

            if (clickedItem.getType() == Material.SUNFLOWER) {
                String displayName = clickedItem.getItemMeta().getDisplayName();
                List<String> lore = clickedItem.getItemMeta().getLore();

                if (displayName != null && lore != null) {
                    String[] parts = lore.get(1).split(ChatColor.translateAlternateColorCodes('&', "-&d"));
                    if (parts.length > 1) {
                        int level = Integer.parseInt(parts[1].trim()); // Trim the string before parsing
                        String[] parts2 = lore.get(2).split(ChatColor.translateAlternateColorCodes('&', "-&d"));
                        String effect = parts2[1];
                        String[] parts3 = lore.get(3).split(ChatColor.translateAlternateColorCodes('&', "-&d"));
                        String id = parts3[1];
                        String[] parts4 = lore.get(4).split(ChatColor.translateAlternateColorCodes('&', "-&d"));
                        boolean upgradable = Boolean.parseBoolean(parts4[1]);
                        String[] parts5 = lore.get(5).split(ChatColor.translateAlternateColorCodes('&', "-&d"));
                        String allowedItem = parts5[1];
                        for (int i = 6; i < lore.size(); i++) {
                            allowedItem += lore.get(i);
                        }

                        createRuneInventory(id, displayName, level, effect, upgradable, allowedItem);
                    }
                }
            }
        }
    }


    private void createRuneInventory(String runeID,String runeName, int level, String effect,  boolean upgradable, String allowedItem){
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', "&cRune | "+runeID));

        inv.setItem(9, createRuneNameItem(runeName));
        inv.setItem(11, createRuneLevelItem(level));
        inv.setItem(13, createRuneEffectItem(effect));
        inv.setItem(15, createRuneUpgradableItem(upgradable));
        inv.setItem(17, createRuneAllowedItem(allowedItem));


    }



    private ItemStack createRuneNameItem(String runeName){
        ItemStack runeItem = new ItemStack(Material.PAPER);
        ItemMeta runeMeta = runeItem.getItemMeta();
        runeMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aNom"));
        runeMeta.setLore(List.of(runeName));
        runeItem.setItemMeta(runeMeta);
        return runeItem;
    }

    private ItemStack createRuneLevelItem(int level){
        ItemStack runeItem = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta runeMeta = runeItem.getItemMeta();
        runeMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a✦ Level"));
        runeMeta.setLore(List.of(String.valueOf(level)));
        runeItem.setItemMeta(runeMeta);
        return runeItem;
    }

    private ItemStack createRuneEffectItem(String effect){
        ItemStack runeItem = new ItemStack(Material.SPLASH_POTION);
        ItemMeta runeMeta = runeItem.getItemMeta();
        runeMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a✦ Effect(s)"));
        runeMeta.setLore(List.of(effect));
        runeItem.setItemMeta(runeMeta);
        return runeItem;
    }

    private ItemStack createRuneUpgradableItem(boolean upgradable){
        ItemStack runeItem = new ItemStack(Material.SPECTRAL_ARROW);
        ItemMeta runeMeta = runeItem.getItemMeta();
        runeMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a✦ Upgradable ?"));
        runeMeta.setLore(List.of(String.valueOf(upgradable)));
        runeItem.setItemMeta(runeMeta);
        return runeItem;
    }

    private ItemStack createRuneAllowedItem(String allowedItem){
        ItemStack runeItem = new ItemStack(Material.GREEN_BANNER);
        ItemMeta runeMeta = runeItem.getItemMeta();
        runeMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a✦ Allowed(s) item(s)"));
        runeMeta.setLore(List.of(allowedItem));
        runeItem.setItemMeta(runeMeta);
        return runeItem;
    }
}
