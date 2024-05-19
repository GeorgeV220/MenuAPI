package com.georgev22.menuapi.api.inventory.components;


import com.georgev22.menuapi.api.inventory.PageRange;
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
    IMenuItem getItem();

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
    void setItem(IMenuItem item);

    /**
     * Sets the slot of the button.
     *
     * @param slot The slot to be set.
     */
    void setSlot(int slot);

    void clickEvent(Player player, InventoryClickEvent event);

}
