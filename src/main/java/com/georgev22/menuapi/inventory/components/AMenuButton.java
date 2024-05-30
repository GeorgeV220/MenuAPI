package com.georgev22.menuapi.inventory.components;

import com.georgev22.menuapi.api.inventory.components.IMenuButton;
import com.georgev22.menuapi.api.inventory.PageRange;
import com.georgev22.menuapi.utilities.SerializableItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public abstract class AMenuButton implements IMenuButton {

    private SerializableItemStack item;
    private int slot;
    private final PageRange pageRange;

    public AMenuButton(SerializableItemStack item, int slot, PageRange pageRange) {
        this.item = item;
        this.slot = slot;
        this.pageRange = pageRange;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SerializableItemStack getItem() {
        return this.item;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSlot() {
        return this.slot;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setItem(SerializableItemStack item) {
        this.item = item;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSlot(int slot) {
        this.slot = slot;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageRange getPageRange() {
        return this.pageRange;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void clickEvent(Player player, InventoryClickEvent event);
}
