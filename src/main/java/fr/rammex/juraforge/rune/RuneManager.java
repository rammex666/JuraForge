package fr.rammex.juraforge.runes;

import fr.rammex.juraforge.rune.Runes;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuneManager {
    private final Map<String, Runes> runes = new HashMap<>();

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

    public Runes getRune(String name) {
        return runes.get(name);
    }
    public Runes getRuneFromItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null && meta.getLore() != null) {
            for (String lore : meta.getLore()) {
                if (lore.startsWith("Rune: ")) {
                    String runeName = lore.substring(6);
                    return getRune(runeName);
                }
            }
        }
        return null;
    }
}