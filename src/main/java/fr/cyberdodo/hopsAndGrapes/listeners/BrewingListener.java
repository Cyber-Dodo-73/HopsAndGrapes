package fr.cyberdodo.hopsAndGrapes.listeners;

import fr.cyberdodo.hopsAndGrapes.items.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class BrewingListener implements Listener {

    private final Plugin plugin;

    public BrewingListener(Plugin plugin) {
        this.plugin = plugin;
        startManualBrewingCheck();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        if (inventory == null || inventory.getType() != InventoryType.BREWING) {
            return;
        }

        // Vérifier que le clic est dans un stand de brassage
        if (event.getSlotType() == InventoryType.SlotType.CRAFTING && event.getCurrentItem() != null) {
            ItemStack currentItem = event.getCurrentItem();

            // Vérifier si l'item est un de nos ingrédients personnalisés
            if (isCustomItem(currentItem, "hops") || isCustomItem(currentItem, "grapes") ||
                    isCustomItem(currentItem, "potato_mash") || isCustomItem(currentItem, "molasses")) {

                plugin.getLogger().info("Custom ingredient inserted in brewing stand: " + currentItem.getItemMeta().getDisplayName());
                event.setCancelled(false);

                // Vérifier que nous avons aussi des bases alcoolisées prêtes
                BrewerInventory brewerInventory = (BrewerInventory) event.getInventory();
                if (areAlcoholBasesPresent(brewerInventory) && !arePreparedDrinksPresent(brewerInventory)) {
                    plugin.getLogger().info("Alcohol bases detected. Starting custom brewing process.");

                    // Lancer le brassage personnalisé après un certain délai (par exemple, 10 secondes)
                    startBrewingWithProgress(brewerInventory, 200); // 200 ticks = 10 secondes environ
                } else {
                    plugin.getLogger().info("No alcohol bases detected in brewing stand.");
                }
            }
        }
    }

    @EventHandler
    public void onBrew(BrewEvent event) {
        BrewerInventory inventory = event.getContents();
        ItemStack ingredient = inventory.getIngredient();

        // Vérifier si l'ingrédient est la levure personnalisée
        if (ingredient != null && isCustomItem(ingredient, "yeast")) {
            plugin.getLogger().info("Yeast detected in brewing stand. Preparing alcohol base.");

            boolean hasWaterBottles = true;
            for (int slot = 0; slot < 3; slot++) {
                ItemStack item = inventory.getItem(slot);
                if (item == null || item.getType() != Material.POTION) {
                    hasWaterBottles = false;
                    plugin.getLogger().info("Slot " + slot + " does not contain a water bottle.");
                    break;
                }
            }

            if (hasWaterBottles) {
                // Planifier la modification du contenu après l'exécution par défaut de l'événement BrewEvent
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        plugin.getLogger().info("Replacing water bottles with alcohol base.");

                        // Créer la base alcoolisée
                        AlcoholicBeverage base = new Beer(plugin); // Utiliser une boisson quelconque pour créer la base
                        ItemStack alcoholBase = base.createBase();

                        // Remplacer les bouteilles d'eau par la base alcoolisée
                        for (int slot = 0; slot < 3; slot++) {
                            inventory.setItem(slot, alcoholBase);
                            plugin.getLogger().info("Slot " + slot + " replaced with alcohol base: " + alcoholBase.getItemMeta().getDisplayName());
                        }

                        // Consommer une seule unité de l'ingrédient (la levure)
                        ingredient.setAmount(ingredient.getAmount() - 1);
                        if (ingredient.getAmount() > 1) {
                            ingredient.setAmount(ingredient.getAmount() - 1);
                            inventory.setIngredient(ingredient);
                        } else {
                            inventory.setIngredient(new ItemStack(Material.AIR));
                        }
                        plugin.getLogger().info("One unit of yeast consumed, alcohol base created.");
                    }
                }.runTaskLater(plugin, 1L); // Exécuter après 1 tick
            } else {
                plugin.getLogger().info("Brewing stand does not have the required water bottles.");
            }
        }
    }

    private boolean areAlcoholBasesPresent(BrewerInventory inventory) {
        NamespacedKey key = new NamespacedKey(plugin, "hopsAndGrapes");

        for (int slot = 0; slot < 3; slot++) {
            ItemStack item = inventory.getItem(slot);
            if (item == null) {
                plugin.getLogger().info("Slot " + slot + " is empty. Alcohol base not present.");
                return false;
            }

            if (item.getType() != Material.POTION) {
                plugin.getLogger().info("Slot " + slot + " does not contain a potion. Found: " + item.getType());
                return false;
            }

            // Vérifier que l'item est une base alcoolisée
            if (!item.hasItemMeta()) {
                plugin.getLogger().info("Slot " + slot + " does not have item meta. Not an alcohol base.");
                return false;
            }

            String dataValue = item.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING);
            if (dataValue == null || !dataValue.equals("alcohol_base")) {
                plugin.getLogger().info("Slot " + slot + " does not contain an alcohol base. Found: " + dataValue);
                return false;
            }

            plugin.getLogger().info("Slot " + slot + " contains a valid alcohol base.");
        }
        return true;
    }

    private void startBrewingWithProgress(BrewerInventory inventory, int totalTicks) {
        plugin.getLogger().info("Brewing process with progress started.");

        new BukkitRunnable() {
            int ticksElapsed = 0;

            @Override
            public void run() {
                if (ticksElapsed >= totalTicks) {
                    // Brassage terminé
                    plugin.getLogger().info("Brewing process complete.");
                    AlcoholicBeverage beverage = determineBeverage(inventory.getIngredient());
                    if (beverage != null) {
                        startBrewing(inventory, beverage);
                    }
                    cancel();
                    return;
                }

                ticksElapsed += 20; // Augmente de 20 ticks à chaque seconde
            }
        }.runTaskTimer(plugin, 0L, 20L); // Exécuter toutes les secondes
    }

    private AlcoholicBeverage determineBeverage(ItemStack ingredient) {
        if (ingredient == null) {
            return null;
        }

        if (isCustomItem(ingredient, "hops")) {
            plugin.getLogger().info("Ingredient is hops - preparing beer");
            return new Beer(plugin);
        } else if (isCustomItem(ingredient, "grapes")) {
            plugin.getLogger().info("Ingredient is grapes - preparing wine");
            return new Wine(plugin);
        } else if (isCustomItem(ingredient, "potato_mash")) {
            plugin.getLogger().info("Ingredient is potato mash - preparing vodka");
            return new Vodka(plugin);
        } else if (isCustomItem(ingredient, "molasses")) {
            plugin.getLogger().info("Ingredient is molasses - preparing rum");
            return new Rum(plugin);
        }

        return null;
    }

    private boolean arePreparedDrinksPresent(BrewerInventory inventory) {
        for (int slot = 0; slot < 3; slot++) {
            ItemStack item = inventory.getItem(slot);
            if (item != null && item.hasItemMeta() && isCustomItem(item, "prepared_drink")) {
                plugin.getLogger().info("Slot " + slot + " already contains a prepared drink.");
                return true;
            }
        }
        return false;
    }

    private void startBrewing(BrewerInventory inventory, AlcoholicBeverage beverage) {
        plugin.getLogger().info("Brewing process started for beverage: " + beverage.getClass().getSimpleName());

        // Remplacer les bases alcoolisées par la boisson préparée
        ItemStack preparedDrink = beverage.createPreparation();

        for (int slot = 0; slot < 3; slot++) {
            inventory.setItem(slot, preparedDrink);
            plugin.getLogger().info("Slot " + slot + " replaced with: " + preparedDrink.getItemMeta().getDisplayName());
        }

        // Consommer une seule unité de l'ingrédient
        ItemStack ingredient = inventory.getIngredient();
        if (ingredient != null) {
            ingredient.setAmount(ingredient.getAmount() - 1);
            if (ingredient.getAmount() > 1) {
                ingredient.setAmount(ingredient.getAmount() - 1);
                inventory.setIngredient(ingredient);
            } else {
                inventory.setIngredient(new ItemStack(Material.AIR));
            }
            plugin.getLogger().info("One unit of ingredient consumed, brewing process complete.");
        }
    }

    private boolean isCustomItem(ItemStack item, String identifier) {
        if (item == null || !item.hasItemMeta()) return false;
        NamespacedKey key = new NamespacedKey(plugin, "hopsAndGrapes");
        String value = item.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING);
        return identifier.equals(value);
    }

    public void startManualBrewingCheck() {
        new BukkitRunnable() {
            @Override
            public void run() {
                // Parcourir tous les brewing stands ouverts et vérifier le contenu
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getOpenInventory().getType() == InventoryType.BREWING) {
                        BrewerInventory brewingInventory = (BrewerInventory) player.getOpenInventory().getTopInventory();

                        // Vérifier si des bases alcoolisées sont présentes
                        if (areAlcoholBasesPresent(brewingInventory)) {
                            plugin.getLogger().info("Alcohol bases detected.");

                            // Vérifier si un ingrédient est ajouté
                            ItemStack ingredient = brewingInventory.getIngredient();
                            if (ingredient != null) {
                                AlcoholicBeverage beverage = determineBeverage(ingredient);
                                if (beverage != null) {
                                    // Démarrer le brassage après un délai de 10 secondes
                                    plugin.getLogger().info("Starting brewing process with custom ingredient.");
                                    startBrewingWithProgress(brewingInventory, 200); // 200 ticks = 10 secondes
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 20L); // Vérifier toutes les secondes (20 ticks)
    }
}
