package fr.rammex.juraforge.rune.customeffects;

import org.bukkit.entity.Player;

public interface CustomEffect {
    void apply(Player player);
    void remove(Player player);
}