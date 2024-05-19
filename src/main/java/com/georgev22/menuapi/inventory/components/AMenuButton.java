package com.georgev22.menuapi.inventory.components;

import com.georgev22.menuapi.api.inventory.components.IMenuButton;
import com.georgev22.menuapi.api.inventory.components.IMenuItem;
import com.georgev22.menuapi.api.inventory.PageRange;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public abstract class AMenuButton implements IMenuButton {

    private IMenuItem item;
    private int slot;
    private final PageRange pageRange;

    public AMenuButton(MenuItem item, int slot, PageRange pageRange) {
        this.item = item;
        this.slot = slot;
        this.pageRange = pageRange;
    }

    /**
     * Retrieves the item in the button.
     *
     * @return The item in the button.
     */
    @Override
    public IMenuItem getItem() {
        return this.item;
    }

    /**
     * Retrieves the slot of the button.
     *
     * @return The slot of the button.
     */
    @Override
    public int getSlot() {
        return this.slot;
    }


    /**
     * Sets the item in the button.
     *
     * @param item The item to be set.
     */
    @Override
    public void setItem(IMenuItem item) {
        this.item = item;
    }

    /**
     * Sets the slot of the button.
     *
     * @param slot The slot to be set.
     */
    @Override
    public void setSlot(int slot) {
        this.slot = slot;
    }

    @Override
    public PageRange getPageRange() {
        return this.pageRange;
    }

    /**
     * Performs the click event of the button.
     *
     * @param player The player who clicked the button.
     * @param event  The click event.
     */
    @Override
    public abstract void clickEvent(Player player, InventoryClickEvent event);
}
