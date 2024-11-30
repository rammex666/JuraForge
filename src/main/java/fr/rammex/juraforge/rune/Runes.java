package fr.rammex.juraforge.rune;

import fr.rammex.juraforge.rune.customeffects.CustomEffect;
import java.util.List;

public class Runes {
    private final String name;
    private final List<CustomEffect> effects;
    private final List<String> allowedItems;
    private final Integer level;
    private final Boolean isUpgradeable;
    private final List<CustomEffect> onHoldItemEffects;
    private final List<CustomEffect> onEquipEffects;
    private final List<CustomEffect> onPlayerInteractEffects;

    public Runes(String name, List<CustomEffect> effects, List<String> allowedItems, Integer level, Boolean isUpgradeable,
                 List<CustomEffect> onHoldItemEffects, List<CustomEffect> onEquipEffects, List<CustomEffect> onPlayerInteractEffects) {
        this.name = name;
        this.effects = effects;
        this.allowedItems = allowedItems;
        this.level = level;
        this.isUpgradeable = isUpgradeable;
        this.onHoldItemEffects = onHoldItemEffects;
        this.onEquipEffects = onEquipEffects;
        this.onPlayerInteractEffects = onPlayerInteractEffects;
    }

    public String getName() {
        return name;
    }

    public List<CustomEffect> getEffects() {
        return effects;
    }

    public List<String> getAllowedItems() {
        return allowedItems;
    }

    public Integer getLevel() {
        return level;
    }

    public Boolean getIsUpgradeable() {
        return isUpgradeable;
    }

    public List<CustomEffect> getOnHoldItemEffects() {
        return onHoldItemEffects;
    }

    public List<CustomEffect> getOnEquipEffects() {
        return onEquipEffects;
    }

    public List<CustomEffect> getOnPlayerInteractEffects() {
        return onPlayerInteractEffects;
    }
}