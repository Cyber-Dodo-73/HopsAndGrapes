package fr.cyberdodo.hopsAndGrapes.items;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.NamespacedKey;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public abstract class AlcoholicBeverage {

    protected Plugin plugin;
    protected String name;
    protected ChatColor colorCode;
    protected Color potionColor;
    protected String type; // Exemple : "beer", "wine"

    public AlcoholicBeverage(Plugin plugin, String name, ChatColor colorCode, Color potionColor, String type) {
        this.plugin = plugin;
        this.name = name;
        this.colorCode = colorCode;
        this.potionColor = potionColor;
        this.type = type;
    }

    public ItemStack createBase() {
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Base Alcoolisée");
        meta.setColor(Color.fromRGB(139, 69, 19)); // Couleur marron

        // Définir les données personnalisées
        NamespacedKey key = new NamespacedKey(plugin, "hopsAndGrapes");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "alcohol_base");

        item.setItemMeta(meta);
        return item;
    }

    public ItemStack createPreparation() {
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.setDisplayName(colorCode + "Préparation de " + name);
        meta.setColor(potionColor);

        // Définir les données personnalisées
        NamespacedKey key = new NamespacedKey(plugin, "hopsAndGrapes");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, type + "_preparation");

        item.setItemMeta(meta);
        return item;
    }

    public ItemStack createFinalDrink() {
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.setDisplayName(colorCode + name);
        meta.setColor(potionColor);

        // Ajouter un effet visuel (optionnel)
        meta.addCustomEffect(new PotionEffect(PotionEffectType.NAUSEA, 1, 0), true);

        // Définir les données personnalisées
        NamespacedKey key = new NamespacedKey(plugin, "hopsAndGrapes");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, type);

        item.setItemMeta(meta);
        return item;
    }

    public abstract void applyEffects(org.bukkit.entity.Player player);

    public String getType() {
        return type;
    }
}