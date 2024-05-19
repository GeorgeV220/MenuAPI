package com.georgev22.menuapi.api.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Menu inventory holder.
 */
public class MenuInventoryHolder implements InventoryHolder {

    private final IMenu menu;
    private final Player player;
    private final Inventory inventory;

    /**
     * Create a new menu inventory holder.
     *
     * @param menu The Menu.
     */
    public MenuInventoryHolder(@NotNull IMenu menu, Player player) {
        this.menu = menu;
        this.player = player;
        this.inventory = Bukkit.createInventory(this, this.menu.getRows() * 9);
    }

    /**
     * Get the object's Menu.
     *
     * @return The Menu.
     */
    public IMenu getMenu() {
        return this.menu;
    }


    /**
     * Get the object's player.
     *
     * @return The player.
     */
    public @NotNull Player getPlayer() {
        return this.player;
    }

    /**
     * Get the object's inventory.
     *
     * @return The inventory.
     */
    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }
}
