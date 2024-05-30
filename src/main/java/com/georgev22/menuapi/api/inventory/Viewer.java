package com.georgev22.menuapi.api.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * The Viewer class represents a user interface for displaying an inventory to a player.
 * It keeps track of the player, the current page, and the associated inventory.
 */
public class Viewer {

    private final Player player;
    private int page;
    private Inventory inventory;

    /**
     * Constructs a Viewer object for the given player with the default page (1).
     *
     * @param player The player associated with this viewer
     */
    public Viewer(Player player) {
        this(player, 1);
    }

    /**
     * Constructs a Viewer object for the given player with the specified inventory.
     *
     * @param player    The player associated with this viewer
     * @param inventory The initial inventory for this viewer
     */
    public Viewer(Player player, Inventory inventory) {
        this(player, 1, inventory);
    }

    /**
     * Constructs a Viewer object for the given player with the specified initial page.
     *
     * @param player The player associated with this viewer
     * @param page   The initial page number
     */
    public Viewer(Player player, int page) {
        this.player = player;
        this.page = page;
    }

    /**
     * Constructs a Viewer object for the given player with the specified initial page and inventory.
     *
     * @param player    The player associated with this viewer
     * @param page      The initial page number
     * @param inventory The initial inventory for this viewer
     */
    public Viewer(Player player, int page, Inventory inventory) {
        this.player = player;
        this.page = page;
        this.inventory = inventory;
    }

    /**
     * Returns the player associated with this viewer.
     *
     * @return The player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Returns the current page number.
     *
     * @return The page number
     */
    public int getPage() {
        return this.page;
    }

    /**
     * Sets the current page number.
     *
     * @param page The new page number
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Returns the associated inventory.
     *
     * @return The inventory
     */
    public Inventory getInventory() {
        return this.inventory;
    }

    /**
     * Sets the associated inventory.
     *
     * @param inventory The new inventory
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Returns a string representation of the Viewer object.
     *
     * @return A string containing player name and current page
     */
    @Override
    public String toString() {
        return "Viewer{" +
                "player=" + player.getName() +
                ", page=" + page +
                '}';
    }
}