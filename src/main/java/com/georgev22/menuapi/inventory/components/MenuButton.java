package com.georgev22.menuapi.inventory.components;

import com.georgev22.menuapi.api.inventory.PageRange;
import com.georgev22.menuapi.utilities.SerializableItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Action-less menu button.
 * This button does not perform any action when clicked.
 */
public class MenuButton extends AMenuButton {

    /**
     * {@inheritDoc}
     */
    public MenuButton(SerializableItemStack item, int slot, PageRange pageRange) {
        super(item, slot, pageRange);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clickEvent(Player player, InventoryClickEvent event) {

    }
}
