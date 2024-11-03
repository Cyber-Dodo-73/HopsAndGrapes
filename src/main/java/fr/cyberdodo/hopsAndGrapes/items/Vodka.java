package fr.cyberdodo.hopsAndGrapes.items;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.plugin.Plugin;

public class Vodka extends AlcoholicBeverage {

    public Vodka(Plugin plugin) {
        super(plugin, "Vodka", ChatColor.GRAY, Color.SILVER, "vodka");
    }

    @Override
    public void applyEffects(Player player) {
        // Appliquer l'effet de nausée
        player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 20 * 90, 2)); // 90 secondes, niveau III
        // Appliquer l'effet de force
        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 20 * 120, 0)); // 2 minutes, niveau I
    }
}