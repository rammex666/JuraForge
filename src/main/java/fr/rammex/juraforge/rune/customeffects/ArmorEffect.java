package fr.rammex.juraforge.rune.customeffects;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class ArmorEffect implements CustomEffect{

    @Override
    public void apply(Player player, int level) {
        AttributeInstance armorAttribute = player.getAttribute(Attribute.GENERIC_ARMOR);
        if (armorAttribute != null) {
            double baseArmor = 0.0;
            double newArmor = baseArmor + level;
            armorAttribute.setBaseValue(newArmor);
        }
    }

    @Override
    public void remove(Player player, int level) {
        AttributeInstance armorAttribute = player.getAttribute(Attribute.GENERIC_ARMOR);
        if (armorAttribute != null) {
            armorAttribute.setBaseValue(0.0);
        }
    }
}
