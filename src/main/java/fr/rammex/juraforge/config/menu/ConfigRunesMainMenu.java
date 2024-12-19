package fr.rammex.juraforge.config.menu;

import fr.rammex.juraforge.rune.RuneManager;
import fr.rammex.juraforge.rune.Runes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigRunesMainMenu {

    static Map<String, Runes> runes = RuneManager.runes;
    private static List<String> lore = new ArrayList<>();

    public static void getConfigRunesMenu(Player player){
        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', "&cConfig Runes"));

        for (Map.Entry<String, Runes> entry : runes.entrySet()){
            Runes rune = entry.getValue();
            lore.add("");
            lore.add(ChatColor.translateAlternateColorCodes('&',"&a✦ Level -&d "+rune.getLevel()));
            lore.add(ChatColor.translateAlternateColorCodes('&',"&a✦ Effect -&d "+rune.getEffects()));
            lore.add(ChatColor.translateAlternateColorCodes('&',"&a✦ Id -&d "+rune.getId()));
            lore.add(ChatColor.translateAlternateColorCodes('&',"&a✦ Upgradable ? -&d "+rune.getIsUpgradeable()));
            lore.add(ChatColor.translateAlternateColorCodes('&',"&a✦ Allowed item - "));
            for(String material : rune.getAllowedItems()){
                lore.add(ChatColor.translateAlternateColorCodes('&',"  &d- "+material));
            }

            ItemStack runeItem = new ItemStack(Material.SUNFLOWER);
            ItemMeta runeMeta = runeItem.getItemMeta();
            runeMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a" + rune.getName()));

            runeMeta.setLore(lore);
            runeItem.setItemMeta(runeMeta);
            inventory.addItem(runeItem);
            lore.clear();
        }
        player.openInventory(inventory);
    }

}
