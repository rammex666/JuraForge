package fr.rammex.juraforge.rune.customeffects;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class ForceEffect implements CustomEffect {

    @Override
    public void apply(Player player, int level) {
        AttributeInstance attackDamageAttribute = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
        if (attackDamageAttribute != null) {
            double baseAttackDamage = 1.0; // Default base attack damage in Minecraft
            double newAttackDamage = baseAttackDamage + level; // Increase attack damage based on level
            attackDamageAttribute.setBaseValue(newAttackDamage);
        }
    }

    @Override
    public void remove(Player player, int level) {
        AttributeInstance attackDamageAttribute = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
        if (attackDamageAttribute != null) {
            attackDamageAttribute.setBaseValue(1.0); // Reset to default base attack damage
        }
    }
}
