package fr.cyberdodo.hopsAndGrapes.items;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.plugin.Plugin;

public class Beer extends AlcoholicBeverage {

    public Beer(Plugin plugin) {
        super(plugin, "Bière", ChatColor.GOLD, Color.ORANGE, "beer");
    }

    @Override
    public void applyEffects(Player player) {
        // Effet de nausée
        player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 20 * 30, 0)); // 30 secondes, niveau I
        // Effet de vitesse
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 120, 0)); // 2 minutes, niveau I
    }
}