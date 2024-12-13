package fr.rammex.juraforge.rune.customeffects;

import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.stat.StatModifier;
import dev.aurelium.auraskills.api.stat.Stats;
import dev.aurelium.auraskills.api.user.SkillsUser;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class ForceEffect implements CustomEffect {
    AuraSkillsApi auraSkills = AuraSkillsApi.get();

    @Override
    public void apply(Player player, int level) {
        SkillsUser user = auraSkills.getUser(player.getUniqueId());
        double strength = user.getStatLevel(Stats.STRENGTH);
        user.addStatModifier(new StatModifier(player.getUniqueId()+"_STRENGTH", Stats.STRENGTH, strength + level));
    }

    @Override
    public void remove(Player player, int level) {
        SkillsUser user = auraSkills.getUser(player.getUniqueId());
        user.removeStatModifier(player.getUniqueId()+"_STRENGTH");
    }
}
