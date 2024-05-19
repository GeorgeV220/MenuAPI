package com.georgev22.menuapi.api.inventory.components;

import com.georgev22.menuapi.utilities.SerializableItemStack;

/**
 * Represents an inventory item.
 */
public interface IMenuItem {

    /**
     * Retrieves the item stack of the item.
     *
     * @return The item stack of the item.
     */
    SerializableItemStack getItemStack();

    /**
     * Sets the item stack of the item.
     *
     * @param itemStack The item stack to be set.
     */
    void setItemStack(SerializableItemStack itemStack);

}
