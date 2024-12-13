package fr.rammex.juraforge.rune.customeffects;

import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.stat.StatModifier;
import dev.aurelium.auraskills.api.stat.Stats;
import dev.aurelium.auraskills.api.user.SkillsUser;
import org.bukkit.entity.Player;

public class SpeedEffect implements CustomEffect {
    AuraSkillsApi auraSkills = AuraSkillsApi.get();

    @Override
    public void apply(Player player, int level) {
        SkillsUser user = auraSkills.getUser(player.getUniqueId());
        double speed = user.getStatLevel(Stats.SPEED);
        user.addStatModifier(new StatModifier(player.getUniqueId()+"_SPEED", Stats.SPEED, speed + 20*level));
    }

    @Override
    public void remove(Player player, int level) {
        SkillsUser user = auraSkills.getUser(player.getUniqueId());
        user.removeStatModifier(player.getUniqueId()+"_SPEED");
    }
}
