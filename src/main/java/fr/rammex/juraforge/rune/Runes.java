package fr.rammex.juraforge.rune;

import org.bukkit.potion.PotionEffect;
import java.util.List;

public class Runes {
    private final String name;
    private final List<PotionEffect> effects;
    private final List<String> allowedItems;
    private final Integer level;

    public Runes(String name, List<PotionEffect> effects, List<String> allowedItems, Integer level) {
        this.name = name;
        this.effects = effects;
        this.allowedItems = allowedItems;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public List<PotionEffect> getEffects() {
        return effects;
    }

    public List<String> getAllowedItems() {
        return allowedItems;
    }

    public Integer getLevel() {
        return level;
    }
}