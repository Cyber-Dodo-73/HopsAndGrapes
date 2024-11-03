package fr.cyberdodo.hopsAndGrapes.listeners;


import fr.cyberdodo.hopsAndGrapes.items.*;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.persistence.PersistentDataType;

public class DrinkListener implements Listener {

    private final Plugin plugin;

    public DrinkListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDrink(PlayerItemConsumeEvent event) {
        ItemStack item = event.getItem();
        if (item == null || item.getType() != org.bukkit.Material.POTION) return;
        if (!item.hasItemMeta()) return;

        NamespacedKey key = new NamespacedKey(plugin, "hopsAndGrapes");
        String type = item.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING);

        if (type == null) return;

        Player player = event.getPlayer();

        AlcoholicBeverage beverage = null;

        switch (type) {
            case "beer":
                beverage = new Beer(plugin);
                break;
            case "wine":
                beverage = new Wine(plugin);
                break;
            case "vodka":
                beverage = new Vodka(plugin);
                break;
            case "rum":
                beverage = new Rum(plugin);
                break;
        }

        if (beverage != null) {
            beverage.applyEffects(player);
        }
    }
}
