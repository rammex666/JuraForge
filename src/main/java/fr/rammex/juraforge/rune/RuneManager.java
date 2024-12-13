package fr.rammex.juraforge.rune;

import fr.rammex.juraforge.rune.Runes;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuneManager {
    private static final Map<String, Runes> runes = new HashMap<>();

    public void registerRune(Runes rune) {
        runes.put(rune.getName(), rune);
    }

    public boolean canApplyRune(ItemStack item, Runes rune) {
        return rune.getAllowedItems().contains(item.getType().toString());
    }

    public void applyRune(ItemStack item, Runes rune) {
        if (!canApplyRune(item, rune)) {
            throw new IllegalArgumentException("Rune cannot be applied to this item.");
        }

        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            List<String> lore = meta.getLore();
            if (lore == null) {
                lore = new ArrayList<>();
            }
            lore.add("Rune: " + rune.getName() + " - " + rune.getLevel());
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
    }

    public void removeRune(ItemStack item, Runes rune) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            List<String> lore = meta.getLore();
            if (lore != null && lore.remove("Rune: " + rune.getName())) {
                meta.setLore(lore);
                item.setItemMeta(meta);
            }
        }
    }

    public void upgradeRune(ItemStack item, Runes rune) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            List<String> lore = meta.getLore();
            if (lore != null) {
                for (int i = 0; i < lore.size(); i++) {
                    if (lore.get(i).startsWith("Rune: " + rune.getName())) {
                        int currentLevel = rune.getLevel();
                        lore.set(i, "Rune: " + rune.getName() + " - " + (currentLevel + 1));
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                        return;
                    }
                }
            }
        }
    }

    public static Runes getRune(String name) {
        return runes.get(name);
    }

    public Runes getRuneFromItem(ItemStack item) {
        System.out.println("Getting rune from item");
        ItemMeta meta = item.getItemMeta();
        if (meta != null && meta.getLore() != null) {
            for (String lore : meta.getLore()) {
                if (lore.startsWith("Rune: ")) {
                    String[] parts = lore.substring(6).split(" - ");
                    String runeName = parts[0];
                    System.out.println("Rune name: " + runeName);
                    return getRune(runeName);
                }
            }
        }
        return null;
    }

    public ItemStack createRuneItem(Runes rune) {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(rune.getName());
            item.setItemMeta(meta);
        }
        return item;
    }
}