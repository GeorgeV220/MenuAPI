package com.georgev22.menuapi.inventory.components;

import com.georgev22.menuapi.api.inventory.components.IMenuItem;
import com.georgev22.menuapi.utilities.SerializableItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;

public class MenuItem implements IMenuItem {

    private SerializableItemStack itemStack;

    public MenuItem() {
        this.itemStack = new SerializableItemStack(
                new ItemStack(Material.PAPER),
                BigInteger.ONE
        );
    }

    public MenuItem(SerializableItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Retrieves the item stack of the item.
     *
     * @return The item stack of the item.
     */
    @Override
    public SerializableItemStack getItemStack() {
        return this.itemStack;
    }

    /**
     * Sets the item stack of the item.
     *
     * @param itemStack The item stack to be set.
     */
    @Override
    public void setItemStack(SerializableItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
