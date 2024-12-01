package fr.rammex.juraforge.rune.customeffects;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class VigueurEffect implements CustomEffect {
    @Override
    public void apply(Player player, int level) {
        AttributeInstance attackSpeedAttribute = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        if (attackSpeedAttribute != null) {
            double baseAttackSpeed = 4.0; // Default base attack speed in Minecraft
            double newAttackSpeed = baseAttackSpeed + (level * 0.1); // Increase attack speed based on level
            attackSpeedAttribute.setBaseValue(newAttackSpeed);
        }
    }

    @Override
    public void remove(Player player, int level) {
        AttributeInstance attackSpeedAttribute = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        if (attackSpeedAttribute != null) {
            attackSpeedAttribute.setBaseValue(4.0); // Reset to default base attack speed
        }
    }
}