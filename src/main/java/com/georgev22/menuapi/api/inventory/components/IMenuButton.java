package com.georgev22.menuapi.api.inventory.components;


import com.georgev22.menuapi.api.inventory.PageRange;
import com.georgev22.menuapi.utilities.SerializableItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Represents an inventory button.
 */
public interface IMenuButton {

    /**
     * Retrieves the item in the button.
     *
     * @return The item in the button.
     */
    SerializableItemStack getItem();

    /**
     * Retrieves the slot of the button.
     *
     * @return The slot of the button.
     */
    int getSlot();

    /**
     * Retrieves the page range of the button.
     *
     * @return The page range of the button.
     */
    PageRange getPageRange();

    /**
     * Sets the item in the button.
     *
     * @param item The item to be set.
     */
    void setItem(SerializableItemStack item);

    /**
     * Sets the slot of the button.
     *
     * @param slot The slot to be set.
     */
    void setSlot(int slot);

    /**
     * Handles the click event of the button.
     *
     * @param player The player who clicked the button.
     * @param event  The click event.
     */
    void clickEvent(Player player, InventoryClickEvent event);

}
